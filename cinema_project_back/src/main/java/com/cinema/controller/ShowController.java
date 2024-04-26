package com.cinema.controller;


import com.cinema.bodies.BasicShowInfo;
import com.cinema.model.Show;
import com.cinema.bodies.ShowInfoIdTime;
import com.cinema.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Chow Controller. It contains endpoints regarding information exchange about show displayed in theater.
 */
@RestController
@RequestMapping("/show")
public class ShowController {

    /**
     * Represents show service layer.
     */
    ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    /**
     * @return List of all shows in database.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Show>> getAllShows() {
        List<Show> shows = showService.findAll();
        return ResponseEntity.ok(shows);
    }

    /**
     * This endpoint look for shows of one movie in given time range.
     * @param movie_id It is movie id which chows are looked for.
     * @param start It is parameter in iso DateTime format representing beginning of scanned interval.
     * @param end It is parameter in iso DateTime format representing end of scanned interval.
     * @return List of Shows
     */
    @GetMapping("/info/movie/range")
    public ResponseEntity<List<ShowInfoIdTime>> getShowInfoByMovieAndDate(
            @RequestParam("movie_id") Long movie_id,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<ShowInfoIdTime> showInfos = showService.getShowInfoByMovie(movie_id, start, end);
        return ResponseEntity.ok(showInfos);
    }

    /**
     * This endpoint look for shows displayed in given room.
     * @param room_id It is room id
     * @param start It is parameter in iso DateTime format representing beginning of scanned interval.
     * @param end It is parameter in iso DateTime format representing end of scanned interval.
     * @return List of Shows
     */
    @GetMapping("/schedule/select")
    public ResponseEntity<List<Show>> getShowByRoomAndDate(
            @RequestParam("room_id") Long room_id,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Show> shows = showService.getShowsByRoomAndDate(room_id, start, end);
        return ResponseEntity.ok(shows);
    }

    /**
     * This endpoint tries to add show display to repertoire.
     * @param newShow It is show info taken as a body of post request.
     * @return true if show added and false otherwise.
     */
    @PostMapping("/schedule/add")
    public ResponseEntity<Boolean> postShowInSchedule(@RequestBody BasicShowInfo newShow) {
        Boolean success = showService.addShowInSchedule(newShow);
        return ResponseEntity.ok(success);
    }


    /**
     *
     * @param show_id
     * @return
     */
    @GetMapping("/delete/{show_id}")
    public ResponseEntity<Boolean> deleteShowFromRepertoire(@PathVariable("show_id") Long show_id) {
        Boolean success = showService.deleteShow(show_id);
        return ResponseEntity.ok(success);
    }


}
