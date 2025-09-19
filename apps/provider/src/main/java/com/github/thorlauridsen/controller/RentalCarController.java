package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.model.RentalCar;
import com.github.thorlauridsen.service.RentalCarService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rental car controller class.
 * This class implements the {@link IRentalCarController} interface and
 * overrides the methods defined in the interface with implementations.
 * The controller is responsible for handling rental car requests and delegating to the service layer.
 */
@RestController
@RequiredArgsConstructor
public class RentalCarController implements IRentalCarController {

    private final RentalCarService rentalCarService;

    /**
     * Retrieve all rental cars.
     *
     * @return {@link ResponseEntity} with a list of {@link RentalCar}.
     */
    @Override
    public ResponseEntity<List<RentalCar>> getAll() throws InterruptedException {
        val list = rentalCarService.findAll();
        return ResponseEntity.ok(list);
    }

    /**
     * Save a rental car.
     *
     * @param rentalCar RentalCar to save.
     * @return {@link ResponseEntity} with the saved {@link RentalCar}.
     */
    @Override
    public ResponseEntity<RentalCar> post(RentalCar rentalCar) {
        val savedRentalCar = rentalCarService.save(rentalCar);
        return ResponseEntity.ok(savedRentalCar);
    }
}
