package com.cinema.model;

import jakarta.persistence.*;

/**
 * Sit table representation from database
 */
@Entity
@Table(name = "sits")
public class Sit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer order_num;

    @ManyToOne()
    @JoinColumn(name = "room_id")
    private Room room;

    public Sit(Long id, Integer order_num, Room room) {
        this.id = id;
        this.order_num = order_num;
        this.room = room;
    }

    public Sit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder_num() {
        return order_num;
    }

    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Sit{" +
                "id=" + id +
                ", order_num=" + order_num +
                ", room=" + room +
                '}';
    }
}
