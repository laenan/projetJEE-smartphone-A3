package fr.exia.web.rest;

import com.codahale.metrics.annotation.Timed;






import fr.exia.domain.Activite;
import fr.exia.repository.ActiviteRepository;
import fr.exia.service.PlanningService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/**
 * REST controller for managing planning.
 */
@RestController
@RequestMapping("/api")
public class PlanningResource {

	private final Logger log = LoggerFactory.getLogger(PlanningResource.class);



	@Inject
	private PlanningService pservice;

	/**
	 * GET /planning -> get planning opti.
	 */

	@RequestMapping(value = "/planning", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Async
	public List<Activite> getPlaningOpti() {
		log.debug("REST request to get the best planning");
		return pservice.planingOpti((Integer.parseInt(new Date().getHours()*100+new Date().getMinutes()+"")));
	}

}
