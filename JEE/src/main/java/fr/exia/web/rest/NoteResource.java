package fr.exia.web.rest;

import com.codahale.metrics.annotation.Timed;

import fr.exia.domain.Note;
import fr.exia.repository.NoteRepository;
import fr.exia.service.NotationService;

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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Note.
 */
@RestController
@RequestMapping("/api")
public class NoteResource {

    private final Logger log = LoggerFactory.getLogger(NoteResource.class);

    @Inject
    private NoteRepository noteRepository;
    
    @Inject
    private NotationService nservice;

    /**
     * POST  /notes -> Create a new note.
     */
    @RequestMapping(value = "/notes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Async
    public ResponseEntity<Void> create(@RequestBody Note note) throws URISyntaxException {
        log.debug("REST request to save Note : {}", note);
        if (note.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new note cannot already have an ID").build();
        }
        if (!nservice.verif(note)){
        	return (ResponseEntity<Void>) ResponseEntity.badRequest();
        }
        noteRepository.save(note);
        return ResponseEntity.created(new URI("/api/notes/" + note.getId())).build();
    }

    /**
     * PUT  /notes -> Updates an existing note.
     */
    @RequestMapping(value = "/notes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Async
    public ResponseEntity<Void> update(@RequestBody Note note) throws URISyntaxException {
        log.debug("REST request to update Note : {}", note);
        if (note.getId() == null) {
            return create(note);
        }
        noteRepository.save(note);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /notes -> get all the notes.
     */
    @RequestMapping(value = "/notes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Async
    public List<Note> getAll() {
        log.debug("REST request to get all Notes");
        return noteRepository.findAll();
    }

    /**
     * GET  /notes/:id -> get the "id" note.
     */
    @RequestMapping(value = "/notes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Async
    public ResponseEntity<Note> get(@PathVariable Long id) {
        log.debug("REST request to get Note : {}", id);
        return Optional.ofNullable(noteRepository.findOne(id))
            .map(note -> new ResponseEntity<>(
                note,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /notes/:id -> delete the "id" note.
     */
    @RequestMapping(value = "/notes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Async
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Note : {}", id);
        noteRepository.delete(id);
    }
}
