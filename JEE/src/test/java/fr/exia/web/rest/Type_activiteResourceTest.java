package fr.exia.web.rest;

import fr.exia.Application;
import fr.exia.domain.Type_activite;
import fr.exia.repository.Type_activiteRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
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
 * Test class for the Type_activiteResource REST controller.
 *
 * @see Type_activiteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Type_activiteResourceTest {

    private static final String DEFAULT_NOM_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_NOM_TYPE = "UPDATED_TEXT";

    @Inject
    private Type_activiteRepository type_activiteRepository;

    private MockMvc restType_activiteMockMvc;

    private Type_activite type_activite;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Type_activiteResource type_activiteResource = new Type_activiteResource();
        ReflectionTestUtils.setField(type_activiteResource, "type_activiteRepository", type_activiteRepository);
        this.restType_activiteMockMvc = MockMvcBuilders.standaloneSetup(type_activiteResource).build();
    }

    @Before
    public void initTest() {
        type_activite = new Type_activite();
        type_activite.setNom_type(DEFAULT_NOM_TYPE);
    }

    @Test
    @Transactional
    public void createType_activite() throws Exception {
        int databaseSizeBeforeCreate = type_activiteRepository.findAll().size();

        // Create the Type_activite
        restType_activiteMockMvc.perform(post("/api/type_activites")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_activite)))
                .andExpect(status().isCreated());

        // Validate the Type_activite in the database
        List<Type_activite> type_activites = type_activiteRepository.findAll();
        assertThat(type_activites).hasSize(databaseSizeBeforeCreate + 1);
        Type_activite testType_activite = type_activites.get(type_activites.size() - 1);
        assertThat(testType_activite.getNom_type()).isEqualTo(DEFAULT_NOM_TYPE);
    }

    @Test
    @Transactional
    public void getAllType_activites() throws Exception {
        // Initialize the database
        type_activiteRepository.saveAndFlush(type_activite);

        // Get all the type_activites
        restType_activiteMockMvc.perform(get("/api/type_activites"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(type_activite.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom_type").value(hasItem(DEFAULT_NOM_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getType_activite() throws Exception {
        // Initialize the database
        type_activiteRepository.saveAndFlush(type_activite);

        // Get the type_activite
        restType_activiteMockMvc.perform(get("/api/type_activites/{id}", type_activite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(type_activite.getId().intValue()))
            .andExpect(jsonPath("$.nom_type").value(DEFAULT_NOM_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingType_activite() throws Exception {
        // Get the type_activite
        restType_activiteMockMvc.perform(get("/api/type_activites/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType_activite() throws Exception {
        // Initialize the database
        type_activiteRepository.saveAndFlush(type_activite);

		int databaseSizeBeforeUpdate = type_activiteRepository.findAll().size();

        // Update the type_activite
        type_activite.setNom_type(UPDATED_NOM_TYPE);
        restType_activiteMockMvc.perform(put("/api/type_activites")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_activite)))
                .andExpect(status().isOk());

        // Validate the Type_activite in the database
        List<Type_activite> type_activites = type_activiteRepository.findAll();
        assertThat(type_activites).hasSize(databaseSizeBeforeUpdate);
        Type_activite testType_activite = type_activites.get(type_activites.size() - 1);
        assertThat(testType_activite.getNom_type()).isEqualTo(UPDATED_NOM_TYPE);
    }

    @Test
    @Transactional
    public void deleteType_activite() throws Exception {
        // Initialize the database
        type_activiteRepository.saveAndFlush(type_activite);

		int databaseSizeBeforeDelete = type_activiteRepository.findAll().size();

        // Get the type_activite
        restType_activiteMockMvc.perform(delete("/api/type_activites/{id}", type_activite.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Type_activite> type_activites = type_activiteRepository.findAll();
        assertThat(type_activites).hasSize(databaseSizeBeforeDelete - 1);
    }
}
