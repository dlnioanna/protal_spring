package com.protal.myApp.Service;

import com.protal.myApp.Entity.Movie;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.Room;
import com.protal.myApp.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static com.protal.myApp.Utils.CompressUtils.decompressBytes;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public Movie findByTitle(String title) {
        Movie movie = movieRepository.findByTitle(title);
        byte[] decompressedPoster = decompressBytes(movie.getPoster());
        movie.setPoster(decompressedPoster);
        return movie;
    }

    @Override
    public List<Movie> findByMovieYear(Integer movieYear) {
        List<Movie> movieList = movieRepository.findByMovieYear(movieYear);
        List<Movie> decompressedMovieList = new ArrayList<>();
        for (Movie movie : movieList) {
            byte[] decompressedPoster = decompressBytes(movie.getPoster());
            movie.setPoster(decompressedPoster);
            decompressedMovieList.add(movie);
        }
        return decompressedMovieList;
    }


    @Override
    public List<Movie> findAll() {
        List<Movie> movieList = movieRepository.findAll();
        List<Movie> decompressedMovieList = new ArrayList<>();
        System.out.println("movieservice list "+movieList.size());
        for (Movie movie : movieList) {
            byte[] decompressedPoster = decompressBytes(movie.getPoster());
            movie.setPoster(decompressedPoster);
            decompressedMovieList.add(movie);
        }
        return decompressedMovieList;
    }

    @Override
    public Movie findById(Integer movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        byte[] decompressedPoster = decompressBytes(movie.getPoster());
        movie.setPoster(decompressedPoster);
        return movie;
    }

    @Override
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public List<Movie> findByMovieShowsOfMovie_ShowDate(Date showDate) {
        List<Movie> movieList = movieRepository.findByMovieShowsOfMovie_ShowDate(showDate);
        List<Movie> decompressedMovieList = new ArrayList<>();
        for (Movie movie : movieList) {
            byte[] decompressedPoster = decompressBytes(movie.getPoster());
            movie.setPoster(decompressedPoster);
            decompressedMovieList.add(movie);
        }
        return decompressedMovieList;
    }

    @Override
    public List<Movie> findByMovieShowsOfMovieIn(List<MovieShow> movieShowList) {
        List<Movie> movieList = movieRepository.findByMovieShowsOfMovieIn(movieShowList);
        List<Movie> decompressedMovieList = new ArrayList<>();
        for (Movie movie : movieList) {
            byte[] decompressedPoster = decompressBytes(movie.getPoster());
            movie.setPoster(decompressedPoster);
            decompressedMovieList.add(movie);
        }
        return decompressedMovieList;
    }

    @Override
    public Movie findByMovieShowsOfMovieContains(MovieShow movieShow) {
        Movie movie = movieRepository.findByMovieShowsOfMovieContains(movieShow);
        byte[] decompressedPoster = decompressBytes(movie.getPoster());
        movie.setPoster(decompressedPoster);
        return movie;
    }

    @Override
    public List<Movie> findAllOrderByMovieShowId() {
        List<Movie> movieList = movieRepository.findAllOrderByMovieShowId();
        List<Movie> decompressedMovieList = new ArrayList<>();
        for (Movie movie : movieList) {
            byte[] decompressedPoster = decompressBytes(movie.getPoster());
            movie.setPoster(decompressedPoster);
            decompressedMovieList.add(movie);
        }
        return decompressedMovieList;
    }

}
