package com.github.thorlauridsen.model;

/**
 * Model record representing a flight.
 *
 * @param flightNumber Flight number.
 * @param airline      Airline of the flight.
 * @param origin       Origin of the flight.
 * @param destination  Destination of the flight.
 */
public record Flight(
        String flightNumber,
        String airline,
        String origin,
        String destination
) {
}
