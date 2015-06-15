package fr.exia.web.rest;

import fr.exia.Application;
import fr.exia.domain.Horaire;
import fr.exia.repository.HoraireRepository;

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
 * Test class for the HoraireResource REST controller.
 *
 * @see HoraireResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HoraireResourceTest {


    private static final LocalDate DEFAULT_DATE_DEBUT = new LocalDate(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = new LocalDate();

    @Inject
    private HoraireRepository horaireRepository;

    private MockMvc restHoraireMockMvc;

    private Horaire horaire;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HoraireResource horaireResource = new HoraireResource();
        ReflectionTestUtils.setField(horaireResource, "horaireRepository", horaireRepository);
        this.restHoraireMockMvc = MockMvcBuilders.standaloneSetup(horaireResource).build();
    }

    @Before
    public void initTest() {
        horaire = new Horaire();
        horaire.setDate_debut(DEFAULT_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void createHoraire() throws Exception {
        int databaseSizeBeforeCreate = horaireRepository.findAll().size();

        // Create the Horaire
        restHoraireMockMvc.perform(post("/api/horaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(horaire)))
                .andExpect(status().isCreated());

        // Validate the Horaire in the database
        List<Horaire> horaires = horaireRepository.findAll();
        assertThat(horaires).hasSize(databaseSizeBeforeCreate + 1);
        Horaire testHoraire = horaires.get(horaires.size() - 1);
        assertThat(testHoraire.getDate_debut()).isEqualTo(DEFAULT_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllHoraires() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get all the horaires
        restHoraireMockMvc.perform(get("/api/horaires"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(horaire.getId().intValue())))
                .andExpect(jsonPath("$.[*].date_debut").value(hasItem(DEFAULT_DATE_DEBUT.toString())));
    }

    @Test
    @Transactional
    public void getHoraire() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

        // Get the horaire
        restHoraireMockMvc.perform(get("/api/horaires/{id}", horaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(horaire.getId().intValue()))
            .andExpect(jsonPath("$.date_debut").value(DEFAULT_DATE_DEBUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHoraire() throws Exception {
        // Get the horaire
        restHoraireMockMvc.perform(get("/api/horaires/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHoraire() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

		int databaseSizeBeforeUpdate = horaireRepository.findAll().size();

        // Update the horaire
        horaire.setDate_debut(UPDATED_DATE_DEBUT);
        restHoraireMockMvc.perform(put("/api/horaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(horaire)))
                .andExpect(status().isOk());

        // Validate the Horaire in the database
        List<Horaire> horaires = horaireRepository.findAll();
        assertThat(horaires).hasSize(databaseSizeBeforeUpdate);
        Horaire testHoraire = horaires.get(horaires.size() - 1);
        assertThat(testHoraire.getDate_debut()).isEqualTo(UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void deleteHoraire() throws Exception {
        // Initialize the database
        horaireRepository.saveAndFlush(horaire);

		int databaseSizeBeforeDelete = horaireRepository.findAll().size();

        // Get the horaire
        restHoraireMockMvc.perform(delete("/api/horaires/{id}", horaire.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Horaire> horaires = horaireRepository.findAll();
        assertThat(horaires).hasSize(databaseSizeBeforeDelete - 1);
    }
}
