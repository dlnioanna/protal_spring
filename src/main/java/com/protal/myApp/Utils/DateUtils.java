package com.protal.myApp.Utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static Date getDateFromSeconds(Long seconds) {
        Long millis = seconds * 1000;
        DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z");
        Date result = new Date(millis);
        return result;
    }

    public static Date getDateFromMillis(Long millis) {
        Date result = new Date(millis);
        return result;
    }

    public static Date getCurrentDate() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public static Date getMovieShowDate(Date day, Long time) {
        Long millisDay = day.getTime();
        Long start = millisDay + time;
        return getDateFromMillis(start);
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd-MMM-yyyy").format(date);
    }


    public static String getTimeString(Long millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));

    }

    public static Long getMillisFromDateString(String dateString) throws ParseException {
        Long imerominia = new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime();
        return imerominia;
    }
    public static Time getTimeFromMillis(Long millis) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        java.sql.Time time = new java.sql.Time(millis);
        return time;
    }

    public static Long getMillisFromTimeString(String timeString) throws ParseException {
        String[] timeSplit = timeString.split(":");
        System.out.println("timeSplit 0"+timeSplit[0]);
        System.out.println("timeSplit 1"+timeSplit[1]);
        Long hours = Long.parseLong(timeSplit[0]) * 60 * 60 * 1000;
        Long minutes = Long.parseLong(timeSplit[1]) * 60 * 1000;
        Long ora = hours + minutes;
        return ora;
    }
}
