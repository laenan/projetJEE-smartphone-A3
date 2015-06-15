package fr.exia.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.exia.domain.Parc;
import fr.exia.repository.ParcRepository;
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
 * REST controller for managing Parc.
 */
@RestController
@RequestMapping("/api")
public class ParcResource {

    private final Logger log = LoggerFactory.getLogger(ParcResource.class);

    @Inject
    private ParcRepository parcRepository;

    /**
     * POST  /parcs -> Create a new parc.
     */
    @RequestMapping(value = "/parcs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Parc parc) throws URISyntaxException {
        log.debug("REST request to save Parc : {}", parc);
        if (parc.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new parc cannot already have an ID").build();
        }
        parcRepository.save(parc);
        return ResponseEntity.created(new URI("/api/parcs/" + parc.getId())).build();
    }

    /**
     * PUT  /parcs -> Updates an existing parc.
     */
    @RequestMapping(value = "/parcs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Parc parc) throws URISyntaxException {
        log.debug("REST request to update Parc : {}", parc);
        if (parc.getId() == null) {
            return create(parc);
        }
        parcRepository.save(parc);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /parcs -> get all the parcs.
     */
    @RequestMapping(value = "/parcs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Parc> getAll() {
        log.debug("REST request to get all Parcs");
        return parcRepository.findAll();
    }

    /**
     * GET  /parcs/:id -> get the "id" parc.
     */
    @RequestMapping(value = "/parcs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Parc> get(@PathVariable Long id) {
        log.debug("REST request to get Parc : {}", id);
        return Optional.ofNullable(parcRepository.findOne(id))
            .map(parc -> new ResponseEntity<>(
                parc,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /parcs/:id -> delete the "id" parc.
     */
    @RequestMapping(value = "/parcs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Parc : {}", id);
        parcRepository.delete(id);
    }
}
