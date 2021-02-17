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

//    @Override
//    public void updateTicketGuest(Guest guest, Ticket ticket) {
//        ticketRepository.updateTicketGuest(guest,ticket);
//    }

}
