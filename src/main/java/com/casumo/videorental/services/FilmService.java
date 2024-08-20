package com.casumo.videorental.services;

import com.casumo.videorental.exceptions.FilmNotFoundException;
import com.casumo.videorental.models.Film;
import com.casumo.videorental.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    /**
     * Add new film to the inventory
     */
    public Film addFilm(Film film) {
        return filmRepository.save(film);
    }

    /**
     * Get a specific film by id
     */
    public Film getFilmById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new FilmNotFoundException("Film with ID " + id + " not found"));
    }

    /**
     * Get a list of films in the store
     */
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }
}
