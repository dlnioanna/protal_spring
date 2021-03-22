package com.protal.myApp.Repository;

import com.protal.myApp.Entity.Movie;
import com.protal.myApp.Entity.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

    Movie findByTitle(String title);

    List<Movie> findByMovieYear(Integer movieYear);

    List<Movie> findByMovieShowsOfMovie_ShowDate(Date showDate);

    List<Movie> findByMovieShowsOfMovieIn(List<MovieShow> movieShowList);

    Movie findByMovieShowsOfMovieContains(MovieShow movieShow);

    @Query("select m from Movie m join MovieShow ms on m.id=ms.movieOfMovieShow.id order by ms.roomOfMovieShow.id, ms.id")
    List<Movie> findAllOrderByMovieShowId();
}
