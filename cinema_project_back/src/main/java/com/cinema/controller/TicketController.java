package com.cinema.controller;

import com.cinema.bodies.BuyTicketInfo;
import com.cinema.model.Ticket;
import com.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * It contains endpoint regarding information about tickets.
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {


    /**
     * Represent ticket service layer.
     */
    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * @return List of all tickets in database
     */
    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.findAll();
        return ResponseEntity.ok(tickets);
    }


    /**
     * Endpoint for buying ticket
     * @param request It is body of post request essential for BuyTicketInfo class.
     * @return true if ticket bought and false otherwise.
     */
    @PostMapping("/buy")
    public ResponseEntity<Boolean> setTicketsToShow(@RequestBody BuyTicketInfo request) {
        Boolean success = ticketService.setTicketsToShow( request );
        return ResponseEntity.ok(success);
    }
}
