package com.ece496.backend;

import com.ece496.database.Event;
import com.ece496.genealgo.GeneticAlgorithmModel;
import com.ece496.genealgo.handler.EventHandler;
import com.ece496.genealgo.handler.FitnessHandler;
import com.ece496.genealgo.handler.PopulationHandler;
import com.ece496.genealgo.handler.TimeslotHandler;
import com.ece496.genealgo.object.Timeslot;
import com.ece496.genealgo.handler.EventHandler;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import static java.util.TimeZone.getAvailableIDs;

public class DynamicScheduler {
    Duration        t_duration;
    LocalDateTime   t_deadline;
    LocalDateTime   t_now;
    List<Event>     db_events;

    List<Event>     unchangeable_events;
    List<Event>     changeable_events;
    List<Event>     history_events;
    Event           scheduling_event;

    List<Timeslot>  available_timeslots;
    int             max_hour_duration;



    public void schedule(String name, String description,
                         Duration t_duration, LocalDateTime t_deadline){
        this.t_duration = t_duration;
        this.t_deadline = t_deadline;
        this.t_now      = LocalDateTime.now().plusHours(1);

        this.scheduling_event = new Event(name, description, t_duration);

//        this.db_events   = Event.listAll(Event.class);
        this.unchangeable_events = new ArrayList<Event>();
        this.changeable_events = new ArrayList<Event>();
        this.history_events = new ArrayList<Event>();

        this.available_timeslots = new ArrayList<Timeslot>();
        this.max_hour_duration = (int)t_duration.toHours();

        find_affected_and_changeable_events();
        find_max_hour_duration();
        find_available_timeslots();

        changeable_events.add(scheduling_event);

        // GA algorithm
        EventHandler h_event = new EventHandler();
        TimeslotHandler h_timeslot = new TimeslotHandler();
        PopulationHandler h_population = new PopulationHandler(h_event, h_timeslot);
        FitnessHandler h_fitness = new FitnessHandler(h_event, h_timeslot, h_population);
        GeneticAlgorithmModel model = new GeneticAlgorithmModel(h_population, h_fitness);

        h_event.events = changeable_events;
        h_timeslot.timeslots = available_timeslots;

        h_population.set_characteristics(
                20, // max population size
                h_event.events.size(), // chromosome length
                h_timeslot.timeslots.size() // number of gene type
        );
        model.train(
                20, // number of initialized population
                0.3, // probability of mutation
                20 //epochs
        );
        System.out.println("Best fit: " + h_population.get_bestfit());

        // delete all events and resave
//        Event.deleteAll(Event.class);
//        List<Event> final_events = new ArrayList<Event>();
//        final_events.addAll(history_events);
//        final_events.addAll(changeable_events);
//        final_events.addAll(unchangeable_events);
//        for(Event event: final_events){
//            event.save();
//        }
    }

    private void find_affected_and_changeable_events(){
        for(Event event: db_events){
            if(event.t_start.isBefore(t_now)){
                history_events.add(event);
            }else if(event.t_start.isAfter(t_now) && event.t_end.isBefore(t_deadline))
            {
                if(event.changeable){
                    changeable_events.add(event);
                }else {
                    unchangeable_events.add(event);
                }
            }
        }
    }

    public void find_max_hour_duration(){
        for(Event event:changeable_events){
            int current_hour_duration = (int)Duration.between(event.t_start, event.t_end).toHours();
            if(max_hour_duration < current_hour_duration)
                max_hour_duration = current_hour_duration;
        }
    }

    public void find_available_timeslots(){
        Duration t_duration = Duration.between(t_now, t_deadline);
        int n_timeslot = (int)t_duration.toHours()-1;


        for(int i=0; i<n_timeslot; i++){
            boolean overlapped = false;
            boolean nighttime = false;

            LocalDateTime t_timeslot = t_now.plusHours(i);

            for(Event event: unchangeable_events){
                // t---start---t+m---end
                if (t_timeslot.isBefore(event.t_start) &&
                        t_timeslot.plusHours(max_hour_duration).isAfter(event.t_start))
                {
                    overlapped = true;
                    break;
                }

                // start---t---end
                if (t_timeslot.isAfter(event.t_start) && t_timeslot.isBefore(event.t_end)){
                    overlapped = true;
                    break;
                }
            }

            if(t_timeslot.getHour() >= 7 && t_timeslot.getHour() <= 23){
                nighttime = false;
            }else{
                nighttime = true;
            }

            if (overlapped == false && nighttime == false){
                available_timeslots.add(new Timeslot(t_timeslot));
            }
        }
    }
}
