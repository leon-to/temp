package com.ece496.genealgo.object;

import java.time.LocalDateTime;

public class Timeslot{


    public final LocalDateTime datetime;

    public Timeslot(LocalDateTime datetime){
        this.datetime = datetime;
    }

    public Timeslot(int year, int month, int day, int hour){
        this.datetime = LocalDateTime.of(year, month, day, hour, 0);
    }

    @Override
    public String toString(){
        return "Timeslot: " + datetime.getMonth() + "-" + datetime.getDayOfMonth() + "-" + datetime.getHour();
    }
}