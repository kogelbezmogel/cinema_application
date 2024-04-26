package com.cinema.repository;

import com.cinema.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


    @Query(
            "SELECT r FROM Room AS r"
            + " JOIN Show AS s ON s.room.id = r.id"
            + " WHERE s.id = :show_id"
    )
    Room getRoomByShowId(@Param("show_id") Long show_id);


    Room getRoomById(@Param("id") Long room_id);

}
