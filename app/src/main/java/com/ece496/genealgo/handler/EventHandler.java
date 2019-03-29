package com.ece496.genealgo.handler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.ece496.database.Event;

public class EventHandler{
    public List<Event> events;

    public EventHandler(){
        this.events = new ArrayList<Event>();
    }
    public EventHandler add_event(String name, Duration t_duration){
        events.add(new Event(name, t_duration));
        return this;
    }
    public EventHandler add_event(String name, Duration t_duration, LocalDateTime deadline){
        events.add(new Event(name, t_duration, deadline));
        return this;
    }
}