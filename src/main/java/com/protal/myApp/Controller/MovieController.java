package com.protal.myApp.Controller;

import com.protal.myApp.Entity.*;
import com.protal.myApp.Service.*;
import com.protal.myApp.Utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class MovieController {
    @Autowired
    MovieService movieService;
    @Autowired
    MovieShowService movieShowService;
    @Autowired
    RoomService roomService;
    @Autowired
    TicketService ticketService;
    @Autowired
    GuestService guestService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/movies", method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/moviesByMovieShowId", method = RequestMethod.OPTIONS)
    public ResponseEntity handleMovies() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path = "/movies/{movieId}")
    public ResponseEntity<Movie> getMovie(@PathVariable("movieId") Integer movieId) {
        Movie movie = movieService.findById(movieId);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    @GetMapping(path = "/movies")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movieList = movieService.findAll();
        return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
    }

    @GetMapping(path = "/moviesByMovieShowId")
    public ResponseEntity<List<Movie>> getMoviesByMovieShowId() {
        List<Movie> movieList = movieService.findAllOrderByMovieShowId();
        return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
    }

    @GetMapping(path = "/movies/movieshows/{movieShowId}")
    public ResponseEntity<Movie> getMovieByMovieShowId(@PathVariable("movieShowId") Integer movieShowId) {
        MovieShow movieShow = movieShowService.findById(movieShowId);
        Movie movie = movieService.findByMovieShowsOfMovieContains(movieShow);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    @PostMapping(path = "/movies/save")
    public ResponseEntity saveMovie(@RequestParam(value = "imageFile", required = false) MultipartFile file,
                                    @RequestParam("title") String title,
                                    @RequestParam("movie_year") Integer movieYear,
                                    @RequestParam("rating") Double rating,
                                    @RequestParam("description") String description,
                                    @RequestParam("movieShowId") Integer movieShowId,
                                    @RequestParam("editedMovieId") Integer editedMovieId,
                                    @RequestParam("capacity") Integer editedCapacity,
                                    @RequestParam("date") String dateString,
                                    @RequestParam("time") String timeString
    ) {
        List<String> emailsToSendMessage = new ArrayList<>();
        String dateTimeEditedMessage = "";
        String capacityEditedMessage = "";
        Movie editedMovie = movieService.findById(editedMovieId);
        String editedMessage = "Η προβολή της ταινίας \'" + editedMovie.getTitle() + "\' άλλαξε. Δείτε παρακάτω τις λεπτομέριες και επικοινωνήστε με το τμήμα κράτησης για αλλαγή του εισιτηρίου σας. \n";
        movieService.updateMovie(file, title, movieYear, rating, description, editedMovieId);
        String movieEditedMessage = "Τίτλος : " + editedMovie.getTitle() + "\n";
        MovieShow editedMovieShow = movieShowService.findById(movieShowId);
        movieShowService.updateMovieShow(editedMovieShow, dateString, timeString);
        //αν έχει αλλάξει η ώρα προβολής βρίσκω τα εισιτηρια που έχουν εκδοθεί για αυτή την προβολή
        // και ενημερώνω τον χρηστη που 'εκανε την αγορά και τους προσκεκλημένους
        List<Ticket> editedTickets = ticketService.findByMovieShow(editedMovieShow);
        List<User> buyers = editedTickets.stream()
                .map(Ticket::getBuyer)
                .distinct()
                .collect(toList());
        for (User buyer : buyers) {
            try {
                String email = buyer.getEmail();
                emailsToSendMessage.add(email);
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
        }
        List<Integer> guestsId = editedTickets.stream()
                .map(Ticket::getGuestId)
                .distinct()
                .collect(toList());
        guestsId.removeAll(Collections.singleton(null));
        for (Integer integer : guestsId) {
            try {
                Guest g = guestService.findById(integer);
                if (g != null) {
                    String email = g.getEmail();
                    emailsToSendMessage.add(email);
                }
            } catch (NullPointerException ne) {
                ne.printStackTrace();
            }
        }
        dateTimeEditedMessage = "Προβολή : " + DateUtils.formatDate(editedMovieShow.getShowDate()) + " - ώρα " +
                DateUtils.getTimeString(editedMovieShow.getStartTime()) + "\n";
        roomService.updateRoomCapacity(editedMovieShow.getRoomOfMovieShow(), editedCapacity);
        Integer availableTickets = editedCapacity - editedMovieShow.getShowTickets().size();
        if (availableTickets < 1) {
            capacityEditedMessage = "Δεν υπάρχουν διαθέσιμα εισιτήρια για την προβολή. Επικοινωνήστε για πιθανή αλλαγή του εισιτηρίου σας.";
        } else {
            capacityEditedMessage = "Υπάρχουν " + availableTickets + " διαθέσιμα εισιτήρια για την προβολή";
        }
        String message = editedMessage + movieEditedMessage + dateTimeEditedMessage + capacityEditedMessage;
        for (String email : emailsToSendMessage)
            movieService.infromAboutMovieChange(email, message);
        return new ResponseEntity(HttpStatus.OK);
    }

}
