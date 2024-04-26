package com.cinema.repository;


import com.cinema.bodies.BasicSitsInfo;
import com.cinema.model.Sit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SitRepository extends JpaRepository<Sit, Long> {
    List<Sit> getSitByRoom_Id(Long room_id);


    @Query(
            "SELECT si.order_num FROM Sit AS si"
            + " JOIN Room AS r ON r.id = si.room.id"
            + " JOIN Show AS s ON s.room.id = r.id"
            + " WHERE s.id = :show_id"
    )
    List<Integer> getSitsByShowId(@Param("show_id") Long show_id);

    @Query(
            "SELECT new com.cinema.bodies.BasicSitsInfo(si.order_num, 'available') FROM Sit as si"
            + " JOIN Room AS r ON r.id = si.room.id"
            + " JOIN Show AS s ON s.room.id = r.id"
            + " WHERE s.id = :show_id"
    )
    List<BasicSitsInfo> getBasicSitsInfoByShowId(@Param("show_id") Long show_id );


    @Query(
            "SELECT si.order_num FROM Sit AS si"
            + " JOIN Ticket AS t ON t.sit.id = si.id"
            + " JOIN Show AS s ON s.id = t.show.id"
            + " WHERE t.show.id = :show_id"
    )
    List<Integer> getSitsTakenByShowId( @Param("show_id") Long show_id );


    @Query(
            "SELECT (r.number_of_cols * r.number_of_rows) FROM Room  as r"
            + " JOIN Show as s ON s.room.id = r.id"
            + " WHERE s.id = :show_id"
    )
    Integer getAmountOfPlaceByShowId( @Param("show_id") Long show_id );


    @Query(
            "SELECT si FROM Sit as si"
            + " JOIN Room AS r ON r.id=si.room.id"
            + " WHERE r.id = :room_id AND si.order_num IN :order_nums"
    )
    List<Sit> getSitsByOrderAndRoom_id( @Param("order_nums") List<Integer> order_nums, @Param("room_id") Long room_id );
}
