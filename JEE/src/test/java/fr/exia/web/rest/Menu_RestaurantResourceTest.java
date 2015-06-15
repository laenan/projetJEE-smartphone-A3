package fr.exia.web.rest;

import fr.exia.Application;
import fr.exia.domain.Menu_Restaurant;
import fr.exia.repository.Menu_RestaurantRepository;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Menu_RestaurantResource REST controller.
 *
 * @see Menu_RestaurantResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Menu_RestaurantResourceTest {

    private static final String DEFAULT_ENTREE_NOM = "SAMPLE_TEXT";
    private static final String UPDATED_ENTREE_NOM = "UPDATED_TEXT";
    private static final String DEFAULT_ENTREE_PHOTO = "SAMPLE_TEXT";
    private static final String UPDATED_ENTREE_PHOTO = "UPDATED_TEXT";
    private static final String DEFAULT_PLAT_NOM = "SAMPLE_TEXT";
    private static final String UPDATED_PLAT_NOM = "UPDATED_TEXT";
    private static final String DEFAULT_PLAT_PHOTO = "SAMPLE_TEXT";
    private static final String UPDATED_PLAT_PHOTO = "UPDATED_TEXT";
    private static final String DEFAULT_DESSERT_NOM = "SAMPLE_TEXT";
    private static final String UPDATED_DESSERT_NOM = "UPDATED_TEXT";
    private static final String DEFAULT_DESSERT_PHOTO = "SAMPLE_TEXT";
    private static final String UPDATED_DESSERT_PHOTO = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_TARIF_MENU = new BigDecimal(0);
    private static final BigDecimal UPDATED_TARIF_MENU = new BigDecimal(1);

    @Inject
    private Menu_RestaurantRepository menu_RestaurantRepository;

    private MockMvc restMenu_RestaurantMockMvc;

    private Menu_Restaurant menu_Restaurant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Menu_RestaurantResource menu_RestaurantResource = new Menu_RestaurantResource();
        ReflectionTestUtils.setField(menu_RestaurantResource, "menu_RestaurantRepository", menu_RestaurantRepository);
        this.restMenu_RestaurantMockMvc = MockMvcBuilders.standaloneSetup(menu_RestaurantResource).build();
    }

    @Before
    public void initTest() {
        menu_Restaurant = new Menu_Restaurant();
        menu_Restaurant.setEntree_nom(DEFAULT_ENTREE_NOM);
        menu_Restaurant.setEntree_photo(DEFAULT_ENTREE_PHOTO);
        menu_Restaurant.setPlat_nom(DEFAULT_PLAT_NOM);
        menu_Restaurant.setPlat_photo(DEFAULT_PLAT_PHOTO);
        menu_Restaurant.setDessert_nom(DEFAULT_DESSERT_NOM);
        menu_Restaurant.setDessert_photo(DEFAULT_DESSERT_PHOTO);
        menu_Restaurant.setTarif_menu(DEFAULT_TARIF_MENU);
    }

    @Test
    @Transactional
    public void createMenu_Restaurant() throws Exception {
        int databaseSizeBeforeCreate = menu_RestaurantRepository.findAll().size();

        // Create the Menu_Restaurant
        restMenu_RestaurantMockMvc.perform(post("/api/menu_Restaurants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu_Restaurant)))
                .andExpect(status().isCreated());

        // Validate the Menu_Restaurant in the database
        List<Menu_Restaurant> menu_Restaurants = menu_RestaurantRepository.findAll();
        assertThat(menu_Restaurants).hasSize(databaseSizeBeforeCreate + 1);
        Menu_Restaurant testMenu_Restaurant = menu_Restaurants.get(menu_Restaurants.size() - 1);
        assertThat(testMenu_Restaurant.getEntree_nom()).isEqualTo(DEFAULT_ENTREE_NOM);
        assertThat(testMenu_Restaurant.getEntree_photo()).isEqualTo(DEFAULT_ENTREE_PHOTO);
        assertThat(testMenu_Restaurant.getPlat_nom()).isEqualTo(DEFAULT_PLAT_NOM);
        assertThat(testMenu_Restaurant.getPlat_photo()).isEqualTo(DEFAULT_PLAT_PHOTO);
        assertThat(testMenu_Restaurant.getDessert_nom()).isEqualTo(DEFAULT_DESSERT_NOM);
        assertThat(testMenu_Restaurant.getDessert_photo()).isEqualTo(DEFAULT_DESSERT_PHOTO);
        assertThat(testMenu_Restaurant.getTarif_menu()).isEqualTo(DEFAULT_TARIF_MENU);
    }

    @Test
    @Transactional
    public void getAllMenu_Restaurants() throws Exception {
        // Initialize the database
        menu_RestaurantRepository.saveAndFlush(menu_Restaurant);

        // Get all the menu_Restaurants
        restMenu_RestaurantMockMvc.perform(get("/api/menu_Restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(menu_Restaurant.getId().intValue())))
                .andExpect(jsonPath("$.[*].entree_nom").value(hasItem(DEFAULT_ENTREE_NOM.toString())))
                .andExpect(jsonPath("$.[*].entree_photo").value(hasItem(DEFAULT_ENTREE_PHOTO.toString())))
                .andExpect(jsonPath("$.[*].plat_nom").value(hasItem(DEFAULT_PLAT_NOM.toString())))
                .andExpect(jsonPath("$.[*].plat_photo").value(hasItem(DEFAULT_PLAT_PHOTO.toString())))
                .andExpect(jsonPath("$.[*].dessert_nom").value(hasItem(DEFAULT_DESSERT_NOM.toString())))
                .andExpect(jsonPath("$.[*].dessert_photo").value(hasItem(DEFAULT_DESSERT_PHOTO.toString())))
                .andExpect(jsonPath("$.[*].tarif_menu").value(hasItem(DEFAULT_TARIF_MENU.intValue())));
    }

    @Test
    @Transactional
    public void getMenu_Restaurant() throws Exception {
        // Initialize the database
        menu_RestaurantRepository.saveAndFlush(menu_Restaurant);

        // Get the menu_Restaurant
        restMenu_RestaurantMockMvc.perform(get("/api/menu_Restaurants/{id}", menu_Restaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(menu_Restaurant.getId().intValue()))
            .andExpect(jsonPath("$.entree_nom").value(DEFAULT_ENTREE_NOM.toString()))
            .andExpect(jsonPath("$.entree_photo").value(DEFAULT_ENTREE_PHOTO.toString()))
            .andExpect(jsonPath("$.plat_nom").value(DEFAULT_PLAT_NOM.toString()))
            .andExpect(jsonPath("$.plat_photo").value(DEFAULT_PLAT_PHOTO.toString()))
            .andExpect(jsonPath("$.dessert_nom").value(DEFAULT_DESSERT_NOM.toString()))
            .andExpect(jsonPath("$.dessert_photo").value(DEFAULT_DESSERT_PHOTO.toString()))
            .andExpect(jsonPath("$.tarif_menu").value(DEFAULT_TARIF_MENU.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMenu_Restaurant() throws Exception {
        // Get the menu_Restaurant
        restMenu_RestaurantMockMvc.perform(get("/api/menu_Restaurants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenu_Restaurant() throws Exception {
        // Initialize the database
        menu_RestaurantRepository.saveAndFlush(menu_Restaurant);

		int databaseSizeBeforeUpdate = menu_RestaurantRepository.findAll().size();

        // Update the menu_Restaurant
        menu_Restaurant.setEntree_nom(UPDATED_ENTREE_NOM);
        menu_Restaurant.setEntree_photo(UPDATED_ENTREE_PHOTO);
        menu_Restaurant.setPlat_nom(UPDATED_PLAT_NOM);
        menu_Restaurant.setPlat_photo(UPDATED_PLAT_PHOTO);
        menu_Restaurant.setDessert_nom(UPDATED_DESSERT_NOM);
        menu_Restaurant.setDessert_photo(UPDATED_DESSERT_PHOTO);
        menu_Restaurant.setTarif_menu(UPDATED_TARIF_MENU);
        restMenu_RestaurantMockMvc.perform(put("/api/menu_Restaurants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(menu_Restaurant)))
                .andExpect(status().isOk());

        // Validate the Menu_Restaurant in the database
        List<Menu_Restaurant> menu_Restaurants = menu_RestaurantRepository.findAll();
        assertThat(menu_Restaurants).hasSize(databaseSizeBeforeUpdate);
        Menu_Restaurant testMenu_Restaurant = menu_Restaurants.get(menu_Restaurants.size() - 1);
        assertThat(testMenu_Restaurant.getEntree_nom()).isEqualTo(UPDATED_ENTREE_NOM);
        assertThat(testMenu_Restaurant.getEntree_photo()).isEqualTo(UPDATED_ENTREE_PHOTO);
        assertThat(testMenu_Restaurant.getPlat_nom()).isEqualTo(UPDATED_PLAT_NOM);
        assertThat(testMenu_Restaurant.getPlat_photo()).isEqualTo(UPDATED_PLAT_PHOTO);
        assertThat(testMenu_Restaurant.getDessert_nom()).isEqualTo(UPDATED_DESSERT_NOM);
        assertThat(testMenu_Restaurant.getDessert_photo()).isEqualTo(UPDATED_DESSERT_PHOTO);
        assertThat(testMenu_Restaurant.getTarif_menu()).isEqualTo(UPDATED_TARIF_MENU);
    }

    @Test
    @Transactional
    public void deleteMenu_Restaurant() throws Exception {
        // Initialize the database
        menu_RestaurantRepository.saveAndFlush(menu_Restaurant);

		int databaseSizeBeforeDelete = menu_RestaurantRepository.findAll().size();

        // Get the menu_Restaurant
        restMenu_RestaurantMockMvc.perform(delete("/api/menu_Restaurants/{id}", menu_Restaurant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Menu_Restaurant> menu_Restaurants = menu_RestaurantRepository.findAll();
        assertThat(menu_Restaurants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
