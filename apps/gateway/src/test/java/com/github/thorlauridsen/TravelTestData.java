package com.github.thorlauridsen;

import com.github.thorlauridsen.model.Flight;
import com.github.thorlauridsen.model.Hotel;
import com.github.thorlauridsen.model.RentalCar;
import com.github.thorlauridsen.model.TravelDetails;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TravelTestData {

    public static final List<Flight> flights = List.of(
            new Flight("AB100", "Airline A", "Origin A", "Destination A"),
            new Flight("AC101", "Airline B", "Origin B", "Destination B"),
            new Flight("AD102", "Airline C", "Origin C", "Destination C")
    );

    public static final List<Hotel> hotels = List.of(
            new Hotel("Hotel A", "Location A", 4.5),
            new Hotel("Hotel B", "Location B", 4.0),
            new Hotel("Hotel C", "Location C", 3.8)
    );

    public static final List<RentalCar> rentalCars = List.of(
            new RentalCar("Company A", "Car model A", "Location A"),
            new RentalCar("Company B", "Car model B", "Location B"),
            new RentalCar("Company C", "Car model C", "Location C")
    );

    public static final TravelDetails travelDetails = new TravelDetails(
            flights,
            hotels,
            rentalCars
    );
}
