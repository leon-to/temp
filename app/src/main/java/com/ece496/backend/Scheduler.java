package com.ece496.backend;

import android.os.Parcelable;

import com.ece496.database.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.ece496.FrontEndEvent;
/**
 * This class take an event object and add to 
 */
public class Scheduler {

    public Scheduler() {

    }

    public void schedule(Event event){
        event.save();
    }

    public List<Event> get_backend_events_of_date(LocalDate date){
        List<Event> db_events = Event.listAll(Event.class);
        List<Event> events = new ArrayList<Event>();

        for(Event event: db_events){
            if (event.t_start.toLocalDate().isEqual(date)){
                events.add(event);
            }
        }

        return events;
    }

    public List<Event> get_backend_events_of_date(int year, int month, int day){
        return get_backend_events_of_date(LocalDate.of(year, month, day));
    }

    public List<FrontEndEvent> get_frontend_events_of_date(LocalDate date){
        List<Event> backend_events = get_backend_events_of_date(date);
        List<FrontEndEvent> frontend_events = new ArrayList<FrontEndEvent>();

        for(Event event: backend_events){
            frontend_events.add(event.toFrontEndEvent());
        }

        return frontend_events;
    }

    public List<FrontEndEvent> get_frontend_events_of_day(int year, int month, int day){
        return get_frontend_events_of_date(LocalDate.of(year, month, day));
    }
}
