package com.protal.myApp.Controller;

import com.protal.myApp.Entity.*;
import com.protal.myApp.Service.*;
import com.protal.myApp.Utils.DateUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.protal.myApp.Utils.CompressUtils.compressBytes;
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
        System.out.println("movies searching ");
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
                                    @RequestParam("date") Long date,
                                    @RequestParam("time") Long time
    ) throws IOException {
        System.out.println("image " + file + " title " + title + "movie_year " + movieYear + " rating " + rating +
                "desc " + description + " movie show id" + movieShowId
                + " editedMovieId " + editedMovieId + " capacity " + editedCapacity + " date " + date + " time " + time);
        List<String> emailsToSendMessage = new ArrayList<>();
        String dateTimeEditedMessage = "";
        String capacityEditedMessage = "";
        Movie editedMovie = movieService.findById(editedMovieId);
        String editedMessage = "Η προβολή της ταινίας \'" + editedMovie.getTitle() + "\' άλλαξε. Δείτε παρακάτω τις λεπτομέριες και επικοινωνήστε με το τμήμα κράτησης για αλλαγή του εισιτηρίου σας. \n";
        editedMovie.setTitle(title);
        editedMovie.setMovieYear(movieYear);
        editedMovie.setRating(rating);
        editedMovie.setDescription(description);
        byte[] image = null;
        if (file != null) {
            image = compressBytes(file.getBytes());
            editedMovie.setPoster(image);
        } else {
            image = compressBytes(editedMovie.getPoster());
            editedMovie.setPoster(image);
        }
        movieService.saveMovie(editedMovie);
        String movieEditedMessage = "Τίτλος : " + editedMovie.getTitle() + "\n";
        MovieShow editedMovieShow = movieShowService.findById(movieShowId);
        if ((editedMovieShow.getShowDate() != DateUtils.getDateFromMillis(date)) ||
                (editedMovieShow.getStartTime() != DateUtils.getDateFromMillis(time))) {
            editedMovieShow.setShowDate(DateUtils.getDateFromMillis(date));
            editedMovieShow.setStartTime(DateUtils.getDateFromMillis(time));
          Long endTime = time+7200000L;
          editedMovieShow.setEndTime(DateUtils.getDateFromMillis(endTime));
          movieShowService.saveMovieShow(editedMovieShow);
        }
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
                DateUtils.getTime(editedMovieShow.getStartTime()) + "\n";

        Room editedRoom = editedMovieShow.getRoomOfMovieShow();
        Integer capacity = editedRoom.getCapacity();
        editedRoom.setCapacity(editedCapacity);
        roomService.saveRoom(editedRoom);
        Integer availableTickets = editedCapacity -editedMovieShow.getShowTickets().size();
        if (availableTickets<1) {
            capacityEditedMessage = "Δεν υπάρχουν διαθέσιμα εισιτήρια για την προβολή. Επικοινωνήστε για πιθανή αλλαγή του εισιτηρίου σας.";
        } else {
            capacityEditedMessage="Υπάρχουν "+availableTickets+" διαθέσιμα εισιτήρια για την προβολή";
        }

        String message = editedMessage+ movieEditedMessage+ dateTimeEditedMessage+ capacityEditedMessage;
        for(String email:emailsToSendMessage )
        movieService.infromAboutMovieChange(email, message);
        return new ResponseEntity(HttpStatus.OK);
    }

    //todo na to sviso
    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        Movie movie = new Movie("Léon ", 1994, 9.0);
        movie.setPoster(compressBytes(file.getBytes()));
        movie.setDescription("Mathilda, a 12-year-old girl, is reluctantly taken in by Léon, a professional assassin, after her family is murdered. An unusual relationship forms as she becomes his protégée and learns the assassin's trade.");
        movieService.saveMovie(movie);
        return ResponseEntity.status(HttpStatus.OK);
    }

    //todo na to sviso
    @GetMapping(path = {"/getImage"})
    public ResponseEntity<Movie> getImage() throws IOException {
        final Movie retrievedMovie = movieService.findById(3);
//        byte[] poster = decompressBytes(retrievedMovie.getPoster());
//        Movie mv = new Movie(retrievedMovie.getTitle(), retrievedMovie.getMovieYear(),
//                decompressBytes(retrievedMovie.getPoster()), retrievedMovie.getRating());
//        System.out.println("poster is  " + retrievedMovie.getPoster().toString());
        return new ResponseEntity<Movie>(retrievedMovie, HttpStatus.OK);
    }


}
