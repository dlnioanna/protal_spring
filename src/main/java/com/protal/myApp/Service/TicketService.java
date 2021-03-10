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

    List<Ticket> findByMovieShow_Id(Integer movieShowId);

    List<Ticket> findByMovieShow_IdAndUsed(Integer movieShowId, Integer used);

    Ticket findById(Integer ticketId);

    List<Ticket> findByBuyer_IdAndMovieShow_IdAndUsed(Integer userId, Integer movieshowId,Integer used);}
