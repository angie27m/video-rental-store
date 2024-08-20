package com.casumo.videorental.services;

import com.casumo.videorental.enumerations.FilmType;
import com.casumo.videorental.exceptions.InvalidFilmTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorTest {

    private PriceCalculator priceCalculator;

    @BeforeEach
    void setUp() {
        priceCalculator = new PriceCalculator();
    }

    @ParameterizedTest
    @CsvSource({
            // Challenge examples
            "NEW_RELEASE, 1, 40",   // Matrix 11
            "REGULAR, 5, 90",       // Spider Man
            "REGULAR, 2, 30",       // Spider Man 2
            "OLD, 7, 90",           // Out of Africa
            // Additional test cases
            "NEW_RELEASE, 3, 120",
            "REGULAR, 1, 30",
            "REGULAR, 3, 30",
            "REGULAR, 4, 60",
            "OLD, 1, 30",
            "OLD, 5, 30",
            "OLD, 6, 60"
    })
    void calculateRentalCost_validInputs(FilmType type, int days, BigDecimal expected) {
        assertEquals(expected, priceCalculator.calculateRentalCost(type, days));
    }

    @Test
    void calculateRentalCost_invalidFilmType() {
        assertThrows(InvalidFilmTypeException.class, () ->
                priceCalculator.calculateRentalCost(null, 1));
    }

    @Test
    void calculateRentalCost_lateReturnNewRelease() {
        // Matrix 11 (New release) 2 extra days 80 SEK
        assertEquals(new BigDecimal("80"), priceCalculator.calculateRentalCost(FilmType.NEW_RELEASE, 2));
    }

    @Test
    void calculateRentalCost_lateReturnRegular() {
        // Spider Man (Regular rental) 1 extra day 30 SEK
        assertEquals(new BigDecimal("30"), priceCalculator.calculateRentalCost(FilmType.REGULAR, 1));
    }

    @Test
    void calculateTotalRentalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;
        totalCost = totalCost.add(priceCalculator.calculateRentalCost(FilmType.NEW_RELEASE, 1));  // Matrix 11
        totalCost = totalCost.add(priceCalculator.calculateRentalCost(FilmType.REGULAR, 5));      // Spider Man
        totalCost = totalCost.add(priceCalculator.calculateRentalCost(FilmType.REGULAR, 2));      // Spider Man 2
        totalCost = totalCost.add(priceCalculator.calculateRentalCost(FilmType.OLD, 7));          // Out of Africa

        assertEquals(new BigDecimal("250"), totalCost);
    }

    @Test
    void calculateTotalLateCharge() {
        BigDecimal totalLateCharge = BigDecimal.ZERO;
        totalLateCharge = totalLateCharge.add(priceCalculator.calculateRentalCost(FilmType.NEW_RELEASE, 2));  // Matrix 11
        totalLateCharge = totalLateCharge.add(priceCalculator.calculateRentalCost(FilmType.REGULAR, 1));      // Spider Man

        assertEquals(new BigDecimal("110"), totalLateCharge);
    }
}