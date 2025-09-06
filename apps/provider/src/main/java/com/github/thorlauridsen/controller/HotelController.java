package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.model.Hotel;
import com.github.thorlauridsen.service.HotelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

/**
 * Hotel controller class.
 * This class implements the {@link IHotelController} interface and
 * overrides the methods defined in the interface with implementations.
 * The controller is responsible for handling hotel requests and delegating to the service layer.
 */
@Controller
@RequiredArgsConstructor
public class HotelController implements IHotelController {

    private final HotelService hotelService;

    /**
     * Retrieve all hotels.
     *
     * @return {@link ResponseEntity} with a list of {@link Hotel}.
     */
    @Override
    public ResponseEntity<List<Hotel>> getAll() throws InterruptedException {
        val list = hotelService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Save a hotel.
     *
     * @param hotel Hotel to save.
     * @return {@link ResponseEntity} with the saved {@link Hotel}.
     */
    @Override
    public ResponseEntity<Hotel> post(Hotel hotel) {
        val savedHotel = hotelService.save(hotel);
        return ResponseEntity.ok(savedHotel);
    }
}
