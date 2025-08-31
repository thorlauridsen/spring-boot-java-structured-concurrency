package com.github.thorlauridsen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thorlauridsen.model.RentalCar;
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

import static com.github.thorlauridsen.controller.BaseEndpoint.RENTAL_CAR_BASE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RentalCarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void getAllRentalCars_success() throws Exception {

        val response = mockMvc.perform(get(RENTAL_CAR_BASE_ENDPOINT).accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        val json = response.getContentAsString();
        val rentalCars = objectMapper.readValue(
                json,
                new TypeReference<List<RentalCar>>() {
                }
        );

        assertEquals(OK.value(), response.getStatus());
        assertEquals(3, rentalCars.size());
    }

    @Test
    @Order(2)
    void saveRentalCar_success() throws Exception {

        val rentalCar = new RentalCar("New Company", "New Model", "New Location");
        val json = objectMapper.writeValueAsString(rentalCar);

        val response = mockMvc.perform(
                post(RENTAL_CAR_BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        assertEquals(OK.value(), response.getStatus());

        val responseJson = response.getContentAsString();
        val saved = objectMapper.readValue(responseJson, RentalCar.class);

        assertEquals(rentalCar.carModel(), saved.carModel());
        assertEquals(rentalCar.company(), saved.company());
        assertEquals(rentalCar.location(), saved.location());
    }
}
