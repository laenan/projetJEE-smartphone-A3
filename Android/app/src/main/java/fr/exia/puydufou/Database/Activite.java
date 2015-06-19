package fr.exia.puydufou.Database;

import java.util.Date;

/**
 * Created by Asvina on 18/06/2015.
 */
public class Activite {

    private int id;
    private String nom;
    private String description;
    private Integer duree;

    private Date date_creation;
    private Integer nb_acteurs;
    private String evt_historique;

    public Activite(String nom, String description, Integer duree, String photo, Date date_creation, Integer nb_acteurs, String evt_historique){
        this.nom=nom;
        this.description=description;
        this.duree=duree;

        this.date_creation=date_creation;
        this.nb_acteurs=nb_acteurs;
        this.evt_historique=evt_historique;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_activite() {
        return nom;
    }

    public void setNom_activite(String nom_activite) {
        this.nom = nom_activite;
    }

    public String getDescription_activite() {
        return description;
    }

    public void setDescription_activite(String description_activite) {
        this.description = description_activite;
    }

    public Integer getDuree_activite() {
        return duree;
    }

    public void setDuree_activite(Integer duree_activite) {
        this.duree = duree_activite;
    }


    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public Integer getNb_acteurs() {
        return nb_acteurs;
    }

    public void setNb_acteurs(Integer nb_acteurs) {
        this.nb_acteurs = nb_acteurs;
    }

    public String getEvt_historique() {
        return evt_historique;
    }

    public void setEvt_historique(String evt_historique) {
        this.evt_historique = evt_historique;
    }


}
