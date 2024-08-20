package com.casumo.videorental.services;

import com.casumo.videorental.enumerations.FilmType;
import com.casumo.videorental.exceptions.InvalidFilmTypeException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * The PriceCalculator class provides functionality to calculate rental costs
 * for films based on their type and the number of rental days.
 */
@Service
public class PriceCalculator {

    // The price per day for NEW_RELEASE films. Set to 40.
    private static final BigDecimal PREMIUM_PRICE = new BigDecimal("40");
    // The base price for REGULAR and OLD films. Set to 30.
    private static final BigDecimal BASIC_PRICE = new BigDecimal("30");

    /**
     * Calculates the rental cost based on the type of film and the number of rental days
     */
    public BigDecimal calculateRentalCost(FilmType type, int rentalDays) {
        switch (type) {
            case NEW_RELEASE:
                return PREMIUM_PRICE.multiply(BigDecimal.valueOf(rentalDays));
            case REGULAR:
                return rentalDays <= 3 ? BASIC_PRICE : BASIC_PRICE.add(BASIC_PRICE.multiply(BigDecimal.valueOf(rentalDays - 3)));
            case OLD:
                return rentalDays <= 5 ? BASIC_PRICE : BASIC_PRICE.add(BASIC_PRICE.multiply(BigDecimal.valueOf(rentalDays - 5)));
            default:
                throw new InvalidFilmTypeException("Invalid film type: " + type);
        }
    }
}
