package com.hodelsi.younghipster.web.rest;

import com.hodelsi.younghipster.YounghipsterApp;
import com.hodelsi.younghipster.domain.Spesen;
import com.hodelsi.younghipster.repository.SpesenRepository;

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
 * Test class for the SpesenResource REST controller.
 *
 * @see SpesenResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = YounghipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class SpesenResourceIntTest {


    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_POSTEN = "AAAAA";
    private static final String UPDATED_POSTEN = "BBBBB";

    private static final Float DEFAULT_BETRAG = 1F;
    private static final Float UPDATED_BETRAG = 2F;

    @Inject
    private SpesenRepository spesenRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSpesenMockMvc;

    private Spesen spesen;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpesenResource spesenResource = new SpesenResource();
        ReflectionTestUtils.setField(spesenResource, "spesenRepository", spesenRepository);
        this.restSpesenMockMvc = MockMvcBuilders.standaloneSetup(spesenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        spesen = new Spesen();
        spesen.setDatum(DEFAULT_DATUM);
        spesen.setPosten(DEFAULT_POSTEN);
        spesen.setBetrag(DEFAULT_BETRAG);
    }

    @Test
    @Transactional
    public void createSpesen() throws Exception {
        int databaseSizeBeforeCreate = spesenRepository.findAll().size();

        // Create the Spesen

        restSpesenMockMvc.perform(post("/api/spesens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(spesen)))
                .andExpect(status().isCreated());

        // Validate the Spesen in the database
        List<Spesen> spesens = spesenRepository.findAll();
        assertThat(spesens).hasSize(databaseSizeBeforeCreate + 1);
        Spesen testSpesen = spesens.get(spesens.size() - 1);
        assertThat(testSpesen.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testSpesen.getPosten()).isEqualTo(DEFAULT_POSTEN);
        assertThat(testSpesen.getBetrag()).isEqualTo(DEFAULT_BETRAG);
    }

    @Test
    @Transactional
    public void getAllSpesens() throws Exception {
        // Initialize the database
        spesenRepository.saveAndFlush(spesen);

        // Get all the spesens
        restSpesenMockMvc.perform(get("/api/spesens?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(spesen.getId().intValue())))
                .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
                .andExpect(jsonPath("$.[*].posten").value(hasItem(DEFAULT_POSTEN.toString())))
                .andExpect(jsonPath("$.[*].betrag").value(hasItem(DEFAULT_BETRAG.doubleValue())));
    }

    @Test
    @Transactional
    public void getSpesen() throws Exception {
        // Initialize the database
        spesenRepository.saveAndFlush(spesen);

        // Get the spesen
        restSpesenMockMvc.perform(get("/api/spesens/{id}", spesen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(spesen.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.posten").value(DEFAULT_POSTEN.toString()))
            .andExpect(jsonPath("$.betrag").value(DEFAULT_BETRAG.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSpesen() throws Exception {
        // Get the spesen
        restSpesenMockMvc.perform(get("/api/spesens/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpesen() throws Exception {
        // Initialize the database
        spesenRepository.saveAndFlush(spesen);
        int databaseSizeBeforeUpdate = spesenRepository.findAll().size();

        // Update the spesen
        Spesen updatedSpesen = new Spesen();
        updatedSpesen.setId(spesen.getId());
        updatedSpesen.setDatum(UPDATED_DATUM);
        updatedSpesen.setPosten(UPDATED_POSTEN);
        updatedSpesen.setBetrag(UPDATED_BETRAG);

        restSpesenMockMvc.perform(put("/api/spesens")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSpesen)))
                .andExpect(status().isOk());

        // Validate the Spesen in the database
        List<Spesen> spesens = spesenRepository.findAll();
        assertThat(spesens).hasSize(databaseSizeBeforeUpdate);
        Spesen testSpesen = spesens.get(spesens.size() - 1);
        assertThat(testSpesen.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testSpesen.getPosten()).isEqualTo(UPDATED_POSTEN);
        assertThat(testSpesen.getBetrag()).isEqualTo(UPDATED_BETRAG);
    }

    @Test
    @Transactional
    public void deleteSpesen() throws Exception {
        // Initialize the database
        spesenRepository.saveAndFlush(spesen);
        int databaseSizeBeforeDelete = spesenRepository.findAll().size();

        // Get the spesen
        restSpesenMockMvc.perform(delete("/api/spesens/{id}", spesen.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Spesen> spesens = spesenRepository.findAll();
        assertThat(spesens).hasSize(databaseSizeBeforeDelete - 1);
    }
}
