package com.github.thorlauridsen.service;

import com.github.thorlauridsen.model.Flight;
import com.github.thorlauridsen.model.IFlightRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for:
 * - Saving flights.
 * - Fetching flights.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FlightService {

    private final IFlightRepo flightRepo;

    /**
     * Save a flight.
     *
     * @param flight {@link Flight} to save.
     * @return {@link Flight} retrieved from database.
     */
    public Flight save(Flight flight) {
        log.info("Saving flight {} to database...", flight);
        return flightRepo.save(flight);
    }

    /**
     * Get all flights.
     * This function has an artificial delay of 2000ms to simulate a slow response.
     *
     * @return List of {@link Flight}.
     */
    public List<Flight> findAll() throws InterruptedException {
        log.info("Retrieving all flights from database...");

        val flights = flightRepo.findAll();
        Thread.sleep(2000);

        log.info("Found {} flights", flights.size());
        return flights;
    }
}
