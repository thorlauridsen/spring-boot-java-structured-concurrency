package com.github.thorlauridsen;

import com.github.thorlauridsen.model.Hotel;
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
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tools.jackson.databind.json.JsonMapper;

import static com.github.thorlauridsen.controller.BaseEndpoint.HOTEL_BASE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for testing the HotelController.
 * A local Docker instance is required to run the tests as Testcontainers is used.
 */
@ActiveProfiles("postgres")
@AutoConfigureRestTestClient
@SpringBootTest
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HotelControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18");

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    @Order(1)
    void getAllHotels_success() {

        val typeReference = new ParameterizedTypeReference<@NotNull List<Hotel>>() {
        };

        val hotels = restTestClient.get()
                .uri(HOTEL_BASE_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(typeReference)
                .returnResult()
                .getResponseBody();

        assertNotNull(hotels);
        assertEquals(3, hotels.size());
    }

    @Test
    @Order(2)
    void saveHotel_success() {

        val hotel = new Hotel("New Hotel", "New Location", 5.0);
        val json = jsonMapper.writeValueAsString(hotel);

        val saved = restTestClient.post()
                .uri(HOTEL_BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Hotel.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(saved);
        assertEquals(hotel.name(), saved.name());
        assertEquals(hotel.location(), saved.location());
        assertEquals(hotel.rating(), saved.rating());
    }
}
