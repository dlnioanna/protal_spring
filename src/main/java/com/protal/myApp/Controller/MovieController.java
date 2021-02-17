package com.protal.myApp.Controller;

import com.protal.myApp.Entity.Movie;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.User;
import com.protal.myApp.Service.MovieService;
import com.protal.myApp.Service.MovieShowService;
import com.protal.myApp.Service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static com.protal.myApp.Utils.CompressUtils.compressBytes;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class MovieController {
    @Autowired
    MovieService movieService;
    @Autowired
    MovieShowService movieShowService;

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
        System.out.println("movies are "+movieList.size());
        return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
    }

    @GetMapping(path = "/moviesByMovieShowId")
    public ResponseEntity<List<Movie>> getMoviesByMovieShowId() {
        List<Movie> movieList = movieService.findAllOrderByMovieShowId();
        System.out.println("movie lis size is "+movieList.size());
        return new ResponseEntity<List<Movie>>(movieList, HttpStatus.OK);
    }

    @GetMapping(path = "/movies/movieshows/{movieShowId}")
    public ResponseEntity<Movie> getMovieByMovieShowId(@PathVariable("movieShowId") Integer movieShowId) {
        MovieShow movieShow = movieShowService.findById(movieShowId);
        Movie movie = movieService.findByMovieShowsOfMovieContains(movieShow);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    //todo na to allakso
    @PostMapping(path = "/movies/save")
    public ResponseEntity saveMovie(@RequestParam (value = "imageFile", required = false) MultipartFile file,@RequestParam ("title") String title,
                                    @RequestParam ("movie_year") String movieYear, @RequestParam ("rating") Long rating,
                                    @RequestParam ("description") String description, @RequestParam ("movieShowId") String movieShowId) throws IOException {
        byte[] image =null;
        if(file!=null) {
            image = compressBytes(file.getBytes());
        }
        System.out.println("image "+file+" title "+title+"movie_year "+movieYear+" rating "+rating+"desc "+description+" movie show id"+ movieShowId);
//        List<User> userList = userService.findByUsernameOrEmail(username, email);
//        if (userList == null || userList.size() == 0) {
//            User newUser = new User(name, lastName, telephone, email,
//                    username, password, User.ROLE_USER);
//            newUser.setImage(image);
//            userService.saveUser(newUser);
            return new ResponseEntity(HttpStatus.OK);
//        } else {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
