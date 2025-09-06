package com.github.thorlauridsen.model;

import java.util.List;

/**
 * Hotel repository interface.
 * This is an interface containing methods for interacting with the hotel table.
 * A repository class will implement this interface to provide the actual implementation.
 * This interface makes it easier to swap out the implementation of the repository if needed.
 */
public interface IHotelRepo {

    /**
     * Save a hotel to the database.
     * @param hotel Hotel to save.
     * @return Hotel retrieved from database.
     */
    Hotel save(Hotel hotel);

    /**
     * Get all hotels from the database.
     * @return List of Hotel
     */
    List<Hotel> findAll();
}
