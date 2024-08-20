package com.casumo.videorental.models;

import jakarta.persistence.*;

/**
 * This class represents a rental entity in the video rental system
 */
@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_sequence")
    @SequenceGenerator(name = "rental_sequence", sequenceName = "rental_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    private int rentalDays;
    private int extraDays;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public int getExtraDays() {
        return extraDays;
    }

    public void setExtraDays(int extraDays) {
        this.extraDays = extraDays;
    }
}