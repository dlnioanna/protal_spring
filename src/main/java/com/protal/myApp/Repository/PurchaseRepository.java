package com.protal.myApp.Repository;

import com.protal.myApp.Entity.Guest;
import com.protal.myApp.Entity.Purchase;
import com.protal.myApp.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
    @Transactional
    @Modifying
    @Query(value = "update Purchase p set p.ticketsPurchased =:tickets where p =:purchase")
    void updatePurchaseTicket(@Param("purchase") Purchase purchase, @Param("tickets") List<Ticket> tickets);
}
