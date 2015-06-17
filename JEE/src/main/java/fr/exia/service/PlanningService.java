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
		log.debug("Recherche des activitÃ©s sur la plage: " + debut.toString()
				+ " - " + parse[1] + " - " + fin.toString() + "-" + parse[3]);
		List<Activite> lista = new ArrayList<Activite>();
		for (Horaire h : hrepos.findAll()) {
			if ((h.getDate_debut().isAfter(debut) || h.getDate_debut().isEqual(
					debut))
					&& h.getDate_debut().isBefore(fin)
					&& (h.getHeure_debut() >= Integer.parseInt(parse[1]))
					&& h.getHeure_debut() < Integer.parseInt(parse[3])) {
				for (Activite a : h.getActivites()) {
					lista.add(a);
				}
			}
		}
		return lista;
	}

	public List<Activite> planingOpti(int hrequest) { // validé reste tsortie
		List<Activite> lista = new ArrayList<Activite>();
		int HeureFinPrec = hrequest, HeureDebutSuiv = 0;
		int deplacement = 10, dureeRepas = 0100;
		Activite prochaineActivite = null;
		boolean repasMidi=false,repasSoir=false;

		while (HeureFinPrec < 2200) {

			int tempsAttente = 10000;
			for (Activite a : arepos.findAll()) {
				for (Horaire h : a.getHoraires()) {
					HeureDebutSuiv = h.getHeure_debut();
					int tempsAttenteTempx = HeureDebutSuiv - HeureFinPrec;
					if (tempsAttenteTempx < tempsAttente
							&& tempsAttenteTempx > 0) {
						tempsAttente = tempsAttenteTempx;
						prochaineActivite = a;
					}
				}
			}
			lista.add(prochaineActivite);
			HeureFinPrec =formatage( HeureDebutSuiv
					+ prochaineActivite.getDuree_activite() + deplacement);
			if (HeureFinPrec >= 1200 && HeureFinPrec < 1400 && repasMidi==false) {
				repasMidi=true;
				HeureFinPrec+=dureeRepas;
			}
			if (HeureFinPrec >= 1930 && HeureFinPrec < 2200 && repasSoir==false) {
				repasSoir=true;
				HeureFinPrec+=dureeRepas;
			}
		}
		return lista;
	}

	private int formatage(int i) {
		int x = (i/100)*100;
		int y = i - x;
		int z = y/60;
		int r = z*60;
		i = x + z + r;
		return i;
	}
}
