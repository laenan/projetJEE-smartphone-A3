package fr.exia.repository;

import fr.exia.domain.Note;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Note entity.
 */
public interface NoteRepository extends JpaRepository<Note,Long> {

}
