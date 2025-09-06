package com.github.thorlauridsen.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Rental car entity class.
 * Represents a rental car with company, model, and location.
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "rental_car")
public class RentalCarEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String carModel;

    @Column(nullable = false)
    private String location;

    /**
     * Constructor for rental car.
     *
     * @param company Company renting out the car.
     * @param carModel Model of the car.
     * @param location Location of the car.
     */
    public RentalCarEntity(String company, String carModel, String location) {
        this.company = company;
        this.carModel = carModel;
        this.location = location;
    }
}
