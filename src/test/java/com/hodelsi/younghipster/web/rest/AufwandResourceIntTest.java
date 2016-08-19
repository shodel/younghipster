package com.hodelsi.younghipster.web.rest;

import com.hodelsi.younghipster.YounghipsterApp;
import com.hodelsi.younghipster.domain.Aufwand;
import com.hodelsi.younghipster.repository.AufwandRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AufwandResource REST controller.
 *
 * @see AufwandResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = YounghipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class AufwandResourceIntTest {


    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_STUNDEN = 1F;
    private static final Float UPDATED_STUNDEN = 2F;
    private static final String DEFAULT_TYP = "AAAAA";
    private static final String UPDATED_TYP = "BBBBB";

    @Inject
    private AufwandRepository aufwandRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAufwandMockMvc;

    private Aufwand aufwand;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AufwandResource aufwandResource = new AufwandResource();
        ReflectionTestUtils.setField(aufwandResource, "aufwandRepository", aufwandRepository);
        this.restAufwandMockMvc = MockMvcBuilders.standaloneSetup(aufwandResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        aufwand = new Aufwand();
        aufwand.setDatum(DEFAULT_DATUM);
        aufwand.setStunden(DEFAULT_STUNDEN);
        aufwand.setTyp(DEFAULT_TYP);
    }

    @Test
    @Transactional
    public void createAufwand() throws Exception {
        int databaseSizeBeforeCreate = aufwandRepository.findAll().size();

        // Create the Aufwand

        restAufwandMockMvc.perform(post("/api/aufwands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aufwand)))
                .andExpect(status().isCreated());

        // Validate the Aufwand in the database
        List<Aufwand> aufwands = aufwandRepository.findAll();
        assertThat(aufwands).hasSize(databaseSizeBeforeCreate + 1);
        Aufwand testAufwand = aufwands.get(aufwands.size() - 1);
        assertThat(testAufwand.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testAufwand.getStunden()).isEqualTo(DEFAULT_STUNDEN);
        assertThat(testAufwand.getTyp()).isEqualTo(DEFAULT_TYP);
    }

    @Test
    @Transactional
    public void getAllAufwands() throws Exception {
        // Initialize the database
        aufwandRepository.saveAndFlush(aufwand);

        // Get all the aufwands
        restAufwandMockMvc.perform(get("/api/aufwands?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aufwand.getId().intValue())))
                .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
                .andExpect(jsonPath("$.[*].stunden").value(hasItem(DEFAULT_STUNDEN.doubleValue())))
                .andExpect(jsonPath("$.[*].typ").value(hasItem(DEFAULT_TYP.toString())));
    }

    @Test
    @Transactional
    public void getAufwand() throws Exception {
        // Initialize the database
        aufwandRepository.saveAndFlush(aufwand);

        // Get the aufwand
        restAufwandMockMvc.perform(get("/api/aufwands/{id}", aufwand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(aufwand.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.stunden").value(DEFAULT_STUNDEN.doubleValue()))
            .andExpect(jsonPath("$.typ").value(DEFAULT_TYP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAufwand() throws Exception {
        // Get the aufwand
        restAufwandMockMvc.perform(get("/api/aufwands/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAufwand() throws Exception {
        // Initialize the database
        aufwandRepository.saveAndFlush(aufwand);
        int databaseSizeBeforeUpdate = aufwandRepository.findAll().size();

        // Update the aufwand
        Aufwand updatedAufwand = new Aufwand();
        updatedAufwand.setId(aufwand.getId());
        updatedAufwand.setDatum(UPDATED_DATUM);
        updatedAufwand.setStunden(UPDATED_STUNDEN);
        updatedAufwand.setTyp(UPDATED_TYP);

        restAufwandMockMvc.perform(put("/api/aufwands")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAufwand)))
                .andExpect(status().isOk());

        // Validate the Aufwand in the database
        List<Aufwand> aufwands = aufwandRepository.findAll();
        assertThat(aufwands).hasSize(databaseSizeBeforeUpdate);
        Aufwand testAufwand = aufwands.get(aufwands.size() - 1);
        assertThat(testAufwand.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testAufwand.getStunden()).isEqualTo(UPDATED_STUNDEN);
        assertThat(testAufwand.getTyp()).isEqualTo(UPDATED_TYP);
    }

    @Test
    @Transactional
    public void deleteAufwand() throws Exception {
        // Initialize the database
        aufwandRepository.saveAndFlush(aufwand);
        int databaseSizeBeforeDelete = aufwandRepository.findAll().size();

        // Get the aufwand
        restAufwandMockMvc.perform(delete("/api/aufwands/{id}", aufwand.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Aufwand> aufwands = aufwandRepository.findAll();
        assertThat(aufwands).hasSize(databaseSizeBeforeDelete - 1);
    }
}
