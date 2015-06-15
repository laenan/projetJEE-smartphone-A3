package fr.exia.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.exia.domain.Horaire;
import fr.exia.repository.HoraireRepository;
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
 * REST controller for managing Horaire.
 */
@RestController
@RequestMapping("/api")
public class HoraireResource {

    private final Logger log = LoggerFactory.getLogger(HoraireResource.class);

    @Inject
    private HoraireRepository horaireRepository;

    /**
     * POST  /horaires -> Create a new horaire.
     */
    @RequestMapping(value = "/horaires",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Horaire horaire) throws URISyntaxException {
        log.debug("REST request to save Horaire : {}", horaire);
        if (horaire.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new horaire cannot already have an ID").build();
        }
        horaireRepository.save(horaire);
        return ResponseEntity.created(new URI("/api/horaires/" + horaire.getId())).build();
    }

    /**
     * PUT  /horaires -> Updates an existing horaire.
     */
    @RequestMapping(value = "/horaires",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Horaire horaire) throws URISyntaxException {
        log.debug("REST request to update Horaire : {}", horaire);
        if (horaire.getId() == null) {
            return create(horaire);
        }
        horaireRepository.save(horaire);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /horaires -> get all the horaires.
     */
    @RequestMapping(value = "/horaires",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Horaire> getAll() {
        log.debug("REST request to get all Horaires");
        return horaireRepository.findAll();
    }

    /**
     * GET  /horaires/:id -> get the "id" horaire.
     */
    @RequestMapping(value = "/horaires/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Horaire> get(@PathVariable Long id) {
        log.debug("REST request to get Horaire : {}", id);
        return Optional.ofNullable(horaireRepository.findOne(id))
            .map(horaire -> new ResponseEntity<>(
                horaire,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /horaires/:id -> delete the "id" horaire.
     */
    @RequestMapping(value = "/horaires/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Horaire : {}", id);
        horaireRepository.delete(id);
    }
}
