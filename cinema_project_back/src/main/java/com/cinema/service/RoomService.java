package com.cinema.service;

import com.cinema.model.Room;
import com.cinema.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for room endpoints.
 */
@Service
public class RoomService {

    RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoomByShowId(Long show_id) {
        return roomRepository.getRoomByShowId(show_id);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }


}
