package com.casumo.videorental.services;

import com.casumo.videorental.enumerations.FilmType;
import com.casumo.videorental.models.Film;
import com.casumo.videorental.models.Rental;
import com.casumo.videorental.repositories.FilmRepository;
import com.casumo.videorental.repositories.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class RentalServiceTest {

    @Autowired
    private RentalService rentalService;

    @MockBean
    private FilmRepository filmRepository;

    @MockBean
    private RentalRepository rentalRepository;

    private Film matrix11;
    private Film spiderMan;
    private Film spiderMan2;
    private Film outOfAfrica;

    @BeforeEach
    void setUp() {
        // Mock data setup
        matrix11 = new Film();
        matrix11.setId(1L);
        matrix11.setTitle("Matrix 11");
        matrix11.setType(FilmType.NEW_RELEASE);

        spiderMan = new Film();
        spiderMan.setId(2L);
        spiderMan.setTitle("Spider Man");
        spiderMan.setType(FilmType.REGULAR);

        spiderMan2 = new Film();
        spiderMan2.setId(3L);
        spiderMan2.setTitle("Spider Man 2");
        spiderMan2.setType(FilmType.REGULAR);

        outOfAfrica = new Film();
        outOfAfrica.setId(4L);
        outOfAfrica.setTitle("Out of Africa");
        outOfAfrica.setType(FilmType.OLD);

        when(filmRepository.findById(1L)).thenReturn(Optional.of(matrix11));
        when(filmRepository.findById(2L)).thenReturn(Optional.of(spiderMan));
        when(filmRepository.findById(3L)).thenReturn(Optional.of(spiderMan2));
        when(filmRepository.findById(4L)).thenReturn(Optional.of(outOfAfrica));
    }

    @Test
    void rentFilms_exampleScenario() {
        Rental rental1 = new Rental();
        rental1.setFilm(matrix11);
        rental1.setRentalDays(1);

        Rental rental2 = new Rental();
        rental2.setFilm(spiderMan);
        rental2.setRentalDays(5);

        Rental rental3 = new Rental();
        rental3.setFilm(spiderMan2);
        rental3.setRentalDays(2);

        Rental rental4 = new Rental();
        rental4.setFilm(outOfAfrica);
        rental4.setRentalDays(7);

        BigDecimal totalCost = rentalService.rentFilms(Arrays.asList(rental1, rental2, rental3, rental4));

        assertEquals(new BigDecimal("250"), totalCost);
        verify(rentalRepository, times(4)).save(any(Rental.class));
    }

    @Test
    void returnFilms_lateReturnScenario() {
        Rental rental1 = new Rental();
        rental1.setId(1L);
        rental1.setFilm(matrix11);
        rental1.setRentalDays(1);
        rental1.setExtraDays(2);
        Rental rental2 = new Rental();
        rental2.setId(2L);
        rental2.setFilm(spiderMan);
        rental2.setRentalDays(5);
        rental2.setExtraDays(1);

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental1));
        when(rentalRepository.findById(2L)).thenReturn(Optional.of(rental2));

        BigDecimal lateFees = rentalService.returnFilms(Arrays.asList(rental1, rental2));

        assertEquals(new BigDecimal("110"), lateFees);
        verify(rentalRepository, times(2)).save(any(Rental.class));
    }
}