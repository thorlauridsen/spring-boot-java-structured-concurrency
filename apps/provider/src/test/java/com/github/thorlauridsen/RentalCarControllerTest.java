package com.github.thorlauridsen;

import com.github.thorlauridsen.model.RentalCar;
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

import static com.github.thorlauridsen.controller.BaseEndpoint.RENTAL_CAR_BASE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureRestTestClient
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RentalCarControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    @Order(1)
    void getAllRentalCars_success() {

        val typeReference = new ParameterizedTypeReference<@NotNull List<RentalCar>>() {
        };

        val rentalCars = restTestClient.get()
                .uri(RENTAL_CAR_BASE_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(typeReference)
                .returnResult()
                .getResponseBody();

        assertNotNull(rentalCars);
        assertEquals(3, rentalCars.size());
    }

    @Test
    @Order(2)
    void saveRentalCar_success() {

        val rentalCar = new RentalCar("New Company", "New Model", "New Location");
        val json = jsonMapper.writeValueAsString(rentalCar);

        val saved = restTestClient.post()
                .uri(RENTAL_CAR_BASE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RentalCar.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(saved);
        assertEquals(rentalCar.carModel(), saved.carModel());
        assertEquals(rentalCar.company(), saved.company());
        assertEquals(rentalCar.location(), saved.location());
    }
}
