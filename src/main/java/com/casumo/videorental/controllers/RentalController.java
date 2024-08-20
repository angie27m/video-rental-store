package com.casumo.videorental.controllers;

import com.casumo.videorental.models.Rental;
import com.casumo.videorental.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // Rent films
    @PostMapping("/rent")
    public ResponseEntity<BigDecimal> rentFilms(@RequestBody List<Rental> rentalRequests) {
        BigDecimal totalCost = rentalService.rentFilms(rentalRequests);
        return ResponseEntity.ok(totalCost);
    }

    // Return films
    @PostMapping("/return")
    public ResponseEntity<BigDecimal> returnFilms(@RequestBody List<Rental> returnedRentals) {
        BigDecimal lateFees = rentalService.returnFilms(returnedRentals);
        return ResponseEntity.ok(lateFees);
    }

    // Get all rentals
    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        // Assuming you have a method to retrieve all rentals if needed
        List<Rental> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }
}