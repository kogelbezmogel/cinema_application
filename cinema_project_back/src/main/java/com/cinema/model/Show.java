package com.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Show table representation from database
 */
@Entity
@Table( name = "shows" )
public class Show {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime start_time;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime end_time;
    @ManyToOne()
    @JoinColumn( name = "room_id" )
    private Room room;
    @ManyToOne()
    @JoinColumn( name = "movie_id" )
    private Movie movie;

    public Show() {
    }

    public Show(LocalDateTime start_time, LocalDateTime end_time) {
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", room=" + room +
                ", movie=" + movie +
                '}';
    }
}
