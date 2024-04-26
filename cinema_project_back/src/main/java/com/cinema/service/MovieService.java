package com.cinema.service;

import com.cinema.model.Genre;
import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for movie endpoints.
 */
@Service
@ComponentScan("com.example.repository")
public class MovieService {

    MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMoviesByDate(LocalDateTime start, LocalDateTime end) {
        List<Movie> movies = movieRepository.getMoviesByDate(start, end);
        // sorting genres
        movies.forEach(
                (movie) -> movie.setGenres(
                            movie.getGenres().stream().sorted( Comparator.comparing(Genre::getGenre) ).collect(Collectors.toList())
                )
        );
        return movies;
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }



}
