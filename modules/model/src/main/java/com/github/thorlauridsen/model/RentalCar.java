package com.github.thorlauridsen.model;

/**
 * Model record representing a rental car.
 * @param company Company that rents out the car.
 * @param carModel Model of the car.
 * @param location Location of the car.
 */
public record RentalCar(
        String company,
        String carModel,
        String location
) {}
