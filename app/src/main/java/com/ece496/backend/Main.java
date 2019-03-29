package com.ece496.backend;

import com.ece496.database.Event;
import com.ece496.genealgo.object.Timeslot;
import com.orm.SugarContext;
import com.orm.util.SugarConfig;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        DynamicScheduler scheduler = new DynamicScheduler();

        List<Event> db_events = scheduler.db_events;
        db_events = new ArrayList<>();
        db_events.add(make_event("Math assignment", 1, 9, 30, 1, 11,0));
        db_events.add(make_event("Math assignment", 20, 20, 0, 20, 21,0));
        db_events.add(make_event("Math assignment", 20, 10, 30, 20, 11,30));
        db_events.add(make_event("Math assignment", 21, 14, 0, 21, 16,0));
        db_events.add(make_event("Math assignment", 21, 16, 0, 21, 17,30));
        db_events.add(make_event("Math assignment", 23, 9, 30, 23, 11,0));
        db_events.add(make_event("English assignment", 23, 12, 30, 23, 15,0, false));
        db_events.add(make_event("Math assignment", 23, 9, 30, 23, 11,0));
        scheduler.db_events = db_events;


        scheduler.schedule(
                "Essay",
                "Write 100k words",
                Duration.ofHours(3),
                LocalDateTime.now().plusDays(5)
        );

        System.out.println(scheduler.available_timeslots.size());

//        System.out.println("---DB Events---");
//        for(Event event: db_events)
//            System.out.println(event);
//
//        System.out.println("---Unchangeable Events---");
//        for(Event event: scheduler.unchangeable_events)
//            System.out.println(event);
//
//        System.out.println("---Changeable Events---");
//        for(Event event: scheduler.changeable_events)
//            System.out.println(event);
//
//        System.out.println("---Max Hour Duration---");
//        System.out.println(scheduler.max_hour_duration);
//
//        System.out.println("---Available Timeslots---");
//        for(Timeslot timeslot: scheduler.available_timeslots)
//            System.out.println(timeslot);
    }

    public static Event make_event(
            String name,
            int d_start, int h_start, int m_start,
            int d_end, int h_end, int m_end
    ){
        return new Event(
            name,
            LocalDateTime.of(2019, 3, d_start, h_start, m_start),
            LocalDateTime.of(2019, 3, d_end, h_end, m_end)
        );
    }

    public static Event make_event(
            String name,
            int d_start, int h_start, int m_start,
            int d_end, int h_end, int m_end,
            boolean changeable
    ){
        return new Event(
                name,
                LocalDateTime.of(2019, 3, d_start, h_start, m_start),
                LocalDateTime.of(2019, 3, d_end, h_end, m_end),
                changeable
        );
    }
}