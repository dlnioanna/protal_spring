package com.protal.myApp.Service;

import com.protal.myApp.Entity.Guest;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.Ticket;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GuestService {
    void sendEmailToGuests(Guest guest, Ticket ticket, String userName, MovieShow movieShow);

    void saveGuest(Guest g);

    Guest findById(Integer id);

    Guest findByTicketId(Integer id);

    List<Guest> getAttendantsOfMovieShow(Integer movieShowId);
}
