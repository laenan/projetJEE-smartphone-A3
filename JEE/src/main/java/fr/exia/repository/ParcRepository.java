package fr.exia.repository;

import fr.exia.domain.Parc;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Parc entity.
 */
public interface ParcRepository extends JpaRepository<Parc,Long> {

}
