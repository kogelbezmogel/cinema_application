package com.cinema.controller;

import com.cinema.model.Room;
import com.cinema.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * It contains endpoint regarding information exchange about rooms in movie theater.
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    /**
     * Represents service room service layer.
     */
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * @return List of all available rooms in theater.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.findAll();
        return ResponseEntity.ok(rooms);
    }

    /**
     * @param show_id It is show id.
     * @return Room in which show with sent id is being displayed.
     */
    @GetMapping("/show/{show_id}")
    public ResponseEntity<Room> getRoomByShowId(@PathVariable("show_id") Long show_id) {
        Room room = roomService.getRoomByShowId(show_id);
        return ResponseEntity.ok(room);
    }

}
