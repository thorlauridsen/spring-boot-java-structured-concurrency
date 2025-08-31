package com.github.thorlauridsen.service;

import com.github.thorlauridsen.model.Flight;
import com.github.thorlauridsen.model.Hotel;
import com.github.thorlauridsen.model.RentalCar;
import com.github.thorlauridsen.model.TravelDetails;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Service class for fetching travel details.
 * This is an example of how to utilize Structured Concurrency in Java
 * to optimize performance when executing multiple remote requests.
 * <p>
 * This service fetches data from three different endpoints:
 * - /hotels
 * - /flights
 * - /rentalcars
 * <p>
 * The data is fetched asynchronously using concurrency and synchronously without concurrency.
 * Both functions will measure the time it takes to fetch the data.
 * <p>
 * The provider subproject exposes endpoints for hotels, flights, and rental cars.
 * The endpoints have simulated delays to demonstrate the benefits of using coroutines.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TravelService {

    private final RestClient restClient;

    /**
     * Get travel details synchronously.
     *
     * @return {@link TravelDetails} containing the fetched data.
     */
    public TravelDetails getSync() {

        log.info("Fetching travel details synchronously");
        val start = OffsetDateTime.now();

        val flights = fetchList("/flights", Flight.class);
        val hotels = fetchList("/hotels", Hotel.class);
        val rentalCars = fetchList("/rentalcars", RentalCar.class);

        val details = new TravelDetails(flights, hotels, rentalCars);

        val duration = Duration.between(start, OffsetDateTime.now());
        log.info("Fetched travel details in {} ms", duration.toMillis());

        return details;
    }

    /**
     * Get travel details asynchronously.
     * <p>
     * This will initiate three asynchronous requests
     * to fetch data from the three different endpoints.
     * The requests will be executed concurrently using {@link StructuredTaskScope}.
     *
     * @return {@link TravelDetails} containing the fetched data.
     */
    public TravelDetails getAsync() throws InterruptedException {

        log.info("Fetching travel details asynchronously");
        val start = OffsetDateTime.now();

        try (val scope = new StructuredTaskScope.ShutdownOnFailure()) {

            val flightsTask = scope.fork(() -> fetchList("/flights", Flight.class));
            val hotelsTask = scope.fork(() -> fetchList("/hotels", Hotel.class));
            val carsTask = scope.fork(() -> fetchList("/rentalcars", RentalCar.class));

            scope.join();
            scope.throwIfFailed(
                    cause -> new IllegalStateException("Failed to fetch travel details", cause)
            );

            val details = new TravelDetails(
                    flightsTask.get(),
                    hotelsTask.get(),
                    carsTask.get()
            );

            val duration = Duration.between(start, OffsetDateTime.now());
            log.info("Fetched travel details in {} ms", duration.toMillis());

            return details;
        }
    }

    /**
     * Generic method to fetch a list of elements from a given path using {@link RestClient}.
     *
     * @param path        The path to fetch the data from.
     * @param elementType The type of the elements in the list.
     * @param <T>         The type of the elements in the list.
     * @return List of elements of type T.
     */
    private <T> List<T> fetchList(String path, Class<T> elementType) {
        log.info("Executing request HTTP GET {}", path);

        ParameterizedTypeReference<List<T>> listType = ParameterizedTypeReference.forType(
                ResolvableType.forClassWithGenerics(List.class, elementType).getType()
        );

        return restClient.get()
                .uri(path)
                .retrieve()
                .body(listType);
    }
}
