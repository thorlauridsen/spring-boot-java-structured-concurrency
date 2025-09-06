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
 * Flight entity class.
 * Represents a flight with number, airline, origin, and destination.
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "flight")
public class FlightEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String flightNumber;

    @Column(nullable = false)
    private String airline;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    /**
     * Constructor for flight.
     *
     * @param flightNumber Flight number.
     * @param airline Airline of the flight.
     * @param origin Origin airport.
     * @param destination Destination airport.
     */
    public FlightEntity(String flightNumber, String airline, String origin, String destination) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
    }
}
