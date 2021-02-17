package com.protal.myApp.Controller;

import com.protal.myApp.Entity.CalendarEvent;
import com.protal.myApp.Entity.Movie;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Service.MovieService;
import com.protal.myApp.Service.MovieShowService;
import com.protal.myApp.Utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class MovieShowController {
    @Autowired
    MovieShowService movieShowService;
    @Autowired
    MovieService movieService;

    @GetMapping(path = "/movieshows/movie/{movieId}")
    public ResponseEntity<List<MovieShow>> getMovieShowsOfMovie(@PathVariable("movieId") Integer movieId) {
        List<MovieShow> movieShowList = movieShowService.findByMovieOfMovieShow_Id(movieId);
        return new ResponseEntity<List<MovieShow>>(movieShowList, HttpStatus.OK);
    }

    @GetMapping(path = "/movieshows")
    public ResponseEntity<List<MovieShow>> getMovieShows() {
        List<MovieShow> movieShowList = movieShowService.findAll();
        return new ResponseEntity<List<MovieShow>>(movieShowList, HttpStatus.OK);
    }

    @GetMapping(path = "/movieshows/{movieShowId}")
    public ResponseEntity<MovieShow> getMovieShowById(@PathVariable("movieShowId") Integer movieShowId) {
       MovieShow movieShow = movieShowService.findById(movieShowId);
        return new ResponseEntity<MovieShow>(movieShow, HttpStatus.OK);
    }

    @GetMapping(path = "/movieshowsOnDate/{showDate}")
    public ResponseEntity<List<Movie>> getMoviesOnSelectedDate(@PathVariable("showDate") String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = Date.valueOf(date);
        List<Movie> moviesList = movieService.findByMovieShowsOfMovie_ShowDate(parsedDate);
        return new ResponseEntity<List<Movie>>(moviesList, HttpStatus.OK);
    }

    @GetMapping(path="/movieshows/calendarEvents")
    public ResponseEntity<List<CalendarEvent>> getCalendarEvents(){
        List<MovieShow> movieShowList = movieShowService.findAll();
        List<CalendarEvent> calendarEvents = new ArrayList<>();
        String color = CalendarEvent.COLOR_RED;
        for(MovieShow movieShow: movieShowList){
            int numberOfAvailableTickets = 0;
            try{
                numberOfAvailableTickets = movieShow.getRoomOfMovieShow().getCapacity()-movieShow.getShowTickets().size();
            } catch (NullPointerException ne){
                ne.printStackTrace();
            }
            if (numberOfAvailableTickets>20){
                color = CalendarEvent.COLOR_BLUE;
            } else if(numberOfAvailableTickets<20 && numberOfAvailableTickets>10){
                color = CalendarEvent.COLOR_YELLOW;
            }
            Date start= DateUtils.getMovieShowDate(movieShow.getShowDate(),movieShow.getStartTime());
            Date end= DateUtils.getMovieShowDate(movieShow.getShowDate(),movieShow.getEndTime());
            CalendarEvent calendarEvent = new CalendarEvent(start,end,
                    movieShow.getMovieOfMovieShow().getId(),movieShow.getMovieOfMovieShow().getTitle(), color);
            try {
                calendarEvents.add(calendarEvent);
            }catch (NullPointerException ne){
                ne.printStackTrace();
            }
        }
        return new ResponseEntity<List<CalendarEvent>>(calendarEvents, HttpStatus.OK);
    }
}
