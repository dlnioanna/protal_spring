package com.protal.myApp.Service;

import com.protal.myApp.Entity.Guest;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.Ticket;
import com.protal.myApp.Entity.User;
import com.protal.myApp.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketRepository ticketRepository;

    public void saveTicket(Ticket t){
        ticketRepository.save(t);
    }

    @Override
    public List<Ticket> findByMovieShow(MovieShow movieShow) {
        return ticketRepository.findByMovieShow(movieShow);
    }

    @Override
    public List<Ticket> findByMovieShow_Id(Integer movieShowId) {
        return ticketRepository.findByMovieShow_Id(movieShowId);
    }

    @Override
    public List<Ticket> findByMovieShow_IdAndUsed(Integer movieShowId, Integer used) {
        return ticketRepository.findByMovieShow_IdAndUsed(movieShowId,used);
    }

    @Override
    public Ticket findById(Integer ticketId) {
        return ticketRepository.findById(ticketId).orElse(null);
    }

    @Override
    public List<Ticket> findByBuyer_IdAndMovieShow_IdAndUsed(Integer userId, Integer movieshowId,Integer used) {
        return ticketRepository.findByBuyer_IdAndMovieShow_IdAndUsed(userId,movieshowId,used);
    }

}
