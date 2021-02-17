package com.protal.myApp.Service;

import com.protal.myApp.Entity.Guest;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.Ticket;
import com.protal.myApp.Repository.GuestRepository;
import com.protal.myApp.Utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailToGuests(Guest guest, Ticket ticket, String userName, MovieShow movieShow){
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(guest.getEmail());
            msg.setSubject("Είσαστε προσκεκλημένος!");
            msg.setText("Ο/Η "+ userName +" σας έχει προσκαλέσει στην προβολή της ταινίας "+movieShow.getMovieOfMovieShow().getTitle()+
                    " την "+DateUtils.formatDate(movieShow.getShowDate())+" και ώρα " +
                    DateUtils.getTime(movieShow.getStartTime())+
                    ". Ο αριθμός εισιτηρίου σας είναι "+ticket.getId()+". Παρακαλούμε να τον έχετε μαζί σας για τον έλεγχο στην είσοδο. ");
            javaMailSender.send(msg);
    }

    @Override
    public void saveGuest(Guest g) {
        guestRepository.save(g);
    }




//    @Override
//    public void updateGuestTicket(Guest guest, Ticket ticketOfGuest) {
//        guestRepository.updateGuestTicket(guest,ticketOfGuest);
//    }
}
