package com.github.thorlauridsen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thorlauridsen.model.Flight;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.thorlauridsen.controller.BaseEndpoint.FLIGHT_BASE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void getAllFlights_success() throws Exception {

        val response = mockMvc.perform(get(FLIGHT_BASE_ENDPOINT).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        val json = response.getContentAsString();
        val flights = objectMapper.readValue(json, new TypeReference<List<Flight>>() {
        });

        assertEquals(OK.value(), response.getStatus());
        assertEquals(3, flights.size());
    }

    @Test
    @Order(2)
    void saveFlight_success() throws Exception {

        val flight = new Flight(
                "XY999",
                "New Airline",
                "New Origin",
                "New Destination"
        );
        val json = objectMapper.writeValueAsString(flight);

        val response = mockMvc.perform(
                post(FLIGHT_BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk()).andReturn().getResponse();

        assertEquals(OK.value(), response.getStatus());

        val responseJson = response.getContentAsString();
        val saved = objectMapper.readValue(responseJson, Flight.class);

        assertEquals(flight.flightNumber(), saved.flightNumber());
        assertEquals(flight.airline(), saved.airline());
        assertEquals(flight.origin(), saved.origin());
        assertEquals(flight.destination(), saved.destination());
    }
}
