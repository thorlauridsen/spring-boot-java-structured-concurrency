package com.github.thorlauridsen.model;

import java.util.List;

/**
 * Model record representing the details of travel options.
 *
 * @param flights    List of flights.
 * @param hotels     List of hotels.
 * @param rentalCars List of rental cars.
 */
public record TravelDetails(
        List<Flight> flights,
        List<Hotel> hotels,
        List<RentalCar> rentalCars
) {
}
