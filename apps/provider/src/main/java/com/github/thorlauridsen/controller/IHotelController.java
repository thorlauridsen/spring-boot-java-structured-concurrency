package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.model.Hotel;
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

import static com.github.thorlauridsen.controller.BaseEndpoint.HOTEL_BASE_ENDPOINT;

/**
 * Hotel controller interface.
 * This interface defines the endpoints for the hotel controller.
 * It also defines the operations which will be used in the OpenAPI documentation.
 * The purpose of this interface is to separate the controller definition from the implementation.
 */
@Tag(name = "Hotel Controller", description = "API for managing hotels")
@RequestMapping(HOTEL_BASE_ENDPOINT)
public interface IHotelController {

    /**
     * Retrieve all hotels.
     *
     * @return {@link ResponseEntity} with a list of {@link Hotel}.
     */
    @GetMapping
    @Operation(
            summary = "Retrieve all hotels",
            description = "Retrieve all hotels"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved hotels"
    )
    ResponseEntity<List<Hotel>> getAll() throws InterruptedException;

    /**
     * Save a hotel.
     *
     * @param hotel Hotel to save.
     * @return {@link ResponseEntity} with the saved {@link Hotel}.
     */
    @PostMapping
    @Operation(
            summary = "Save a hotel",
            description = "Save a hotel"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Hotel successfully created"
    )
    ResponseEntity<Hotel> post(@Valid @RequestBody Hotel hotel);
}
