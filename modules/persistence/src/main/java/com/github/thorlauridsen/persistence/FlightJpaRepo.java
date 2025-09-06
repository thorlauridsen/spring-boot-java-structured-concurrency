package com.github.thorlauridsen.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Flight repository interface.
 * This is a JPA repository for the {@link FlightEntity}.
 * It extends the {@link JpaRepository} interface which allows us to easily define CRUD methods.
 */
public interface FlightJpaRepo extends JpaRepository<FlightEntity, UUID> {
}
