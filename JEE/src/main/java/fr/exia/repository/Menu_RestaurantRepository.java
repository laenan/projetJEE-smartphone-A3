package fr.exia.repository;

import fr.exia.domain.Menu_Restaurant;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Menu_Restaurant entity.
 */
public interface Menu_RestaurantRepository extends JpaRepository<Menu_Restaurant,Long> {

}
