package com.protal.myApp.Utils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static Date getDateFromSeconds(Long seconds){
        Long millis = seconds*1000;
        DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
        Date result = new Date(millis);
        return result;
    }

    public static Date getDateFromMillis(Long millis){
        Date result = new Date(millis);
        return result;
    }

    public static Date getCurrentDate() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public static Date getMovieShowDate(Date day, Date time){
        Long millisDay = day.getTime();
        Long millisTime = time.getTime();
        Long start=millisDay+millisTime;
        return getDateFromMillis(start);
    }

    public static String formatDate(Date date){
       return new SimpleDateFormat("dd-MMM-yyyy").format(date);
    }


    public static String getTime(Date date){
        long millis = date.getTime();
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));

    }
}
