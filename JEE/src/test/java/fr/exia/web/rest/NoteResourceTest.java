package fr.exia.web.rest;

import fr.exia.Application;
import fr.exia.domain.Note;
import fr.exia.repository.NoteRepository;

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
 * Test class for the NoteResource REST controller.
 *
 * @see NoteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NoteResourceTest {

    private static final String DEFAULT_KEY_API = "SAMPLE_TEXT";
    private static final String UPDATED_KEY_API = "UPDATED_TEXT";

    private static final Integer DEFAULT_NOTE = 0;
    private static final Integer UPDATED_NOTE = 1;
    private static final String DEFAULT_COMMENTAIRE_NOTE = "SAMPLE_TEXT";
    private static final String UPDATED_COMMENTAIRE_NOTE = "UPDATED_TEXT";

    @Inject
    private NoteRepository noteRepository;

    private MockMvc restNoteMockMvc;

    private Note note;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NoteResource noteResource = new NoteResource();
        ReflectionTestUtils.setField(noteResource, "noteRepository", noteRepository);
        this.restNoteMockMvc = MockMvcBuilders.standaloneSetup(noteResource).build();
    }

    @Before
    public void initTest() {
        note = new Note();
        note.setKeyAPI(DEFAULT_KEY_API);
        note.setNote(DEFAULT_NOTE);
        note.setCommentaire_note(DEFAULT_COMMENTAIRE_NOTE);
    }

    @Test
    @Transactional
    public void createNote() throws Exception {
        int databaseSizeBeforeCreate = noteRepository.findAll().size();

        // Create the Note
        restNoteMockMvc.perform(post("/api/notes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(note)))
                .andExpect(status().isCreated());

        // Validate the Note in the database
        List<Note> notes = noteRepository.findAll();
        assertThat(notes).hasSize(databaseSizeBeforeCreate + 1);
        Note testNote = notes.get(notes.size() - 1);
        assertThat(testNote.getKeyAPI()).isEqualTo(DEFAULT_KEY_API);
        assertThat(testNote.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testNote.getCommentaire_note()).isEqualTo(DEFAULT_COMMENTAIRE_NOTE);
    }

    @Test
    @Transactional
    public void getAllNotes() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get all the notes
        restNoteMockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(note.getId().intValue())))
                .andExpect(jsonPath("$.[*].keyAPI").value(hasItem(DEFAULT_KEY_API.toString())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
                .andExpect(jsonPath("$.[*].commentaire_note").value(hasItem(DEFAULT_COMMENTAIRE_NOTE.toString())));
    }

    @Test
    @Transactional
    public void getNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

        // Get the note
        restNoteMockMvc.perform(get("/api/notes/{id}", note.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(note.getId().intValue()))
            .andExpect(jsonPath("$.keyAPI").value(DEFAULT_KEY_API.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.commentaire_note").value(DEFAULT_COMMENTAIRE_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNote() throws Exception {
        // Get the note
        restNoteMockMvc.perform(get("/api/notes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

		int databaseSizeBeforeUpdate = noteRepository.findAll().size();

        // Update the note
        note.setKeyAPI(UPDATED_KEY_API);
        note.setNote(UPDATED_NOTE);
        note.setCommentaire_note(UPDATED_COMMENTAIRE_NOTE);
        restNoteMockMvc.perform(put("/api/notes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(note)))
                .andExpect(status().isOk());

        // Validate the Note in the database
        List<Note> notes = noteRepository.findAll();
        assertThat(notes).hasSize(databaseSizeBeforeUpdate);
        Note testNote = notes.get(notes.size() - 1);
        assertThat(testNote.getKeyAPI()).isEqualTo(UPDATED_KEY_API);
        assertThat(testNote.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testNote.getCommentaire_note()).isEqualTo(UPDATED_COMMENTAIRE_NOTE);
    }

    @Test
    @Transactional
    public void deleteNote() throws Exception {
        // Initialize the database
        noteRepository.saveAndFlush(note);

		int databaseSizeBeforeDelete = noteRepository.findAll().size();

        // Get the note
        restNoteMockMvc.perform(delete("/api/notes/{id}", note.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Note> notes = noteRepository.findAll();
        assertThat(notes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
