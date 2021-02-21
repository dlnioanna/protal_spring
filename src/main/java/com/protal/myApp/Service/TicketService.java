package com.protal.myApp.Service;

import com.protal.myApp.Entity.Guest;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.Purchase;
import com.protal.myApp.Entity.Ticket;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketService {
    void saveTicket(Ticket t);

    List<Ticket> findByMovieShow(MovieShow movieShow);

    Ticket findById(Integer ticketId);
}
