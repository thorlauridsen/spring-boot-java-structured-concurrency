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
 * Hotel entity class.
 * Represents a hotel with name, location, and rating.
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "hotel")
public class HotelEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, name = "hotel_name")
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private double rating;

    /**
     * Constructor for hotel.
     *
     * @param name     Name of the hotel.
     * @param location Location of the hotel.
     * @param rating   Rating of the hotel.
     */
    public HotelEntity(String name, String location, double rating) {
        this.name = name;
        this.location = location;
        this.rating = rating;
    }
}
