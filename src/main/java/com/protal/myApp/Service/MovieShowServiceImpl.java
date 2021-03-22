package com.protal.myApp.Service;

import com.protal.myApp.Entity.Movie;
import com.protal.myApp.Entity.MovieShow;
import com.protal.myApp.Entity.Room;
import com.protal.myApp.Repository.MovieShowRepository;
import com.protal.myApp.Utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;;
import java.text.ParseException;
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
    public void saveMovieShow(MovieShow movieShow) {
        movieShowRepository.save(movieShow);
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

    @Override
    public void updateMovieShow(MovieShow movieShow, String showDate, String showTime) {
        Long dateMillis = null, timeMillis=null;
        Date date;
        try {
            dateMillis = Long.parseLong(showDate);
        } catch (NumberFormatException ne) {
            ne.printStackTrace();
        }
        try {
            dateMillis = DateUtils.getMillisFromDateString(showDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        date=new Date(dateMillis);
        try {
            timeMillis = Long.parseLong(showTime);
        } catch (NumberFormatException ne) {
            ne.printStackTrace();
        }
        try {
            timeMillis = DateUtils.getMillisFromTimeString(showTime);
        } catch (ParseException | IndexOutOfBoundsException pe) {
            pe.printStackTrace();
        }
         movieShow.setStartTime(new java.sql.Time(timeMillis));
        Long endTimeMillis = timeMillis+7200000L;
         movieShow.setEndTime(new java.sql.Time(endTimeMillis));
        movieShow.setShowDate(date);
        saveMovieShow(movieShow);
    }


}

