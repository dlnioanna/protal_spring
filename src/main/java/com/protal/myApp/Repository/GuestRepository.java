package com.protal.myApp.Repository;

import com.protal.myApp.Entity.Guest;
import com.protal.myApp.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest,Integer> {
    List<Guest> findByEmail(String email);

}
