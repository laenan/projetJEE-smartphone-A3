package fr.exia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.exia.domain.util.CustomLocalDateSerializer;
import fr.exia.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Activite.
 */
@Entity
@Table(name = "ACTIVITE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom_activite")
    private String nom_activite;

    @Column(name = "description_activite")
    private String description_activite;

    @Column(name = "duree_activite")
    private Integer duree_activite;

    @Column(name = "photo_activite")
    private String photo_activite;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date_creation")
    private LocalDate date_creation;

    @Column(name = "nb_acteurs")
    private Integer nb_acteurs;

    @Column(name = "evt_historique")
    private String evt_historique;

    @Column(name = "position")
    private Integer position;

    @ManyToOne
    private Parc parc;

    @OneToMany(mappedBy = "activite")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Note> notes = new HashSet<>();

    @ManyToOne
    private Type_activite type_activite;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ACTIVITE_HORAIRE",
               joinColumns = @JoinColumn(name="activites_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="horaires_id", referencedColumnName="ID"))
    private Set<Horaire> horaires = new HashSet<>();

    @OneToMany(mappedBy = "activite")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Menu_Restaurant> menu_Restaurants = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_activite() {
        return nom_activite;
    }

    public void setNom_activite(String nom_activite) {
        this.nom_activite = nom_activite;
    }

    public String getDescription_activite() {
        return description_activite;
    }

    public void setDescription_activite(String description_activite) {
        this.description_activite = description_activite;
    }

    public Integer getDuree_activite() {
        return duree_activite;
    }

    public void setDuree_activite(Integer duree_activite) {
        this.duree_activite = duree_activite;
    }

    public String getPhoto_activite() {
        return photo_activite;
    }

    public void setPhoto_activite(String photo_activite) {
        this.photo_activite = photo_activite;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Parc getParc() {
        return parc;
    }

    public void setParc(Parc parc) {
        this.parc = parc;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Type_activite getType_activite() {
        return type_activite;
    }

    public void setType_activite(Type_activite type_activite) {
        this.type_activite = type_activite;
    }

    public Set<Horaire> getHoraires() {
        return horaires;
    }

    public void setHoraires(Set<Horaire> horaires) {
        this.horaires = horaires;
    }

    public Set<Menu_Restaurant> getMenu_Restaurants() {
        return menu_Restaurants;
    }

    public void setMenu_Restaurants(Set<Menu_Restaurant> menu_Restaurants) {
        this.menu_Restaurants = menu_Restaurants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Activite activite = (Activite) o;

        if ( ! Objects.equals(id, activite.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Activite{" +
                "id=" + id +
                ", nom_activite='" + nom_activite + "'" +
                ", description_activite='" + description_activite + "'" +
                ", duree_activite='" + duree_activite + "'" +
                ", photo_activite='" + photo_activite + "'" +
                ", date_creation='" + date_creation + "'" +
                ", nb_acteurs='" + nb_acteurs + "'" +
                ", evt_historique='" + evt_historique + "'" +
                ", position='" + position + "'" +
                '}';
    }
}
