package com.protal.myApp.Service;

import com.protal.myApp.Entity.Movie;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.Room;
import com.protal.myApp.Repository.MovieShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;;
import java.util.List;

@Service
public class MovieShowServiceImpl implements MovieShowService {
    @Autowired
    MovieShowRepository movieShowRepository;

    @Override
    public List<MovieShow> findAll() {
        return movieShowRepository.findAll();
    }

    @Override
    public List<MovieShow> findByMovieOfMovieShow(Movie movie) {
        return movieShowRepository.findByMovieOfMovieShow(movie);
    }

    @Override
    public List<MovieShow> findByMovieOfMovieShow_Id(Integer movieId) {
        return movieShowRepository.findByMovieOfMovieShow_Id(movieId);
    }

    @Override
    public List<MovieShow> findByRoomOfMovieShow(Room room) {
        return movieShowRepository.findByRoomOfMovieShow(room);
    }

    @Override
    public List<MovieShow> findByRoomOfMovieShow_Id(Integer roomId) {
        return movieShowRepository.findByMovieOfMovieShow_Id(roomId);
    }

    @Override
    public List<MovieShow> findByShowDate(Date showDate) {
        return movieShowRepository.findByShowDate(showDate);
    }

    @Override
    public MovieShow findById(Integer movieShowId) {
        return movieShowRepository.findById(movieShowId).orElse(null);
    }
}
