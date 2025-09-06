package com.github.thorlauridsen.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Rental car repository interface.
 * This is a JPA repository for the {@link RentalCarEntity}.
 * It extends the {@link JpaRepository} interface which allows us to easily define CRUD methods.
 */
public interface RentalCarJpaRepo extends JpaRepository<RentalCarEntity, UUID> {
}
