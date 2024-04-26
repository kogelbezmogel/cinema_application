package com.cinema.repository;

import com.cinema.bodies.BasicShowInfo;
import com.cinema.model.Show;
import com.cinema.bodies.ShowInfoIdTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query(
            "SELECT new com.cinema.bodies.BasicShowInfo(s.id, s.start_time, s.end_time, s.room.id, s.movie.id) FROM Show AS s WHERE DATE(s.start_time) = DATE(:day)"
    )
    List<BasicShowInfo> getAllByDate(@Param("day") LocalDate date);

    @Query(
            "SELECT (s.start_time) FROM Show AS s JOIN Movie AS m ON m.id = s.movie.id WHERE (m.id = :movie_id AND s.start_time >= :start AND s.end_time <= :end)"
    )
    List<String> getShowTimesByMovie(@Param("movie_id") Long movie_id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Query(
            "SELECT new com.cinema.bodies.ShowInfoIdTime(s.id, s.start_time) FROM Show AS s JOIN Movie AS m ON m.id = s.movie.id WHERE (m.id = :movie_id AND s.start_time >= :start AND s.end_time <= :end)"
    )
    List<ShowInfoIdTime> getShowInfoByMovie(@Param("movie_id") Long movie_id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(
            "SELECT sh FROM Show AS sh WHERE (sh.room.id = :room_id AND sh.start_time >= :start AND sh.end_time <= :end)"
    )
    List<Show> getShowsByRoomAndDate(@Param("room_id") Long room_id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
