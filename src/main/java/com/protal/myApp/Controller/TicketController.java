package com.protal.myApp.Controller;

import com.protal.myApp.Entity.*;
import com.protal.myApp.Service.*;
import com.protal.myApp.Utils.DateUtils;
import com.protal.myApp.jwt.GoogleTokenVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class TicketController {
    @Autowired
    MovieShowService movieShowService;
    @Autowired
    MovieService movieService;
    @Autowired
    TicketService ticketService;
    @Autowired
    GuestService guestService;
    @Autowired
    UserService userService;
    @Autowired
    PurchaseService purchaseService;

    @RequestMapping(value = "/purchase_tickets", method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path="ticketCheck/{ticketNumber}")
    public ResponseEntity checkTicket(@PathVariable Integer ticketNumber){
        System.out.println("ticket number is "+ticketNumber);
       Ticket ticket = ticketService.findById(ticketNumber);
       if(ticket==null){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }else{
           ticket.setUsed(1);
           ticketService.saveTicket(ticket);
           return new ResponseEntity(HttpStatus.OK);
       }

    }

    @PostMapping(value = "/purchase_tickets")
    public ResponseEntity buyTickets(@RequestParam(name = "numberOfTickets") Integer numberOfTickets, @RequestParam(name = "userName") String userName,
                                     @RequestParam(name = "guestList", required = false) String guestList, @RequestParam(name = "isSocialUser") Integer isSocialUser,
                                     @RequestParam(name = "socialUserEmail", required = false) String socialUserEmail,
                                     @RequestParam(name = "movieShowId") Integer movieShowId, @RequestParam(name = "socialUser", required = false) String socialUser) {
        MovieShow movieShow = movieShowService.findById(movieShowId);
        User user = null;
        //αν έχει συνδεθεί μέσω google του φτιαχνω λογαριασμο με password socialUser και τηλέφωνο 0000000000 και τα υπόλοιπα στοιχεία κενά
        if (isSocialUser == 1) {
            User existingUser = userService.findByEmail(socialUserEmail);
            if (existingUser != null) {
                user = existingUser;
            } else {
                user = new User(userName, "", 0000000000L, socialUserEmail, userName, "socialUser", "USER");
                userService.saveUser(user);
            }
        } else {
            user = userService.findByUsernameUncompressed(userName);
        }
        System.out.println("user is " + user.getUsername());
        //αποθηκεύω την αγορά εισιτηρίων
        Purchase purchase = new Purchase(user);
        purchaseService.savePurchase(purchase);
        System.out.println("purchase saved ");
        //το frontend δεν επιτρέπει λιγότερα από 1 εισιτήρια. Το πρώτο το κρατάω για τον user και τα υπόλοιπα τα μοιράζω
        // στους καλεσμένους. Αν περισσεύουν απλά αποθηκεύονται στον λογαριασμό του χρήστη για να μπορέι να δεί
        // προηγούμενες αγορές του
        Ticket userTicket = new Ticket(DateUtils.getCurrentDate(), 0, purchase, movieShow, user);
        ticketService.saveTicket(userTicket);
        System.out.println("user ticket saved ");

        //φτιάχνω τη λίστα με τους καλεσμένους
        List<Guest> guests = new ArrayList<>();
        if (guestList != null) {
            JSONObject obj = new JSONObject(guestList);
            JSONArray arr = obj.getJSONArray("guests");
            for (int i = 0; i < arr.length(); i++) {
                String guestName = arr.getJSONObject(i).getString("guestName");
                String guestEmail = arr.getJSONObject(i).getString("guestEmail");
                Guest guest = new Guest(guestName, guestEmail);
                guests.add(guest);
            }
        }
        //φτιάχνω τη λίστα με τα εισιτηρια
        List<Ticket> guestsTickets = new ArrayList<>();
        for (int i = 0; i < numberOfTickets - 1; i++) {
            Ticket guestTicket = new Ticket(DateUtils.getCurrentDate(), 0, purchase, movieShow, user);
           ticketService.saveTicket(guestTicket);
            guestsTickets.add(guestTicket);
            if(i<guests.size()){
                Guest g = guests.get(i);
                g.setTicketId(guestTicket.getId());
                guestService.saveGuest(g);
                guestTicket.setGuestId(g.getId());
                ticketService.saveTicket(guestTicket);
            }
        }
        for (int i = 0; i < guests.size(); i++) {
            Guest g = guests.get(i);
            guestService.sendEmailToGuests(g, guestsTickets.get(i), userName, movieShow);
        }
        purchase.setTicketsPurchased(guestsTickets);
        purchaseService.savePurchase(purchase);
        userService.sendEmailToUser(guests, userTicket, guestsTickets, user, movieShow);
        return new ResponseEntity(HttpStatus.OK);
    }

}
