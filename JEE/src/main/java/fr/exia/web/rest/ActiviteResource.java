package fr.exia.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.exia.domain.Activite;
import fr.exia.repository.ActiviteRepository;
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
 * REST controller for managing Activite.
 */
@RestController
@RequestMapping("/api")
public class ActiviteResource {

    private final Logger log = LoggerFactory.getLogger(ActiviteResource.class);

    @Inject
    private ActiviteRepository activiteRepository;

    /**
     * POST  /activites -> Create a new activite.
     */
    @RequestMapping(value = "/activites",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Activite activite) throws URISyntaxException {
        log.debug("REST request to save Activite : {}", activite);
        if (activite.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new activite cannot already have an ID").build();
        }
        activiteRepository.save(activite);
        return ResponseEntity.created(new URI("/api/activites/" + activite.getId())).build();
    }

    /**
     * PUT  /activites -> Updates an existing activite.
     */
    @RequestMapping(value = "/activites",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Activite activite) throws URISyntaxException {
        log.debug("REST request to update Activite : {}", activite);
        if (activite.getId() == null) {
            return create(activite);
        }
        activiteRepository.save(activite);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /activites -> get all the activites.
     */
    @RequestMapping(value = "/activites",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Activite> getAll() {
        log.debug("REST request to get all Activites");
        return activiteRepository.findAll();
    }

    /**
     * GET  /activites/:id -> get the "id" activite.
     */
    @RequestMapping(value = "/activites/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Activite> get(@PathVariable Long id) {
        log.debug("REST request to get Activite : {}", id);
        return Optional.ofNullable(activiteRepository.findOneWithEagerRelationships(id))
            .map(activite -> new ResponseEntity<>(
                activite,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /activites/:id -> delete the "id" activite.
     */
    @RequestMapping(value = "/activites/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Activite : {}", id);
        activiteRepository.delete(id);
    }
}
