package com.ece496.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventBuilder {
    String name;

    LocalTime t_start;
    LocalTime t_end;
    LocalDate t_when;

    LocalDateTime t_deadline;

    int frequency;

//    public EventBuilder name(String name){
//        this.name = name;
//        return this;
//    }
//
//    public EventBuilder t_start(int year, int month, int day, int hour, int minute, int second){
//        this.t_start = LocalDateTime.of(year, month, day, hour, minute, second);
//        return this;
//    }
//
//    public EventBuilder t_end(int year, int month, int day, int hour, int minute, int second){
//        this.t_end = LocalDateTime.of(year, month, day, hour, minute, second);
//        return this;
//    }
//
//    public EventBuilder t_deadline(int year, int month, int day, int hour, int minute, int second){
//        this.t_deadline = LocalDateTime.of(year, month, day, hour, minute, second);
//        return this;
//    }
//
//    public Event build(){
//        return new Event(name, t_start, t_end, t t_deadline);
//    }
}
