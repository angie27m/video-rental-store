package com.casumo.videorental.services;

import com.casumo.videorental.exceptions.FilmNotFoundException;
import com.casumo.videorental.exceptions.RentalNotFoundException;
import com.casumo.videorental.models.Film;
import com.casumo.videorental.models.Rental;
import com.casumo.videorental.repositories.FilmRepository;
import com.casumo.videorental.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private PriceCalculator priceCalculator;

    /***
     * Rent one or several films, this method returns a total cost according
     * the film type and number of rental days
     */
    public BigDecimal rentFilms(List<Rental> rentalRequests) {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (Rental rental : rentalRequests) {
            // Ensure film exists
            Film film = filmRepository.findById(rental.getFilm().getId())
                    .orElseThrow(() -> new FilmNotFoundException("Film not found with ID " + rental.getFilm().getId()));

            // Calculate the cost for the current rental
            BigDecimal rentalCost = priceCalculator.calculateRentalCost(film.getType(), rental.getRentalDays());
            // Add the rental cost to the total cost
            totalCost = totalCost.add(rentalCost);
            // Save the rental
            rental.setFilm(film);
            rentalRepository.save(rental);
        }

        return totalCost;
    }

    /**
     * This method allows to return one or several films and calculate the late fees
     */
    public BigDecimal returnFilms(List<Rental> returnedRentals) {
        BigDecimal lateFees = BigDecimal.ZERO;

        for (Rental rental : returnedRentals) {
            // Ensure rental exists
            Rental existingRental = rentalRepository.findById(rental.getId())
                    .orElseThrow(() -> new RentalNotFoundException("Rental with ID " + rental.getId() + " not found"));

            // Update extra days
            existingRental.setExtraDays(rental.getExtraDays());

            // Calculate the late fee based on the updated extraDays
            if (existingRental.getExtraDays() > 0) {
                BigDecimal rentalCostPerDay = priceCalculator.calculateRentalCost(existingRental.getFilm().getType(), 1);
                BigDecimal lateFee = rentalCostPerDay.multiply(BigDecimal.valueOf(existingRental.getExtraDays()));
                lateFees = lateFees.add(lateFee);
            }

            // Save the updated rental with extraDays back to the repository
            rentalRepository.save(existingRental);
        }

        return lateFees;
    }

    /**
     * Get all rentals in the store
     */
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

}