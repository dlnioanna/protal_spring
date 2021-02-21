package com.protal.myApp.Repository;

import com.protal.myApp.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByBuyer(User buyer);

    List<Ticket> findByMovieShow(MovieShow movieShow);

//    List<Ticket> findByGuestOfTicket(Guest guest);

//    @Transactional
//    @Modifying
//    @Query(value = "update Ticket t set t.guestOfTicket =:guest where t =:ticket")
//    void updateTicketGuest(@Param("guest") Guest guest,@Param("ticket") Ticket ticket);
}
