package com.protal.myApp.Service;

import com.protal.myApp.Entity.*;
import com.protal.myApp.Repository.GuestRepository;
import com.protal.myApp.Utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {
    @Autowired
    GuestRepository guestRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserService userService;

    @Override
    @Async
    public void sendEmailToGuests(Guest guest, Ticket ticket, String userName, MovieShow movieShow){
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(guest.getEmail());
            msg.setSubject("Είσαστε προσκεκλημένος!");
            msg.setText("Ο/Η "+ userName +" σας έχει προσκαλέσει στην προβολή της ταινίας \'"+movieShow.getMovieOfMovieShow().getTitle()+
                    "\' την "+DateUtils.formatDate(movieShow.getShowDate())+" και ώρα " +
                    movieShow.getStartTime()+
                    ". Ο αριθμός εισιτηρίου σας είναι "+ticket.getId()+". Παρακαλούμε να τον έχετε μαζί σας για τον έλεγχο στην είσοδο. ");
            javaMailSender.send(msg);
    }

    @Override
    public void saveGuest(Guest g) {
        guestRepository.save(g);
    }

    @Override
    public Guest findById(Integer id) {
        return guestRepository.findById(id).orElse(null);
    }

    @Override
    public Guest findByTicketId(Integer id) {
        return guestRepository.findByTicketId(id);
    }

    @Override
    public List<Guest> getAttendantsOfMovieShow(Integer movieShowId) {
        List<Ticket> ticketList = ticketService.findByMovieShow_IdAndUsed(movieShowId,1);
        List<Guest> guestList = new ArrayList<>();
        for(Ticket ticket: ticketList){
            Guest g = guestRepository.findByTicketId(ticket.getId());
            if(g!=null && !g.getName().equals("")){
                guestList.add(g);
            }
        }
        List<User> buyers = userService.findBuyersByMovieShow_Id(movieShowId);
        for (User user: buyers){
            List<Ticket> tList = ticketService.findByBuyer_IdAndMovieShow_IdAndUsed(user.getId(),movieShowId,1);
            for(Ticket t: tList){
                if(t.getGuestId()==null){
                    Guest temp = new Guest(user.getName(),user.getEmail());
                    temp.setTicketId(t.getId());
                    guestList.add(temp);
                }
            }
        }
        return guestList;
    }

}
