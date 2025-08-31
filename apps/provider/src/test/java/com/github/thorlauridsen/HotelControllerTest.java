package com.github.thorlauridsen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thorlauridsen.model.Hotel;
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

import static com.github.thorlauridsen.controller.BaseEndpoint.HOTEL_BASE_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    void getAllHotels_success() throws Exception {

        val response = mockMvc.perform(get(HOTEL_BASE_ENDPOINT).accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        val json = response.getContentAsString();
        val hotels = objectMapper.readValue(json, new TypeReference<List<Hotel>>() {});

        assertEquals(OK.value(), response.getStatus());
        assertEquals(3, hotels.size());
    }

    @Test
    @Order(2)
    void saveHotel_success() throws Exception {

        val hotel = new Hotel("New Hotel", "New Location", 5.0);
        val json = objectMapper.writeValueAsString(hotel);

        val response = mockMvc.perform(
                post(HOTEL_BASE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        assertEquals(OK.value(), response.getStatus());

        val responseJson = response.getContentAsString();
        val saved = objectMapper.readValue(responseJson, Hotel.class);

        assertEquals(hotel.name(), saved.name());
        assertEquals(hotel.location(), saved.location());
        assertEquals(hotel.rating(), saved.rating());
    }
}
