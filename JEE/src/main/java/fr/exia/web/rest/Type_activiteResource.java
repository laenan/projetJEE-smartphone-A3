package fr.exia.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.exia.domain.Type_activite;
import fr.exia.repository.Type_activiteRepository;
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
 * REST controller for managing Type_activite.
 */
@RestController
@RequestMapping("/api")
public class Type_activiteResource {

    private final Logger log = LoggerFactory.getLogger(Type_activiteResource.class);

    @Inject
    private Type_activiteRepository type_activiteRepository;

    /**
     * POST  /type_activites -> Create a new type_activite.
     */
    @RequestMapping(value = "/type_activites",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Type_activite type_activite) throws URISyntaxException {
        log.debug("REST request to save Type_activite : {}", type_activite);
        if (type_activite.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new type_activite cannot already have an ID").build();
        }
        type_activiteRepository.save(type_activite);
        return ResponseEntity.created(new URI("/api/type_activites/" + type_activite.getId())).build();
    }

    /**
     * PUT  /type_activites -> Updates an existing type_activite.
     */
    @RequestMapping(value = "/type_activites",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Type_activite type_activite) throws URISyntaxException {
        log.debug("REST request to update Type_activite : {}", type_activite);
        if (type_activite.getId() == null) {
            return create(type_activite);
        }
        type_activiteRepository.save(type_activite);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /type_activites -> get all the type_activites.
     */
    @RequestMapping(value = "/type_activites",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Type_activite> getAll() {
        log.debug("REST request to get all Type_activites");
        return type_activiteRepository.findAll();
    }

    /**
     * GET  /type_activites/:id -> get the "id" type_activite.
     */
    @RequestMapping(value = "/type_activites/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_activite> get(@PathVariable Long id) {
        log.debug("REST request to get Type_activite : {}", id);
        return Optional.ofNullable(type_activiteRepository.findOne(id))
            .map(type_activite -> new ResponseEntity<>(
                type_activite,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type_activites/:id -> delete the "id" type_activite.
     */
    @RequestMapping(value = "/type_activites/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Type_activite : {}", id);
        type_activiteRepository.delete(id);
    }
}
