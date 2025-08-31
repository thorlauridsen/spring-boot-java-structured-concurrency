package com.github.thorlauridsen.model;

/**
 * Model record representing a hotel.
 *
 * @param name     Name of the hotel.
 * @param location Location of the hotel.
 * @param rating   Rating of the hotel.
 */
public record Hotel(
        String name,
        String location,
        double rating
) {
}
