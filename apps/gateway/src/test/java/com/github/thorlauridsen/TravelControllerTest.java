package com.github.thorlauridsen;

import com.github.thorlauridsen.model.TravelDetails;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;
import tools.jackson.databind.json.JsonMapper;

import static com.github.thorlauridsen.controller.BaseEndpoint.TRAVEL_BASE_ENDPOINT;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@AutoConfigureRestTestClient
@SpringBootTest
class TravelControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private JsonMapper jsonMapper;

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
    void resetWireMock() {
        WIREMOCK.resetAll();
        setupWireMockStubs();
    }

    @Test
    void get_travel_details_async_success() {
        val details = restTestClient.get()
                .uri(TRAVEL_BASE_ENDPOINT + "/async")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TravelDetails.class)
                .returnResult()
                .getResponseBody();

        assertEquals(TravelTestData.travelDetails, details);
    }

    @Test
    void get_travel_details_sync_success() {
        val details = restTestClient.get()
                .uri(TRAVEL_BASE_ENDPOINT + "/sync")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TravelDetails.class)
                .returnResult()
                .getResponseBody();

        assertEquals(TravelTestData.travelDetails, details);
    }

    /**
     * Setup WireMock stubs for the external services.
     */
    void setupWireMockStubs() {
        val hotelsJson = jsonMapper.writeValueAsString(TravelTestData.hotels);
        val flightsJson = jsonMapper.writeValueAsString(TravelTestData.flights);
        val carsJson = jsonMapper.writeValueAsString(TravelTestData.rentalCars);

        WIREMOCK.stubFor(WireMock.get(urlEqualTo("/hotels"))
                .willReturn(okJson(hotelsJson)));

        WIREMOCK.stubFor(WireMock.get(urlEqualTo("/flights"))
                .willReturn(okJson(flightsJson)));

        WIREMOCK.stubFor(WireMock.get(urlEqualTo("/rentalcars"))
                .willReturn(okJson(carsJson)));
    }
}
