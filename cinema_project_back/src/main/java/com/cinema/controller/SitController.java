package com.cinema.controller;

import com.cinema.bodies.BasicShowInfo;
import com.cinema.bodies.BasicSitsInfo;
import com.cinema.model.Sit;
import com.cinema.service.SitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Sit Controller. It contains endpoints regarding information exchange regarding sits in theater rooms.
 */
@RestController
@RequestMapping("/sit")
public class SitController {

    /**
     * Represents sit service layer.
     */
    private SitService sitService;

    @Autowired
    public SitController(SitService sitService) {
        this.sitService = sitService;
    }

    /**
     * @return List of all sits in database.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Sit>> getAllSits() {
        List<Sit> sits = sitService.findAll();
        return ResponseEntity.ok(sits);
    }

    /**
     * Endpoint looking for sits in given room.
     * @param room_id It is room id for which sits are looked for.
     * @return List of sits.
     */
    @GetMapping("/room/{room_id}")
    public ResponseEntity<List<Sit>> getAllSitsInRoom(@PathVariable("room_id") Long room_id) {
        List<Sit> sits = sitService.getSitByRoom_Id(room_id);
        return ResponseEntity.ok(sits);
    }

    /**
     * Endpoint creating information about sits for given show. Information includes taken and available sits marked.
     * @param show_id It is show id for which information is created.
     * @return List of BasicSitsInfo.
     */
    @GetMapping("/show/{show_id}")
    public ResponseEntity<List<BasicSitsInfo>> getBasicSitsInfoByShowId(@PathVariable("show_id") Long show_id) {
        List<BasicSitsInfo> sitsInfos = sitService.getBasicSitsInfoByShowId(show_id);
        return ResponseEntity.ok(sitsInfos);
    }



}
