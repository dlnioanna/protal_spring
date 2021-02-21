package com.protal.myApp.Service;

import com.protal.myApp.Entity.Movie;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.Room;

import java.sql.Date;;
import java.util.List;

public interface MovieShowService {
    List<MovieShow> findAll();

    void saveMovieShow(MovieShow movieShow);

    List<MovieShow>  findByMovieOfMovieShow(Movie movie);

    List<MovieShow>  findByMovieOfMovieShow_Id(Integer movieId);

    List<MovieShow> findByRoomOfMovieShow(Room room);

    List<MovieShow> findByRoomOfMovieShow_Id(Integer roomId);

    List<MovieShow> findByShowDate(Date showDate);

   MovieShow findById(Integer movieShowId);

}
