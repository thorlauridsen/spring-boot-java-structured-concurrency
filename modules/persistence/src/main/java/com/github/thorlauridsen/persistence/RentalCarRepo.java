package com.github.thorlauridsen.persistence;

import com.github.thorlauridsen.model.IRentalCarRepo;
import com.github.thorlauridsen.model.RentalCar;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Repository;

/**
 * Rental car repository facade class.
 * <p>
 * This class is a facade for the {@link RentalCarJpaRepo}.
 * A service class can use this facade to easily interact with the
 * repository without needing to know about the database entity {@link RentalCarEntity}.
 * <p>
 * It is annotated with {@link Repository} to allow Spring to automatically
 * detect it as a bean and inject it where needed.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class RentalCarRepo implements IRentalCarRepo {

    private final RentalCarJpaRepo jpaRepo;

    /**
     * Save a rental car.
     * This will create a new {@link RentalCarEntity} and save it to the database.
     *
     * @param rentalCar Input object for creating a rental car.
     * @return {@link RentalCar} model class.
     */
    @Override
    public RentalCar save(RentalCar rentalCar) {
        log.info("Saving rental car with company: {}, model: {}", rentalCar.company(), rentalCar.carModel());

        val entity = new RentalCarEntity(
                rentalCar.company(),
                rentalCar.carModel(),
                rentalCar.location()
        );
        val saved = jpaRepo.save(entity);

        log.info("Rental car saved with id: {}", saved.getId());

        return new RentalCar(
                saved.getCompany(),
                saved.getCarModel(),
                saved.getLocation()
        );
    }

    /**
     * Find all rental cars.
     * This method will convert the list of {@link RentalCarEntity} to a list of {@link RentalCar} models.
     *
     * @return List of {@link RentalCar} model classes.
     */
    @Override
    public List<RentalCar> findAll() {
        log.info("Fetching all rental cars");

        return jpaRepo.findAll().stream()
                .map(entity -> new RentalCar(
                        entity.getCompany(),
                        entity.getCarModel(),
                        entity.getLocation()
                ))
                .toList();
    }
}
