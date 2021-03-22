package com.protal.myApp.Repository;

import com.protal.myApp.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest,Integer> {
    List<Guest> findByEmail(String email);

    Guest findByTicketId(Integer id);
}
