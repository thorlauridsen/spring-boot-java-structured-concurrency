package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.model.Flight;
import com.github.thorlauridsen.service.FlightService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

/**
 * Flight controller class.
 * This class implements the {@link IFlightController} interface and
 * overrides the methods defined in the interface with implementations.
 * The controller is responsible for handling flight requests and delegating to the service layer.
 */
@Controller
@RequiredArgsConstructor
public class FlightController implements IFlightController {

    private final FlightService flightService;

    /**
     * Retrieve all flights.
     *
     * @return {@link ResponseEntity} with a list of {@link Flight}.
     */
    @Override
    public ResponseEntity<List<Flight>> getAll() throws InterruptedException {
        val list = flightService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Save a flight.
     *
     * @param flight Flight to save.
     * @return {@link ResponseEntity} with the saved {@link Flight}.
     */
    @Override
    public ResponseEntity<Flight> post(Flight flight) {
        val savedFlight = flightService.save(flight);
        return ResponseEntity.ok(savedFlight);
    }
}
