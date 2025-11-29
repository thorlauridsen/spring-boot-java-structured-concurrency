package com.github.thorlauridsen;

import com.github.thorlauridsen.model.Flight;
import java.util.List;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.json.JsonMapper;

import static com.github.thorlauridsen.controller.BaseEndpoint.FLIGHT_BASE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureRestTestClient
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    @Order(1)
    void getAllFlights_success() {

        val typeReference = new ParameterizedTypeReference<@NotNull List<Flight>>() {
        };

        val flights = restTestClient.get()
                .uri(FLIGHT_BASE_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(typeReference)
                .returnResult()
                .getResponseBody();

        assertNotNull(flights);
        assertEquals(3, flights.size());
    }

    @Test
    @Order(2)
    void saveFlight_success() {

        val flight = new Flight(
                "XY999",
                "New Airline",
                "New Origin",
                "New Destination"
        );
        val json = jsonMapper.writeValueAsString(flight);

        val saved = restTestClient.post()
                .uri(FLIGHT_BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Flight.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(saved);
        assertEquals(flight.flightNumber(), saved.flightNumber());
        assertEquals(flight.airline(), saved.airline());
        assertEquals(flight.origin(), saved.origin());
        assertEquals(flight.destination(), saved.destination());
    }
}
