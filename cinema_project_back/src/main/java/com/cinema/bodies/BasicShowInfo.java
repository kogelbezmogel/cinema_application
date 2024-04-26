package com.cinema.bodies;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents information about Show to enable body deserialization from post request.
 */
public class BasicShowInfo {
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start_time;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end_time;
    private Long room_id;
    private Long movie_id;


    public BasicShowInfo() {
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = LocalDateTime.parse(start_time, DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setEnd_time(String end_time) {
        this.end_time = LocalDateTime.parse(end_time, DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public Long getId() {
        return id;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    @Override
    public String toString() {
        return "BasicShowInfo{" +
                "id=" + id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", room_id=" + room_id +
                ", movie_id=" + movie_id +
                '}';
    }
}
