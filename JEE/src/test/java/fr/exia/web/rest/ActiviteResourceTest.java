package fr.exia.web.rest;

import fr.exia.Application;
import fr.exia.domain.Activite;
import fr.exia.repository.ActiviteRepository;

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
import org.joda.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActiviteResource REST controller.
 *
 * @see ActiviteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ActiviteResourceTest {

    private static final String DEFAULT_NOM_ACTIVITE = "SAMPLE_TEXT";
    private static final String UPDATED_NOM_ACTIVITE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION_ACTIVITE = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION_ACTIVITE = "UPDATED_TEXT";

    private static final Integer DEFAULT_DUREE_ACTIVITE = 0;
    private static final Integer UPDATED_DUREE_ACTIVITE = 1;
    private static final String DEFAULT_PHOTO_ACTIVITE = "SAMPLE_TEXT";
    private static final String UPDATED_PHOTO_ACTIVITE = "UPDATED_TEXT";

    private static final LocalDate DEFAULT_DATE_CREATION = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE_CREATION = new LocalDate();

    private static final Integer DEFAULT_NB_ACTEURS = 0;
    private static final Integer UPDATED_NB_ACTEURS = 1;
    private static final String DEFAULT_EVT_HISTORIQUE = "SAMPLE_TEXT";
    private static final String UPDATED_EVT_HISTORIQUE = "UPDATED_TEXT";

    private static final Integer DEFAULT_POSITION = 0;
    private static final Integer UPDATED_POSITION = 1;

    @Inject
    private ActiviteRepository activiteRepository;

    private MockMvc restActiviteMockMvc;

    private Activite activite;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActiviteResource activiteResource = new ActiviteResource();
        ReflectionTestUtils.setField(activiteResource, "activiteRepository", activiteRepository);
        this.restActiviteMockMvc = MockMvcBuilders.standaloneSetup(activiteResource).build();
    }

    @Before
    public void initTest() {
        activite = new Activite();
        activite.setNom_activite(DEFAULT_NOM_ACTIVITE);
        activite.setDescription_activite(DEFAULT_DESCRIPTION_ACTIVITE);
        activite.setDuree_activite(DEFAULT_DUREE_ACTIVITE);
        activite.setPhoto_activite(DEFAULT_PHOTO_ACTIVITE);
        activite.setDate_creation(DEFAULT_DATE_CREATION);
        activite.setNb_acteurs(DEFAULT_NB_ACTEURS);
        activite.setEvt_historique(DEFAULT_EVT_HISTORIQUE);
        activite.setPosition(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    public void createActivite() throws Exception {
        int databaseSizeBeforeCreate = activiteRepository.findAll().size();

        // Create the Activite
        restActiviteMockMvc.perform(post("/api/activites")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activite)))
                .andExpect(status().isCreated());

        // Validate the Activite in the database
        List<Activite> activites = activiteRepository.findAll();
        assertThat(activites).hasSize(databaseSizeBeforeCreate + 1);
        Activite testActivite = activites.get(activites.size() - 1);
        assertThat(testActivite.getNom_activite()).isEqualTo(DEFAULT_NOM_ACTIVITE);
        assertThat(testActivite.getDescription_activite()).isEqualTo(DEFAULT_DESCRIPTION_ACTIVITE);
        assertThat(testActivite.getDuree_activite()).isEqualTo(DEFAULT_DUREE_ACTIVITE);
        assertThat(testActivite.getPhoto_activite()).isEqualTo(DEFAULT_PHOTO_ACTIVITE);
        assertThat(testActivite.getDate_creation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testActivite.getNb_acteurs()).isEqualTo(DEFAULT_NB_ACTEURS);
        assertThat(testActivite.getEvt_historique()).isEqualTo(DEFAULT_EVT_HISTORIQUE);
        assertThat(testActivite.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    public void getAllActivites() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get all the activites
        restActiviteMockMvc.perform(get("/api/activites"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(activite.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom_activite").value(hasItem(DEFAULT_NOM_ACTIVITE.toString())))
                .andExpect(jsonPath("$.[*].description_activite").value(hasItem(DEFAULT_DESCRIPTION_ACTIVITE.toString())))
                .andExpect(jsonPath("$.[*].duree_activite").value(hasItem(DEFAULT_DUREE_ACTIVITE)))
                .andExpect(jsonPath("$.[*].photo_activite").value(hasItem(DEFAULT_PHOTO_ACTIVITE.toString())))
                .andExpect(jsonPath("$.[*].date_creation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
                .andExpect(jsonPath("$.[*].nb_acteurs").value(hasItem(DEFAULT_NB_ACTEURS)))
                .andExpect(jsonPath("$.[*].evt_historique").value(hasItem(DEFAULT_EVT_HISTORIQUE.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }

    @Test
    @Transactional
    public void getActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", activite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(activite.getId().intValue()))
            .andExpect(jsonPath("$.nom_activite").value(DEFAULT_NOM_ACTIVITE.toString()))
            .andExpect(jsonPath("$.description_activite").value(DEFAULT_DESCRIPTION_ACTIVITE.toString()))
            .andExpect(jsonPath("$.duree_activite").value(DEFAULT_DUREE_ACTIVITE))
            .andExpect(jsonPath("$.photo_activite").value(DEFAULT_PHOTO_ACTIVITE.toString()))
            .andExpect(jsonPath("$.date_creation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.nb_acteurs").value(DEFAULT_NB_ACTEURS))
            .andExpect(jsonPath("$.evt_historique").value(DEFAULT_EVT_HISTORIQUE.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }

    @Test
    @Transactional
    public void getNonExistingActivite() throws Exception {
        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

		int databaseSizeBeforeUpdate = activiteRepository.findAll().size();

        // Update the activite
        activite.setNom_activite(UPDATED_NOM_ACTIVITE);
        activite.setDescription_activite(UPDATED_DESCRIPTION_ACTIVITE);
        activite.setDuree_activite(UPDATED_DUREE_ACTIVITE);
        activite.setPhoto_activite(UPDATED_PHOTO_ACTIVITE);
        activite.setDate_creation(UPDATED_DATE_CREATION);
        activite.setNb_acteurs(UPDATED_NB_ACTEURS);
        activite.setEvt_historique(UPDATED_EVT_HISTORIQUE);
        activite.setPosition(UPDATED_POSITION);
        restActiviteMockMvc.perform(put("/api/activites")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(activite)))
                .andExpect(status().isOk());

        // Validate the Activite in the database
        List<Activite> activites = activiteRepository.findAll();
        assertThat(activites).hasSize(databaseSizeBeforeUpdate);
        Activite testActivite = activites.get(activites.size() - 1);
        assertThat(testActivite.getNom_activite()).isEqualTo(UPDATED_NOM_ACTIVITE);
        assertThat(testActivite.getDescription_activite()).isEqualTo(UPDATED_DESCRIPTION_ACTIVITE);
        assertThat(testActivite.getDuree_activite()).isEqualTo(UPDATED_DUREE_ACTIVITE);
        assertThat(testActivite.getPhoto_activite()).isEqualTo(UPDATED_PHOTO_ACTIVITE);
        assertThat(testActivite.getDate_creation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testActivite.getNb_acteurs()).isEqualTo(UPDATED_NB_ACTEURS);
        assertThat(testActivite.getEvt_historique()).isEqualTo(UPDATED_EVT_HISTORIQUE);
        assertThat(testActivite.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void deleteActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

		int databaseSizeBeforeDelete = activiteRepository.findAll().size();

        // Get the activite
        restActiviteMockMvc.perform(delete("/api/activites/{id}", activite.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Activite> activites = activiteRepository.findAll();
        assertThat(activites).hasSize(databaseSizeBeforeDelete - 1);
    }
}
