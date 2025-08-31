package com.github.thorlauridsen.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Hotel repository interface.
 * This is a JPA repository for the {@link HotelEntity}.
 * It extends the {@link JpaRepository} interface which allows us to easily define CRUD methods.
 */
public interface HotelJpaRepo extends JpaRepository<HotelEntity, UUID> {
}
