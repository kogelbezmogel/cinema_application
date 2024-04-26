package com.cinema.bodies;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Represents show information to enable body deserialization from post request.
 */
public class ShowInfoIdTime {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    public ShowInfoIdTime() {
    }

    public ShowInfoIdTime(Long id, LocalDateTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ShowInfoIdTime{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }
}
