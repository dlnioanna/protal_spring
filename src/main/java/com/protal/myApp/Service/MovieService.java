package com.protal.myApp.Service;

import com.protal.myApp.Entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;;
import java.util.List;

public interface MovieService {
    Movie findByTitle(String title);

    List<Movie> findByMovieYear(Integer movieYear);

    List<Movie> findAll();

    Movie findById(Integer id);

    void saveMovie(Movie movie);

    List<Movie> findByMovieShowsOfMovie_ShowDate(Date showDate);

    List<Movie> findByMovieShowsOfMovieIn(List<MovieShow> movieShowList);

    Movie findByMovieShowsOfMovieContains(MovieShow movieShow);

    List<Movie> findAllOrderByMovieShowId();

    void infromAboutMovieChange(String email, String message);

    void updateMovie(MultipartFile file, String title, Integer movieYear, Double rating, String description, Integer editedMovieId);
}
