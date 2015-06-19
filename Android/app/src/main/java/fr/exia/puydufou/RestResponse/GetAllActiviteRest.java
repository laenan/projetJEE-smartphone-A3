package fr.exia.puydufou.RestResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Collection;
import java.util.Date;

/**
 * Created by araguin on 18/06/15.
 */
public class GetAllActiviteRest {
    public Collection<Activite> activities;

    @JsonTypeName("Activite")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Activite{
        private String id;
        private String nom_activite;
        private String description_activite;
        private int duree_activite;
        private String photo_activite;
        private Date date_creation;
        private int nb_acteurs;
        private String evt_historique;
        private int position;

        public String getId() {
            return this.id;
        }

        public String getNomActivite() {
            return this.nom_activite;
        }

        public String getDescriptionActivite() {
            return this.description_activite;
        }
    }
}
