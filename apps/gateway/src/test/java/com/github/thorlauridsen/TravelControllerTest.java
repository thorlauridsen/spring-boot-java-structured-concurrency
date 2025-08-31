package com.github.thorlauridsen;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thorlauridsen.model.TravelDetails;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.nio.charset.StandardCharsets;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.thorlauridsen.controller.BaseEndpoint.TRAVEL_BASE_ENDPOINT;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class TravelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final WireMockServer WIREMOCK = new WireMockServer(9561);

    @BeforeAll
    static void setupWireMock() {
        WIREMOCK.start();
    }

    @AfterAll
    static void stopWireMock() {
        WIREMOCK.stop();
    }

    @BeforeEach
    void resetWireMock() throws JsonProcessingException {
        WIREMOCK.resetAll();
        setupWireMockStubs();
    }

    @Test
    void get_travel_details_async_success() throws Exception {
        val response = mockMvc.perform(
                        get(TRAVEL_BASE_ENDPOINT + "/async").accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        val json = response.getContentAsString(StandardCharsets.UTF_8);
        val details = objectMapper.readValue(json, TravelDetails.class);

        assertEquals(TravelTestData.travelDetails, details);
    }

    @Test
    void get_travel_details_sync_success() throws Exception {
        val response = mockMvc.perform(
                        get(TRAVEL_BASE_ENDPOINT + "/sync").accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        val json = response.getContentAsString(StandardCharsets.UTF_8);
        val details = objectMapper.readValue(json, TravelDetails.class);

        assertEquals(TravelTestData.travelDetails, details);
    }

    /**
     * Setup WireMock stubs for the external services.
     *
     * @throws JsonProcessingException if there is an error processing JSON.
     */
    void setupWireMockStubs() throws JsonProcessingException {
        val hotelsJson = objectMapper.writeValueAsString(TravelTestData.hotels);
        val flightsJson = objectMapper.writeValueAsString(TravelTestData.flights);
        val carsJson = objectMapper.writeValueAsString(TravelTestData.rentalCars);

        WIREMOCK.stubFor(WireMock.get(urlEqualTo("/hotels"))
                .willReturn(okJson(hotelsJson)));

        WIREMOCK.stubFor(WireMock.get(urlEqualTo("/flights"))
                .willReturn(okJson(flightsJson)));

        WIREMOCK.stubFor(WireMock.get(urlEqualTo("/rentalcars"))
                .willReturn(okJson(carsJson)));
    }
}
