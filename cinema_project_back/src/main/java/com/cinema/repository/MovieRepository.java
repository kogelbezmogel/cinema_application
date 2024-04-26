package com.cinema.repository;

import com.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(
            "SELECT m FROM Movie AS m "
            + "JOIN Show AS s ON s.movie.id = m.id "
            + "WHERE (s.start_time >= :start AND s.end_time <= :end)"
    )
    List<Movie> getMoviesByDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end );


    Movie getMovieById( @Param("id") Long movie_id );

}
