package com.github.thorlauridsen.controller;

import com.github.thorlauridsen.model.TravelDetails;
import com.github.thorlauridsen.service.TravelService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

/**
 * Travel controller class.
 * This class implements the {@link ITravelController} interface and
 * overrides the methods defined in the interface with implementations.
 * The controller is responsible for handling requests for travel details
 * and delegating to the service layer.
 */
@Controller
@RequiredArgsConstructor
public class TravelController implements ITravelController {

    private final TravelService travelService;

    /**
     * Retrieves travel details asynchronously.
     *
     * @return {@link ResponseEntity} with {@link TravelDetails}.
     */
    @Override
    public ResponseEntity<TravelDetails> getAsync() throws InterruptedException {
        val details = travelService.getAsync();
        return ResponseEntity.ok(details);
    }

    /**
     * Retrieves travel details synchronously.
     *
     * @return {@link ResponseEntity} with {@link TravelDetails}.
     */
    @Override
    public ResponseEntity<TravelDetails> getSync() {
        val details = travelService.getSync();
        return ResponseEntity.ok(details);
    }
}
