package com.cinema.controller;


import com.cinema.model.Genre;
import com.cinema.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class represents endpoints for information exchange with frontend regarding movie genres.
 */
@RestController
@RequestMapping("/genre")
public class GenreController {


    /**
     * Representing service layer for genres
     */
    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * Function doesn't take arguments
     * @return list of all genres in database
     */
    @GetMapping("/all")
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreService.findAll();
        return ResponseEntity.ok(genres);
    }

}
