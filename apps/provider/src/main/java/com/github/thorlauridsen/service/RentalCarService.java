package com.github.thorlauridsen.service;

import com.github.thorlauridsen.model.IRentalCarRepo;
import com.github.thorlauridsen.model.RentalCar;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for:
 * - Saving rental cars.
 * - Fetching rental cars.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RentalCarService {

    private final IRentalCarRepo rentalCarRepo;

    /**
     * Save a rental car.
     *
     * @param rentalCar {@link RentalCar} to save.
     * @return {@link RentalCar} retrieved from database.
     */
    public RentalCar save(RentalCar rentalCar) {
        log.info("Saving rental car {} to database...", rentalCar);
        return rentalCarRepo.save(rentalCar);
    }

    /**
     * Get all rental cars.
     * This function has an artificial delay of 2000ms to simulate a slow response.
     *
     * @return List of {@link RentalCar}.
     */
    public List<RentalCar> findAll() throws InterruptedException {
        log.info("Retrieving all rental cars from database...");

        val rentalCars = rentalCarRepo.findAll();
        Thread.sleep(2000);

        log.info("Found {} rental cars", rentalCars.size());
        return rentalCars;
    }
}
