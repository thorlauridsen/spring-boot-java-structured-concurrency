package com.github.thorlauridsen.service;

import com.github.thorlauridsen.model.Hotel;
import com.github.thorlauridsen.model.IHotelRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for:
 * - Saving hotels.
 * - Fetching hotels.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class HotelService {

    private final IHotelRepo hotelRepo;

    /**
     * Save a hotel.
     *
     * @param hotel {@link Hotel} to save.
     * @return {@link Hotel} retrieved from database.
     */
    public Hotel save(Hotel hotel) {
        log.info("Saving hotel {} to database...", hotel);
        return hotelRepo.save(hotel);
    }

    /**
     * Get all hotels.
     * This function has an artificial delay of 2000ms to simulate a slow response.
     *
     * @return List of {@link Hotel}.
     */
    public List<Hotel> findAll() throws InterruptedException {
        log.info("Retrieving all hotels from database...");

        val hotels = hotelRepo.findAll();
        Thread.sleep(2000);

        log.info("Found {} hotels", hotels.size());
        return hotels;
    }
}
