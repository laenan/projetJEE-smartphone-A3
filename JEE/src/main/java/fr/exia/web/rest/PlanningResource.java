package fr.exia.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.exia.domain.Planning;
import fr.exia.repository.PlanningRepository;
import fr.exia.service.PlanningService;

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

/**
 * REST controller for managing planning.
 */
@RestController
@RequestMapping("/api")
public class PlanningResource {

	private final Logger log = LoggerFactory.getLogger(PlanningResource.class);

	@Inject
	private PlanningRepository planningRepository;

	@Inject
	private PlanningService pservice;

	/**
	 * POST /planning -> Create a new planning.
	 */
	@RequestMapping(value = "/planning", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> create(@RequestBody Planning planning)
			throws URISyntaxException {
		log.debug("REST request to save planning : {}", planning);
		if (planning.getId() != null) {
			return ResponseEntity
					.badRequest()
					.header("Failure",
							"A new planning cannot already have an ID").build();
		}
		planningRepository.save(planning);
		return ResponseEntity.created(
				new URI("/api/planning/" + planning.getId())).build();
	}

	/**
	 * PUT /planning -> Updates an existing planning.
	 */
	@RequestMapping(value = "/planning", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> update(@RequestBody Planning planning)
			throws URISyntaxException {
		log.debug("REST request to update Planning : {}", planning);
		if (planning.getId() == null) {
			return create(planning);
		}
		planningRepository.save(planning);
		return ResponseEntity.ok().build();
	}

	/**
	 * GET /planning -> get all the planning.
	 */
	@RequestMapping(value = "/planning", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<Planning> getAll(String plage) {
		if (plage == null) {
			pservice.getPlage(plage);
		}
		log.debug("REST request to get all planning");
		return planningRepository.findAll();
	}

	/**
	 * GET /planning/:id -> get the "id" planning.
	 */
	@RequestMapping(value = "/planning/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Planning> get(@PathVariable Long id) {
		log.debug("REST request to get Planning : {}", id);
		return Optional
				.ofNullable(
						planningRepository.findOneWithEagerRelationships(id))
				.map(planning -> new ResponseEntity<>(planning, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /planning/:id -> delete the "id" planning.
	 */
	@RequestMapping(value = "/planning/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public void delete(@PathVariable Long id) {
		log.debug("REST request to delete Planning : {}", id);
		planningRepository.delete(id);
	}
}
