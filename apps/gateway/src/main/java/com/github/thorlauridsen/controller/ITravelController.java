package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.model.TravelDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.github.thorlauridsen.controller.BaseEndpoint.TRAVEL_BASE_ENDPOINT;

/**
 * Travel controller interface.
 * This interface defines the endpoints for the travel controller.
 * It also defines the operations which will be used in the OpenAPI documentation.
 * The purpose of this interface is to separate the controller definition from the implementation.
 */
@Tag(name = "Travel Controller", description = "API for retrieving travel information")
@RequestMapping(TRAVEL_BASE_ENDPOINT)
public interface ITravelController {

    /**
     * Retrieve travel details asynchronously.
     *
     * @return {@link ResponseEntity} with {@link TravelDetails}.
     */
    @GetMapping("/async")
    @Operation(
            summary = "Retrieve travel details",
            description = "Retrieve travel details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved travel details"
    )
    ResponseEntity<TravelDetails> getAsync() throws InterruptedException;

    /**
     * Retrieve travel details synchronously.
     *
     * @return {@link ResponseEntity} with {@link TravelDetails}.
     */
    @GetMapping("/sync")
    @Operation(
            summary = "Retrieve travel details",
            description = "Retrieve travel details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved travel details"
    )
    ResponseEntity<TravelDetails> getSync();
}
