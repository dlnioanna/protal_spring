package com.protal.myApp.Repository;

import com.protal.myApp.Entity.Movie;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.sql.Date;;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieShowRepository extends JpaRepository<MovieShow,Integer> {

   Optional<MovieShow> findById(Integer movieShowId);

    List<MovieShow>  findByMovieOfMovieShow(Movie movie);

    List<MovieShow>  findByMovieOfMovieShow_Id(Integer movieId);

    List<MovieShow> findByRoomOfMovieShow(Room room);

    List<MovieShow> findByRoomOfMovieShow_Id(Integer roomId);

    List<MovieShow> findByShowDate(Date showDate);

}
