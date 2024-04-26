package com.cinema.controller;

import com.cinema.model.Event;
import com.cinema.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addEvent(@RequestBody Event event) {
        Boolean success = eventService.addEvent(event);
        return ResponseEntity.ok(success);
    }
}
