package com.protal.myApp.Repository;

import com.protal.myApp.Entity.Purchase;
import com.protal.myApp.Entity.Ticket;
import com.protal.myApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByTicketsBought(Ticket ticket);

    User findByUserPurchases(Purchase purchase);

    User findByEmailAndPassword(String email, String password);

    User findByUsername(String username);

    List<User> findByUsernameOrEmail(String username, String email);

    @Query("select distinct t.buyer from Ticket t where t.movieShow.id=:movieShowId and t.used=1")
    List<User> findBuyersByMovieShow_Id(@Param("movieShowId") Integer movieShowId);

}
