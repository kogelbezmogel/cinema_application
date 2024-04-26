package com.cinema.model;


import jakarta.persistence.*;

/**
 * Genre table representation from database
 */
@Entity
@Table( name = "genres")
public class Genre {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(length = 60, unique = true)
    private String genre;

    public Genre(Long id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public Genre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
