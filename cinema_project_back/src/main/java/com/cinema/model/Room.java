package com.cinema.model;

import jakarta.persistence.*;

/**
 * Room table representation from database
 */
@Entity
@Table( name = "rooms" )
public class Room {

    @Id
    private Long id;
    @Column
    private Integer number_of_rows;
    @Column
    private Integer number_of_cols;


    public Room(Long id, Integer number_of_rows, Integer number_of_cols) {
        this.id = id;
        this.number_of_rows = number_of_rows;
        this.number_of_cols = number_of_cols;
    }

    public Room() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber_of_rows(Integer number_of_rows) {
        this.number_of_rows = number_of_rows;
    }

    public void setNumber_of_cols(Integer number_of_cols) {
        this.number_of_cols = number_of_cols;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumber_of_rows() {
        return number_of_rows;
    }

    public Integer getNumber_of_cols() {
        return number_of_cols;
    }
}
