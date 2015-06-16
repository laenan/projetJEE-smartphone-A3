package fr.exia.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.exia.domain.Activite;
import fr.exia.domain.Horaire;
import fr.exia.repository.ActiviteRepository;
import fr.exia.repository.HoraireRepository;
import fr.exia.repository.NoteRepository;

@Service
@Transactional
public class PlanningService {

	private final Logger log = LoggerFactory.getLogger(PlanningService.class);

	@Inject
	private HoraireRepository hrepos;
	@Inject
	private ActiviteRepository arepos;

	@Inject
	private NoteRepository nrepos;

	public List<Activite> getPlage(String plage) {
		String[] parse = plage.split(";|\\:");
		LocalDate debut = new LocalDate(parse[0]);
		LocalDate fin = new LocalDate(parse[2]);
		log.debug("Recherche des activités sur la plage: "+debut.toString()+" - "+parse[1]+ " - " + fin.toString() +"-"+ parse[3]);
		List<Activite> lista = new ArrayList<Activite>();
		for (Horaire h : hrepos.findAll()) {
			if ((h.getDate_debut().isAfter(debut)||h.getDate_debut().isEqual(debut))
					&& h.getDate_debut().isBefore(fin)
					&& (h.getHeure_debut()>=Integer.parseInt(parse[1]))
					&& h.getHeure_debut()<Integer.parseInt(parse[3])) {
				for (Activite a : h.getActivites()) {
					lista.add(a);
				}
			}
		}
		return lista;
	}

	public List<Activite> planingOpti() { // en cours
		List<Activite> lista = new ArrayList<Activite>();
		return lista;
	}
}
