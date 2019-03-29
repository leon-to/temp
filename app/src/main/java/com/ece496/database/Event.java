package com.ece496.database;

import com.ece496.backend.DynamicScheduler;
import com.ece496.backend.Scheduler;
import com.orm.SchemaGenerator;
import com.orm.SugarRecord;

import com.ece496.FrontEndEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event  extends SugarRecord{
    String name;
    String description;

    public LocalDateTime t_start;
    public LocalDateTime t_end;
    public LocalDateTime t_deadline;
    public Duration      t_duration;

    public boolean changeable;

    public Event(String name, String description,
                 LocalDateTime t_start, LocalDateTime t_end, LocalDateTime t_deadline,
                 boolean changeable){
        this.name = name;
        this.description = description;

        this.t_start = t_start;
        this.t_end = t_end;
        this.t_deadline = t_deadline;
        this.changeable = changeable;

        if(t_start!=null && t_end!=null) {
            this.t_duration = Duration.between(t_start, t_end);
        }else{
            this.t_duration = null;
        }
    }

    public Event(String name){
        this(name, null, null, null, null, true);
    }
    public Event(String name, Duration t_duration){
        this(name, null, null, null, null, true);
        this.t_duration = t_duration;
    }
    public Event(String name, Duration t_duration, LocalDateTime t_deadline){
        this(name, null, null, null, t_deadline, true);
        this.t_duration = t_duration;
    }
    public Event(String name, String description, Duration t_duration){
        this(name, description, null, null, null, true);
        this.t_duration = t_duration;
    }
    public Event(String name, String description, Duration t_duration, LocalDateTime t_deadline){
        this(name, description, t_duration);
        this.t_deadline = t_deadline;
    }

    public Event(String name, LocalDateTime t_start, LocalDateTime t_end){
        this(name, null, t_start, t_end, null, true);
    }
    public Event(String name, LocalDateTime t_start, LocalDateTime t_end, boolean changeable){
        this(name, null, t_start, t_end, null, changeable);
    }

    public Event(String name, LocalDateTime t_deadline){
        this(name, null, null, null, t_deadline, true);
    }
    public Event(){
        this("no name", LocalDateTime.now().plusDays(7));
    }


    public String get_name(){
        return get_name();
    }
    public  LocalDateTime get_deadline(){
        return t_deadline;
    }

    public void schedule(){
        new Scheduler().schedule(this);
    }

    public void dynamic_schedule(){
        new DynamicScheduler().schedule(name, description, t_duration, t_deadline);
    }

    public FrontEndEvent toFrontEndEvent(){
        String str_deadline;
        if (t_deadline == null){
            str_deadline = "0 0 0 0 0";
        }else{
            str_deadline = get_time_string(t_deadline);
        }

        return new FrontEndEvent(
                name,
                description,
                get_time_string(t_start),
                get_time_string(t_end),
                (int)t_duration.toMinutes(),
                "0 0 0 0 0",
                "0 0 0 0 0",
                -1,
                str_deadline
        );
    }

    public String toString(){
        return "Event: " + name + ", Start: " + t_start + ", End: " + t_end + ", Deadline: " + t_deadline;
    }

    private String get_time_string(LocalDateTime time){
        return time.getYear() + " " + time.getMonthValue() + " " + time.getDayOfMonth() + " " +
                time.getHour() + " " + time.getMinute();
    }
}