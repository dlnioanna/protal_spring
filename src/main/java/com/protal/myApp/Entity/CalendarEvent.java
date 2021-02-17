package com.protal.myApp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEvent {
    private Date start;
    private Date end;
    private Integer id;
    private String title;
    private String color;

    public static final String COLOR_RED="colors.red";
    public static final String COLOR_YELLOW="colors.yellow";
    public static final String COLOR_BLUE="colors.blue";
}
