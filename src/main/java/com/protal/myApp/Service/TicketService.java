package com.protal.myApp.Service;

import com.protal.myApp.Entity.Guest;
import com.protal.myApp.Entity.Purchase;
import com.protal.myApp.Entity.Ticket;
import org.springframework.data.repository.query.Param;

public interface TicketService {
    void saveTicket(Ticket t);

//    void updateTicketGuest(Guest guest, Ticket ticket);
}
