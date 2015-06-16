package fr.exia.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.exia.domain.Note;
import fr.exia.repository.ActiviteRepository;
import fr.exia.repository.NoteRepository;

@Service
@Transactional
public class NotationService {

	private final Logger log = LoggerFactory.getLogger(NotationService.class);

	@Inject
	private NoteRepository nrepos;

	@Inject
	private ActiviteRepository arepos;

	public boolean verif(Note note) {
		for (Note n : nrepos.findAll()) {
			if (n.getKeyAPI() == note.getKeyAPI()
					&& n.getActivite() == note.getActivite()) {
				return false;
			}
		}
		return true;
	}
}
