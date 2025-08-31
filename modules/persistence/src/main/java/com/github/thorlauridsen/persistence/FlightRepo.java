package com.github.thorlauridsen.persistence;

import com.github.thorlauridsen.model.Flight;
import com.github.thorlauridsen.model.IFlightRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

/**
 * Flight repository facade class.
 * <p>
 * This class is a facade for the {@link FlightJpaRepo}.
 * A service class can use this facade to easily interact with the
 * repository without needing to know about the database entity {@link FlightEntity}.
 * <p>
 * It is annotated with {@link Repository} to allow Spring to automatically
 * detect it as a bean and inject it where needed.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class FlightRepo implements IFlightRepo {

    private final FlightJpaRepo jpaRepo;

    /**
     * Save a flight.
     * This will create a new {@link FlightEntity} and save it to the database.
     *
     * @param flight Input object for creating a flight.
     * @return {@link Flight} model class.
     */
    @Override
    public Flight save(Flight flight) {
        log.info("Saving flight with number: {}", flight.flightNumber());

        val entity = new FlightEntity(
                flight.flightNumber(),
                flight.airline(),
                flight.origin(),
                flight.destination()
        );
        val saved = jpaRepo.save(entity);

        log.info("Flight saved with id: {}", saved.getId());

        return new Flight(
                saved.getFlightNumber(),
                saved.getAirline(),
                saved.getOrigin(),
                saved.getDestination()
        );
    }

    /**
     * Find all flights.
     * This method will convert the list of {@link FlightEntity} to a list of {@link Flight} models.
     *
     * @return List of {@link Flight} model classes.
     */
    @Override
    public List<Flight> findAll() {
        log.info("Fetching all flights");

        return jpaRepo.findAll().stream()
                .map(entity -> new Flight(
                        entity.getFlightNumber(),
                        entity.getAirline(),
                        entity.getOrigin(),
                        entity.getDestination()
                ))
                .toList();
    }
}
