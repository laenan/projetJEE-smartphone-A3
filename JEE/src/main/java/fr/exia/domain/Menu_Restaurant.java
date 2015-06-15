package fr.exia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Menu_Restaurant.
 */
@Entity
@Table(name = "MENU_RESTAURANT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Menu_Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "entree_nom")
    private String entree_nom;

    @Column(name = "entree_photo")
    private String entree_photo;

    @Column(name = "plat_nom")
    private String plat_nom;

    @Column(name = "plat_photo")
    private String plat_photo;

    @Column(name = "dessert_nom")
    private String dessert_nom;

    @Column(name = "dessert_photo")
    private String dessert_photo;

    @Column(name = "tarif_menu", precision=10, scale=2)
    private BigDecimal tarif_menu;

    @ManyToOne
    private Activite activite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntree_nom() {
        return entree_nom;
    }

    public void setEntree_nom(String entree_nom) {
        this.entree_nom = entree_nom;
    }

    public String getEntree_photo() {
        return entree_photo;
    }

    public void setEntree_photo(String entree_photo) {
        this.entree_photo = entree_photo;
    }

    public String getPlat_nom() {
        return plat_nom;
    }

    public void setPlat_nom(String plat_nom) {
        this.plat_nom = plat_nom;
    }

    public String getPlat_photo() {
        return plat_photo;
    }

    public void setPlat_photo(String plat_photo) {
        this.plat_photo = plat_photo;
    }

    public String getDessert_nom() {
        return dessert_nom;
    }

    public void setDessert_nom(String dessert_nom) {
        this.dessert_nom = dessert_nom;
    }

    public String getDessert_photo() {
        return dessert_photo;
    }

    public void setDessert_photo(String dessert_photo) {
        this.dessert_photo = dessert_photo;
    }

    public BigDecimal getTarif_menu() {
        return tarif_menu;
    }

    public void setTarif_menu(BigDecimal tarif_menu) {
        this.tarif_menu = tarif_menu;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Menu_Restaurant menu_Restaurant = (Menu_Restaurant) o;

        if ( ! Objects.equals(id, menu_Restaurant.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Menu_Restaurant{" +
                "id=" + id +
                ", entree_nom='" + entree_nom + "'" +
                ", entree_photo='" + entree_photo + "'" +
                ", plat_nom='" + plat_nom + "'" +
                ", plat_photo='" + plat_photo + "'" +
                ", dessert_nom='" + dessert_nom + "'" +
                ", dessert_photo='" + dessert_photo + "'" +
                ", tarif_menu='" + tarif_menu + "'" +
                '}';
    }
}
