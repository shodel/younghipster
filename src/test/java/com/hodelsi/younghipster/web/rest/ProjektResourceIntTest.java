package com.hodelsi.younghipster.web.rest;

import com.hodelsi.younghipster.YounghipsterApp;
import com.hodelsi.younghipster.domain.Projekt;
import com.hodelsi.younghipster.repository.ProjektRepository;

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
 * Test class for the ProjektResource REST controller.
 *
 * @see ProjektResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = YounghipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProjektResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private ProjektRepository projektRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjektMockMvc;

    private Projekt projekt;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProjektResource projektResource = new ProjektResource();
        ReflectionTestUtils.setField(projektResource, "projektRepository", projektRepository);
        this.restProjektMockMvc = MockMvcBuilders.standaloneSetup(projektResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        projekt = new Projekt();
        projekt.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProjekt() throws Exception {
        int databaseSizeBeforeCreate = projektRepository.findAll().size();

        // Create the Projekt

        restProjektMockMvc.perform(post("/api/projekts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(projekt)))
                .andExpect(status().isCreated());

        // Validate the Projekt in the database
        List<Projekt> projekts = projektRepository.findAll();
        assertThat(projekts).hasSize(databaseSizeBeforeCreate + 1);
        Projekt testProjekt = projekts.get(projekts.size() - 1);
        assertThat(testProjekt.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllProjekts() throws Exception {
        // Initialize the database
        projektRepository.saveAndFlush(projekt);

        // Get all the projekts
        restProjektMockMvc.perform(get("/api/projekts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(projekt.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProjekt() throws Exception {
        // Initialize the database
        projektRepository.saveAndFlush(projekt);

        // Get the projekt
        restProjektMockMvc.perform(get("/api/projekts/{id}", projekt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(projekt.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjekt() throws Exception {
        // Get the projekt
        restProjektMockMvc.perform(get("/api/projekts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjekt() throws Exception {
        // Initialize the database
        projektRepository.saveAndFlush(projekt);
        int databaseSizeBeforeUpdate = projektRepository.findAll().size();

        // Update the projekt
        Projekt updatedProjekt = new Projekt();
        updatedProjekt.setId(projekt.getId());
        updatedProjekt.setName(UPDATED_NAME);

        restProjektMockMvc.perform(put("/api/projekts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProjekt)))
                .andExpect(status().isOk());

        // Validate the Projekt in the database
        List<Projekt> projekts = projektRepository.findAll();
        assertThat(projekts).hasSize(databaseSizeBeforeUpdate);
        Projekt testProjekt = projekts.get(projekts.size() - 1);
        assertThat(testProjekt.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteProjekt() throws Exception {
        // Initialize the database
        projektRepository.saveAndFlush(projekt);
        int databaseSizeBeforeDelete = projektRepository.findAll().size();

        // Get the projekt
        restProjektMockMvc.perform(delete("/api/projekts/{id}", projekt.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Projekt> projekts = projektRepository.findAll();
        assertThat(projekts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
