package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.model.RentalCar;
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

import static com.github.thorlauridsen.controller.BaseEndpoint.RENTAL_CAR_BASE_ENDPOINT;

/**
 * Rental car controller interface.
 * This interface defines the endpoints for the rental car controller.
 * It also defines the operations which will be used in the OpenAPI documentation.
 * The purpose of this interface is to separate the controller definition from the implementation.
 */
@Tag(name = "Rental Car Controller", description = "API for managing rental cars")
@RequestMapping(RENTAL_CAR_BASE_ENDPOINT)
public interface IRentalCarController {

    /**
     * Retrieve all rental cars.
     *
     * @return {@link ResponseEntity} with a list of {@link RentalCar}.
     */
    @GetMapping
    @Operation(
            summary = "Retrieve all rental cars",
            description = "Retrieve all rental cars"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved rental cars"
    )
    ResponseEntity<List<RentalCar>> getAll() throws InterruptedException;

    /**
     * Save a rental car.
     *
     * @param rentalCar RentalCar to save.
     * @return {@link ResponseEntity} with the saved {@link RentalCar}.
     */
    @PostMapping
    @Operation(
            summary = "Save a rental car",
            description = "Save a rental car"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Rental car successfully created"
    )
    ResponseEntity<RentalCar> post(@Valid @RequestBody RentalCar rentalCar);
}
