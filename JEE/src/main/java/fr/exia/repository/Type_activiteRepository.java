package fr.exia.repository;

import fr.exia.domain.Type_activite;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Type_activite entity.
 */
public interface Type_activiteRepository extends JpaRepository<Type_activite,Long> {

}
