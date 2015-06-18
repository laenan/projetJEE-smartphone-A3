package fr.exia.puydufou.ViewModel;


import java.util.Date;

/**
 * Created by Asvina on 17/06/2015.
 */
public class detailActiviteViewModel {

    private String id;
    private String nom_activite;
    private String description_activite;
    private Integer duree_activite;
    private String photo_activite;
    private Date date_creation;
    private Integer nb_acteurs;
    private String evt_historique;


    public String getId() {
        return this.id;
    }

    public String getNom_activite() {
        return this.nom_activite;
    }

    public String getDescription_activite() {
        return this.description_activite;
    }

    public Integer getDuree_activite() {
        return this.duree_activite;
    }

    public String getPhoto_activite() {
        return this.photo_activite;
    }

    public Date getDate_creation() {
        return this.date_creation;
    }

    public Integer getNb_acteurs() {
        return this.nb_acteurs;
    }

    public String getEvt_historique() {
        return this.evt_historique;
    }

}