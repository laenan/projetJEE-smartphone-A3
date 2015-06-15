package fr.exia.repository;

import fr.exia.domain.Horaire;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Horaire entity.
 */
public interface HoraireRepository extends JpaRepository<Horaire,Long> {

}
