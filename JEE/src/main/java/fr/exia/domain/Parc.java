package fr.exia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Parc.
 */
@Entity
@Table(name = "PARC")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Parc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="nom_parc")
    private String nom_parc;

    @Column(name = "description_parc")
    private String description_parc;

    @OneToMany(mappedBy = "parc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Activite> activites = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom_parc(){
    	return nom_parc;
    }
    
    public void setNom_parc(String nom_parc){
    	this.nom_parc=nom_parc;
    }

    public String getDescription_parc() {
        return description_parc;
    }

    public void setDescription_parc(String description_parc) {
        this.description_parc = description_parc;
    }

    public Set<Activite> getActivites() {
        return activites;
    }

    public void setActivites(Set<Activite> activites) {
        this.activites = activites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Parc parc = (Parc) o;

        if ( ! Objects.equals(id, parc.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Parc{" +
                "id=" + id +
                ", description_parc='" + description_parc + "'" +
                '}';
    }
}
