package com.cinema.service;


import com.cinema.bodies.BasicShowInfo;
import com.cinema.model.Show;
import com.cinema.bodies.ShowInfoIdTime;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.RoomRepository;
import com.cinema.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for show endpoints.
 */
@Service
public class ShowService {

    ShowRepository showRepository;
    MovieRepository movieRepository;
    RoomRepository roomRepository;

    @Autowired
    public ShowService(ShowRepository showRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    public List<Show> findAll() {
        return showRepository.findAll();
    }


    public List<BasicShowInfo> getAllByDate(LocalDate date) {
        return showRepository.getAllByDate(date);
    }


    public List<ShowInfoIdTime> getShowInfoByMovie(Long movie_id, LocalDateTime start, LocalDateTime end) {
        List<ShowInfoIdTime> shows = showRepository.getShowInfoByMovie(movie_id, start, end);
        System.out.println("Shows: " + shows);
        shows = shows.stream().sorted(Comparator.comparing(ShowInfoIdTime::getTime)).collect(Collectors.toList());
        return shows;
    }


    public List<Show> getShowsByRoomAndDate(Long room_id, LocalDateTime shift_start, LocalDateTime shift_end) {
        LocalDateTime shift_now = shift_start;

        System.out.println("Searching range: " + shift_start + "  -  " + shift_end);

        List<Show> shows = showRepository.getShowsByRoomAndDate(room_id, shift_start, shift_end);
        List<Show> shows_with_breaks = new LinkedList<Show>();

        System.out.println("Shows: " + shows);

//        shows.stream().sorted( (Show show1, Show show2) -> { return show1.getStart_time().compareTo(show2.getStart_time()); } );
        shows = shows.stream().sorted(Comparator.comparing(Show::getStart_time)).collect(Collectors.toList());

        for (Show show : shows) {
            if( shift_now.isBefore(show.getStart_time()) )
                shows_with_breaks.add( new Show(shift_now, show.getStart_time()) ); // adding break
            shows_with_breaks.add(show); // adding show

            shift_now = show.getEnd_time();
        }
        if( shift_now.isBefore(shift_end) )
            shows_with_breaks.add( new Show(shift_now, shift_end) );

        System.out.println("Shows with breaks: " + shows_with_breaks);
        return shows_with_breaks;
}


    @Transactional
    public Boolean addShowInSchedule(BasicShowInfo newShow) {

        Show show = new Show();
        show.setStart_time( newShow.getStart_time() );
        show.setEnd_time( newShow.getEnd_time() );
        show.setRoom( roomRepository.getRoomById(newShow.getRoom_id()) );
        show.setMovie( movieRepository.getMovieById(newShow.getMovie_id()) );

        System.out.println("Created show: " + newShow);

        Integer hour = newShow.getStart_time().getHour();
        LocalDate day = newShow.getStart_time().toLocalDate();
        if( hour <= 4 )
            day = day.minusDays(1);

        System.out.println("Checking day: " + day);

        LocalDateTime shift_start = LocalDateTime.of(day, LocalTime.of(12, 0));
        LocalDateTime shift_end   = LocalDateTime.of(day.plusDays(1), LocalTime.of(4, 0));

        System.out.println("Searching range: " + shift_start + "  -  " + shift_end);

        List<Show> shows = showRepository.getShowsByRoomAndDate( newShow.getRoom_id(), shift_start, shift_end );
        if( !checkIfShowCollide(show, shows) ) {
            showRepository.save(show);
            return true;
        }
        return false;
    }


    public Boolean deleteShow(Long show_id) {
        showRepository.deleteById(show_id);
        return true;
    }


    public Boolean checkIfShowCollide(Show newShow, List<Show> shows) {

        Boolean collide = false;
        // s == newShow start
        // e == newShow end
        // e_i == end of ith show in shows
        // s_i == start of ith show in shows
        for( Show show : shows ) {
            if (newShow.getStart_time().equals(show.getStart_time()) && newShow.getEnd_time().equals(show.getEnd_time()) )
                collide = true; // s_i == s && e_i == e
            else if( show.getStart_time().isBefore(newShow.getStart_time()) && newShow.getStart_time().isBefore(show.getEnd_time()) )
                collide = true; // s_i < s < e_i
            else if ( show.getStart_time().isBefore( newShow.getEnd_time()) && newShow.getEnd_time().isBefore(show.getEnd_time()) )
                collide = true; // s_i < e < e_i
            else if ( newShow.getStart_time().isBefore(show.getEnd_time()) && show.getEnd_time().isBefore(newShow.getEnd_time()) )
                collide = true; // s < e_i < e
            else if ( newShow.getStart_time().isBefore(show.getStart_time()) && show.getStart_time().isBefore(newShow.getEnd_time()) )
                collide = true; // s < s_i < e
        }

        return collide;
    }




}
