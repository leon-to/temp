package com.ece496.genealgo.handler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import com.ece496.genealgo.object.Timeslot;

public class TimeslotHandler{
    private static final Logger logger = Logger.getGlobal();

    public List<Timeslot> timeslots;

    public TimeslotHandler(){
        this.timeslots = new ArrayList<Timeslot>();
    }

    public TimeslotHandler add_timeslot(
            int year,
            int month,
            int day,
            int hour)
    {
        timeslots.add(new Timeslot(year, month, day, hour));
        return this;
    }
}