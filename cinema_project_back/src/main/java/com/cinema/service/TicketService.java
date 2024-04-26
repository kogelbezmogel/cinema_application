package com.cinema.service;


import com.cinema.bodies.BuyTicketInfo;
import com.cinema.bodies.BuyerInfo;
import com.cinema.model.*;
import com.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for ticket endpoints.
 */
@Service
public class TicketService {


    private final TicketRepository ticketRepository;
    private final RoomRepository roomRepository;
    private final ShowRepository showRepository;
    private final SitRepository sitRepository;

    private final RegisteredUserRepository registeredUserRepository;


    @Autowired
    public TicketService(TicketRepository ticketRepository, RoomRepository roomRepository, ShowRepository showRepository, SitRepository sitRepository, RegisteredUserRepository registeredUserRepository) {
        this.ticketRepository = ticketRepository;
        this.roomRepository = roomRepository;
        this.showRepository = showRepository;
        this.sitRepository = sitRepository;
        this.registeredUserRepository = registeredUserRepository;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Transactional
    public Boolean setTicketsToShow( BuyTicketInfo buyTicketInfo ) {
        System.out.println("Setting tickets");
        try {
            List<Integer> sits_num = buyTicketInfo.getSits_nums();
            Long user_id = null;
            Long show_id = buyTicketInfo.getShow_id();
            BuyerInfo buyer = buyTicketInfo.getBuyerInfo();

            Room room = roomRepository.getRoomByShowId(show_id);
            Show show = showRepository.getReferenceById(show_id);
            List<Sit> sits = sitRepository.getSitsByOrderAndRoom_id( sits_num, room.getId() );

            Optional<RegisteredUser> o_user;
            if( buyTicketInfo.getUserLogin() != null ) {
                o_user = registeredUserRepository.findRegisteredUserByLogin(buyTicketInfo.getUserLogin());
                if( o_user.isPresent() )
                    user_id = o_user.get().getId();
            }

            Long finalUser_id = user_id;
            List<Ticket> tickets = sits.stream().map(
                    sit -> new Ticket(buyer.getFname(), buyer.getLname(), buyer.getMail(), finalUser_id, sit, show)
            ).toList();

            ticketRepository.saveAll(tickets);
            System.out.println("sits: " + sits_num);
            System.out.println("Sits: " + sits);
            System.out.println("Tickets: " + tickets);
            System.out.println("Tickets saved");
            return true;
        } catch (Exception e) {
            System.out.println("setTicketsToShowFail");
            System.out.println(e.getMessage());
        }
        return false;
    }

}
