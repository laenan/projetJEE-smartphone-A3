package fr.exia.web.rest;

import fr.exia.Application;
import fr.exia.domain.Parc;
import fr.exia.repository.ParcRepository;

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
 * Test class for the ParcResource REST controller.
 *
 * @see ParcResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ParcResourceTest {

    private static final String DEFAULT_DESCRIPTION_PARC = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION_PARC = "UPDATED_TEXT";

    @Inject
    private ParcRepository parcRepository;

    private MockMvc restParcMockMvc;

    private Parc parc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParcResource parcResource = new ParcResource();
        ReflectionTestUtils.setField(parcResource, "parcRepository", parcRepository);
        this.restParcMockMvc = MockMvcBuilders.standaloneSetup(parcResource).build();
    }

    @Before
    public void initTest() {
        parc = new Parc();
        parc.setDescription_parc(DEFAULT_DESCRIPTION_PARC);
    }

    @Test
    @Transactional
    public void createParc() throws Exception {
        int databaseSizeBeforeCreate = parcRepository.findAll().size();

        // Create the Parc
        restParcMockMvc.perform(post("/api/parcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(parc)))
                .andExpect(status().isCreated());

        // Validate the Parc in the database
        List<Parc> parcs = parcRepository.findAll();
        assertThat(parcs).hasSize(databaseSizeBeforeCreate + 1);
        Parc testParc = parcs.get(parcs.size() - 1);
        assertThat(testParc.getDescription_parc()).isEqualTo(DEFAULT_DESCRIPTION_PARC);
    }

    @Test
    @Transactional
    public void getAllParcs() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

        // Get all the parcs
        restParcMockMvc.perform(get("/api/parcs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(parc.getId().intValue())))
                .andExpect(jsonPath("$.[*].description_parc").value(hasItem(DEFAULT_DESCRIPTION_PARC.toString())));
    }

    @Test
    @Transactional
    public void getParc() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

        // Get the parc
        restParcMockMvc.perform(get("/api/parcs/{id}", parc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(parc.getId().intValue()))
            .andExpect(jsonPath("$.description_parc").value(DEFAULT_DESCRIPTION_PARC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParc() throws Exception {
        // Get the parc
        restParcMockMvc.perform(get("/api/parcs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParc() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

		int databaseSizeBeforeUpdate = parcRepository.findAll().size();

        // Update the parc
        parc.setDescription_parc(UPDATED_DESCRIPTION_PARC);
        restParcMockMvc.perform(put("/api/parcs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(parc)))
                .andExpect(status().isOk());

        // Validate the Parc in the database
        List<Parc> parcs = parcRepository.findAll();
        assertThat(parcs).hasSize(databaseSizeBeforeUpdate);
        Parc testParc = parcs.get(parcs.size() - 1);
        assertThat(testParc.getDescription_parc()).isEqualTo(UPDATED_DESCRIPTION_PARC);
    }

    @Test
    @Transactional
    public void deleteParc() throws Exception {
        // Initialize the database
        parcRepository.saveAndFlush(parc);

		int databaseSizeBeforeDelete = parcRepository.findAll().size();

        // Get the parc
        restParcMockMvc.perform(delete("/api/parcs/{id}", parc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Parc> parcs = parcRepository.findAll();
        assertThat(parcs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
