package com.casumo.videorental.models;

import com.casumo.videorental.enumerations.FilmType;
import jakarta.persistence.*;

/**
 * This class represents a film entity in the video rental system
 */
@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "film_sequence")
    @SequenceGenerator(name = "film_sequence", sequenceName = "film_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FilmType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FilmType getType() {
        return type;
    }

    public void setType(FilmType type) {
        this.type = type;
    }
}