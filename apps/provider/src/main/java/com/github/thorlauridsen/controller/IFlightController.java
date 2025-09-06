package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.model.Flight;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.github.thorlauridsen.controller.BaseEndpoint.FLIGHT_BASE_ENDPOINT;

/**
 * Flight controller interface.
 * This interface defines the endpoints for the flight controller.
 * It also defines the operations which will be used in the OpenAPI documentation.
 * The purpose of this interface is to separate the controller definition from the implementation.
 */
@Tag(name = "Flight Controller", description = "API for managing flights")
@RequestMapping(FLIGHT_BASE_ENDPOINT)
public interface IFlightController {

    /**
     * Retrieve all flights.
     *
     * @return {@link ResponseEntity} with a list of {@link Flight}.
     */
    @GetMapping
    @Operation(
            summary = "Retrieve all flights",
            description = "Retrieve all flights"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved flights"
    )
    ResponseEntity<List<Flight>> getAll() throws InterruptedException;

    /**
     * Save a flight.
     *
     * @param flight Flight to save.
     * @return {@link ResponseEntity} with the saved {@link Flight}.
     */
    @PostMapping
    @Operation(
            summary = "Save a flight",
            description = "Save a flight"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Flight successfully created"
    )
    ResponseEntity<Flight> post(@Valid @RequestBody Flight flight);
}
