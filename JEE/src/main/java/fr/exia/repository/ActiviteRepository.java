package fr.exia.repository;

import fr.exia.domain.Activite;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Activite entity.
 */
public interface ActiviteRepository extends JpaRepository<Activite,Long> {

    @Query("select activite from Activite activite left join fetch activite.horaires where activite.id =:id")
    Activite findOneWithEagerRelationships(@Param("id") Long id);

}
