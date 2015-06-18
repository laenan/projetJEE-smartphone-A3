package fr.exia.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.exia.domain.Menu_Restaurant;
import fr.exia.repository.Menu_RestaurantRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Menu_Restaurant.
 */
@RestController
@RequestMapping("/api")
public class Menu_RestaurantResource {

    private final Logger log = LoggerFactory.getLogger(Menu_RestaurantResource.class);

    @Inject
    private Menu_RestaurantRepository menu_RestaurantRepository;

    /**
     * POST  /menu_Restaurants -> Create a new menu_Restaurant.
     */
    @RequestMapping(value = "/menu_Restaurants",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Menu_Restaurant menu_Restaurant) throws URISyntaxException {
        log.debug("REST request to save Menu_Restaurant : {}", menu_Restaurant);
        if (menu_Restaurant.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new menu_Restaurant cannot already have an ID").build();
        }
        menu_RestaurantRepository.save(menu_Restaurant);
        return ResponseEntity.created(new URI("/api/menu_Restaurants/" + menu_Restaurant.getId())).build();
    }

    /**
     * PUT  /menu_Restaurants -> Updates an existing menu_Restaurant.
     */
    @RequestMapping(value = "/menu_Restaurants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Menu_Restaurant menu_Restaurant) throws URISyntaxException {
        log.debug("REST request to update Menu_Restaurant : {}", menu_Restaurant);
        if (menu_Restaurant.getId() == null) {
            return create(menu_Restaurant);
        }
        menu_RestaurantRepository.save(menu_Restaurant);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /menu_Restaurants -> get all the menu_Restaurants.
     */
    @RequestMapping(value = "/menu_Restaurants",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Async
    public List<Menu_Restaurant> getAll() {
        log.debug("REST request to get all Menu_Restaurants");
        return menu_RestaurantRepository.findAll();
    }

    /**
     * GET  /menu_Restaurants/:id -> get the "id" menu_Restaurant.
     */
    @RequestMapping(value = "/menu_Restaurants/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Async
    public ResponseEntity<Menu_Restaurant> get(@PathVariable Long id) {
        log.debug("REST request to get Menu_Restaurant : {}", id);
        return Optional.ofNullable(menu_RestaurantRepository.findOne(id))
            .map(menu_Restaurant -> new ResponseEntity<>(
                menu_Restaurant,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /menu_Restaurants/:id -> delete the "id" menu_Restaurant.
     */
    @RequestMapping(value = "/menu_Restaurants/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Menu_Restaurant : {}", id);
        menu_RestaurantRepository.delete(id);
    }
}
