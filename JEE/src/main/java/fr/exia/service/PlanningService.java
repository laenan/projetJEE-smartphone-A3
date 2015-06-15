package fr.exia.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.exia.domain.Activite;
import fr.exia.domain.Horaire;
import fr.exia.repository.ActiviteRepository;
import fr.exia.repository.HoraireRepository;

@Service
@Transactional
public class PlanningService {

    private final Logger log = LoggerFactory.getLogger(PlanningService.class);

    
    @Inject
    private HoraireRepository hrepos;
    @Inject
    private ActiviteRepository arepos;

    public List<Activite> getPlage(LocalDate debut, LocalDate fin){
    	List <Activite> lista= new ArrayList<Activite>();
    	for (Horaire h: hrepos.findAll()){
    		if (h.getDate_debut().isAfter(debut)&&h.getDate_debut().isBefore(fin)){
    			for(Activite a: h.getActivites()){
    				lista.add(a);
    			}
    		}
    	}
		return lista;
    }  
    
    public List<Activite> planingOpti(){ // en cours
    	List <Activite> lista= new ArrayList<Activite>();
		return lista;
    }
}
