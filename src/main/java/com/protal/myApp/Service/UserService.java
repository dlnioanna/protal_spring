package com.protal.myApp.Service;

import com.protal.myApp.Entity.*;
import org.apache.catalina.LifecycleState;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User findById(Integer id);

    List<User> findAll();

    User findByEmail(String email);

    User findByTicketsBought(Ticket ticket);

    User findByUserPurchases(Purchase purchase);

    User findByEmailAndPassword(String email, String password);

    List<User> findByUsernameOrEmail(String username, String email);

    boolean checkIfUserExists(String username, String email);

    void saveUser(User user);

    User findByUsername(String username);

    User findByUsernameUncompressed(String username);

    void sendEmailToUser(List<Guest> guest, Ticket userTicket,List<Ticket> guestsTicket,  User user, MovieShow movieShow);

    List<User> findBuyersByMovieShow_Id(Integer movieShowId);

    boolean registerNewUser(MultipartFile file, String name, String lastName, Long telephone,
                            String email, String username, String password);

    boolean editUserValues(String name,String lastName, Long telephone, String password,
                           String role, String username, String email, Integer id);

    boolean updateAccount(MultipartFile file, String name, String lastName, Long telephone,
                            String password, Integer id);
}
