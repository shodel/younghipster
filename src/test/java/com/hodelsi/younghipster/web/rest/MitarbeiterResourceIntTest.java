package com.hodelsi.younghipster.web.rest;

import com.hodelsi.younghipster.YounghipsterApp;
import com.hodelsi.younghipster.domain.Mitarbeiter;
import com.hodelsi.younghipster.repository.MitarbeiterRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MitarbeiterResource REST controller.
 *
 * @see MitarbeiterResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = YounghipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class MitarbeiterResourceIntTest {

    private static final String DEFAULT_VORNAME = "AAAAA";
    private static final String UPDATED_VORNAME = "BBBBB";
    private static final String DEFAULT_NACHNAME = "AAAAA";
    private static final String UPDATED_NACHNAME = "BBBBB";
    private static final String DEFAULT_ROLLE = "AAAAA";
    private static final String UPDATED_ROLLE = "BBBBB";

    private static final Integer DEFAULT_WOCHENSTUNDEN = 1;
    private static final Integer UPDATED_WOCHENSTUNDEN = 2;

    @Inject
    private MitarbeiterRepository mitarbeiterRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMitarbeiterMockMvc;

    private Mitarbeiter mitarbeiter;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MitarbeiterResource mitarbeiterResource = new MitarbeiterResource();
        ReflectionTestUtils.setField(mitarbeiterResource, "mitarbeiterRepository", mitarbeiterRepository);
        this.restMitarbeiterMockMvc = MockMvcBuilders.standaloneSetup(mitarbeiterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        mitarbeiter = new Mitarbeiter();
        mitarbeiter.setVorname(DEFAULT_VORNAME);
        mitarbeiter.setNachname(DEFAULT_NACHNAME);
        mitarbeiter.setRolle(DEFAULT_ROLLE);
        mitarbeiter.setWochenstunden(DEFAULT_WOCHENSTUNDEN);
    }

    @Test
    @Transactional
    public void createMitarbeiter() throws Exception {
        int databaseSizeBeforeCreate = mitarbeiterRepository.findAll().size();

        // Create the Mitarbeiter

        restMitarbeiterMockMvc.perform(post("/api/mitarbeiters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mitarbeiter)))
                .andExpect(status().isCreated());

        // Validate the Mitarbeiter in the database
        List<Mitarbeiter> mitarbeiters = mitarbeiterRepository.findAll();
        assertThat(mitarbeiters).hasSize(databaseSizeBeforeCreate + 1);
        Mitarbeiter testMitarbeiter = mitarbeiters.get(mitarbeiters.size() - 1);
        assertThat(testMitarbeiter.getVorname()).isEqualTo(DEFAULT_VORNAME);
        assertThat(testMitarbeiter.getNachname()).isEqualTo(DEFAULT_NACHNAME);
        assertThat(testMitarbeiter.getRolle()).isEqualTo(DEFAULT_ROLLE);
        assertThat(testMitarbeiter.getWochenstunden()).isEqualTo(DEFAULT_WOCHENSTUNDEN);
    }

    @Test
    @Transactional
    public void getAllMitarbeiters() throws Exception {
        // Initialize the database
        mitarbeiterRepository.saveAndFlush(mitarbeiter);

        // Get all the mitarbeiters
        restMitarbeiterMockMvc.perform(get("/api/mitarbeiters?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(mitarbeiter.getId().intValue())))
                .andExpect(jsonPath("$.[*].vorname").value(hasItem(DEFAULT_VORNAME.toString())))
                .andExpect(jsonPath("$.[*].nachname").value(hasItem(DEFAULT_NACHNAME.toString())))
                .andExpect(jsonPath("$.[*].rolle").value(hasItem(DEFAULT_ROLLE.toString())))
                .andExpect(jsonPath("$.[*].wochenstunden").value(hasItem(DEFAULT_WOCHENSTUNDEN)));
    }

    @Test
    @Transactional
    public void getMitarbeiter() throws Exception {
        // Initialize the database
        mitarbeiterRepository.saveAndFlush(mitarbeiter);

        // Get the mitarbeiter
        restMitarbeiterMockMvc.perform(get("/api/mitarbeiters/{id}", mitarbeiter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(mitarbeiter.getId().intValue()))
            .andExpect(jsonPath("$.vorname").value(DEFAULT_VORNAME.toString()))
            .andExpect(jsonPath("$.nachname").value(DEFAULT_NACHNAME.toString()))
            .andExpect(jsonPath("$.rolle").value(DEFAULT_ROLLE.toString()))
            .andExpect(jsonPath("$.wochenstunden").value(DEFAULT_WOCHENSTUNDEN));
    }

    @Test
    @Transactional
    public void getNonExistingMitarbeiter() throws Exception {
        // Get the mitarbeiter
        restMitarbeiterMockMvc.perform(get("/api/mitarbeiters/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMitarbeiter() throws Exception {
        // Initialize the database
        mitarbeiterRepository.saveAndFlush(mitarbeiter);
        int databaseSizeBeforeUpdate = mitarbeiterRepository.findAll().size();

        // Update the mitarbeiter
        Mitarbeiter updatedMitarbeiter = new Mitarbeiter();
        updatedMitarbeiter.setId(mitarbeiter.getId());
        updatedMitarbeiter.setVorname(UPDATED_VORNAME);
        updatedMitarbeiter.setNachname(UPDATED_NACHNAME);
        updatedMitarbeiter.setRolle(UPDATED_ROLLE);
        updatedMitarbeiter.setWochenstunden(UPDATED_WOCHENSTUNDEN);

        restMitarbeiterMockMvc.perform(put("/api/mitarbeiters")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMitarbeiter)))
                .andExpect(status().isOk());

        // Validate the Mitarbeiter in the database
        List<Mitarbeiter> mitarbeiters = mitarbeiterRepository.findAll();
        assertThat(mitarbeiters).hasSize(databaseSizeBeforeUpdate);
        Mitarbeiter testMitarbeiter = mitarbeiters.get(mitarbeiters.size() - 1);
        assertThat(testMitarbeiter.getVorname()).isEqualTo(UPDATED_VORNAME);
        assertThat(testMitarbeiter.getNachname()).isEqualTo(UPDATED_NACHNAME);
        assertThat(testMitarbeiter.getRolle()).isEqualTo(UPDATED_ROLLE);
        assertThat(testMitarbeiter.getWochenstunden()).isEqualTo(UPDATED_WOCHENSTUNDEN);
    }

    @Test
    @Transactional
    public void deleteMitarbeiter() throws Exception {
        // Initialize the database
        mitarbeiterRepository.saveAndFlush(mitarbeiter);
        int databaseSizeBeforeDelete = mitarbeiterRepository.findAll().size();

        // Get the mitarbeiter
        restMitarbeiterMockMvc.perform(delete("/api/mitarbeiters/{id}", mitarbeiter.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Mitarbeiter> mitarbeiters = mitarbeiterRepository.findAll();
        assertThat(mitarbeiters).hasSize(databaseSizeBeforeDelete - 1);
    }
}
