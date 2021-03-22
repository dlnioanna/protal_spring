package com.protal.myApp.Service;

import com.protal.myApp.Entity.*;
import com.protal.myApp.Repository.UserRepository;
import com.protal.myApp.Utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.protal.myApp.Utils.CompressUtils.decompressBytes;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public User findById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        if(user.getImage()!=null){
            try {
                byte[] decompressedImage = decompressBytes(user.getImage());
                user.setImage(decompressedImage);
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        List<User> decompressedUserList = new ArrayList<>();
        for (User user : userList) {
            try {
                byte[] decompressedImage = decompressBytes(user.getImage());
                user.setImage(decompressedImage);
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
            decompressedUserList.add(user);
        }
        return decompressedUserList;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        try {
            byte[] decompressedImage = decompressBytes(user.getImage());
            user.setImage(decompressedImage);
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByTicketsBought(Ticket ticket) {
        User user = userRepository.findByTicketsBought(ticket);
        try {
            byte[] decompressedImage = decompressBytes(user.getImage());
            user.setImage(decompressedImage);
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByUserPurchases(Purchase purchase) {
        User user = userRepository.findByUserPurchases(purchase);
        try {
            byte[] decompressedImage = decompressBytes(user.getImage());
            user.setImage(decompressedImage);
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        try {
            byte[] decompressedImage = decompressBytes(user.getImage());
            user.setImage(decompressedImage);
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findByUsernameOrEmail(String username, String email) {
        List<User> userList = userRepository.findByUsernameOrEmail(username, email);
        List<User> decompressedUserList = new ArrayList<>();
        for (User user : userList) {
            try {
                byte[] decompressedImage = decompressBytes(user.getImage());
                user.setImage(decompressedImage);
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
            decompressedUserList.add(user);
        }
        return decompressedUserList;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user.getImage()!=null){
            try {
                byte[] decompressedImage = decompressBytes(user.getImage());
                user.setImage(decompressedImage);
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User findByUsernameUncompressed(String username) {
        return  userRepository.findByUsername(username);
    }

    @Override
    @Async
    public void sendEmailToUser(List<Guest> guest,Ticket userTicket, List<Ticket> guestsTicket, User user, MovieShow movieShow){
       int numOfReservedTickets = guestsTicket.size();
       String idOfGuestsTickets = null;
       String guestsText = "";
       if(numOfReservedTickets!=0){
           idOfGuestsTickets="";
       }
       for (Ticket ticket:guestsTicket){
           idOfGuestsTickets=idOfGuestsTickets+ticket.getId().toString()+",";
       }
        if(numOfReservedTickets>0){
            guestsText="Στο όνομά σας έχουν κρατηθεί επιπλέον "+numOfReservedTickets+" εισιτήρια με αριθμό "+idOfGuestsTickets+" για τους φίλους σας.";
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setSubject("Κράτηση εισιτηρίων");
        msg.setText("Η κράτησή σας για την ταινία \'"+movieShow.getMovieOfMovieShow().getTitle()+
                "\' την "+ DateUtils.formatDate(movieShow.getShowDate())+" και ώρα " +
                DateUtils.getTimeString(movieShow.getStartTime())+" έχει ολοκληρωθεί. Ο αριθμός εισιτηρίου σας είναι "
                +userTicket.getId() +" Παρακαλούμε να τον έχετε μαζί σας για τον έλεγχο στην είσοδο. "+guestsText);
        javaMailSender.send(msg);
    }

    @Override
    public List<User> findBuyersByMovieShow_Id(Integer movieShowId) {
        return userRepository.findBuyersByMovieShow_Id(movieShowId);
    }

}
