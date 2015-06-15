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
 * A Note.
 */
@Entity
@Table(name = "NOTE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "key_api")
    private String keyAPI;

    @Column(name = "note")
    private Integer note;

    @Column(name = "commentaire_note")
    private String commentaire_note;

    @ManyToOne
    private Activite activite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyAPI() {
        return keyAPI;
    }

    public void setKeyAPI(String keyAPI) {
        this.keyAPI = keyAPI;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getCommentaire_note() {
        return commentaire_note;
    }

    public void setCommentaire_note(String commentaire_note) {
        this.commentaire_note = commentaire_note;
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

        Note note = (Note) o;

        if ( ! Objects.equals(id, note.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", keyAPI='" + keyAPI + "'" +
                ", note='" + note + "'" +
                ", commentaire_note='" + commentaire_note + "'" +
                '}';
    }
}
