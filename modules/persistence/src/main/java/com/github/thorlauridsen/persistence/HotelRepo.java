package com.github.thorlauridsen.persistence;

import com.github.thorlauridsen.model.Hotel;
import com.github.thorlauridsen.model.IHotelRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

/**
 * Hotel repository facade class.
 * <p>
 * This class is a facade for the {@link HotelJpaRepo}.
 * A service class can use this facade to easily interact with the
 * repository without needing to know about the database entity {@link HotelEntity}.
 * <p>
 * It is annotated with {@link Repository} to allow Spring to automatically
 * detect it as a bean and inject it where needed.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class HotelRepo implements IHotelRepo {

    private final HotelJpaRepo jpaRepo;

    /**
     * Save a hotel.
     * This will create a new {@link HotelEntity} and save it to the database.
     *
     * @param hotel Input object for creating a hotel.
     * @return {@link Hotel} model class.
     */
    @Override
    public Hotel save(Hotel hotel) {
        log.info("Saving hotel with name: {}", hotel.name());

        val entity = new HotelEntity(hotel.name(), hotel.location(), hotel.rating());
        val saved = jpaRepo.save(entity);

        log.info("Hotel saved with id: {}", saved.getId());

        return new Hotel(
                saved.getName(),
                saved.getLocation(),
                saved.getRating()
        );
    }

    /**
     * Find all hotels.
     * This method will convert the list of {@link HotelEntity} to a list of {@link Hotel} models.
     *
     * @return List of {@link Hotel} model classes.
     */
    @Override
    public List<Hotel> findAll() {
        log.info("Fetching all hotels");

        return jpaRepo.findAll().stream()
                .map(entity -> new Hotel(
                        entity.getName(),
                        entity.getLocation(),
                        entity.getRating()
                ))
                .toList();
    }
}
