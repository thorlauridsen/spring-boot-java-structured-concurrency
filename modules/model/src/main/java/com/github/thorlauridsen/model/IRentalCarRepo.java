package com.github.thorlauridsen.model;

import java.util.List;

/**
 * Rental car repository interface.
 * This is an interface containing methods for interacting with the rental car table.
 * A repository class will implement this interface to provide the actual implementation.
 * This interface makes it easier to swap out the implementation of the repository if needed.
 */
public interface IRentalCarRepo {

    /**
     * Save a rental car to the database.
     * @param rentalCar RentalCar to save.
     * @return RentalCar retrieved from database.
     */
    RentalCar save(RentalCar rentalCar);

    /**
     * Get all rental cars from the database.
     * @return List of RentalCar
     */
    List<RentalCar> findAll();
}
