package com.ece496;


import com.ece496.database.Event;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FrontEndEvent{

    private String event_title;
    private String event_description;
    private int event_duration;
    private int frequency;

    private Date start_date;
    private Date end_date;
    private int id;

    String due_date_string;
    private int due_year;
    private int due_month;
    private int due_day;
    private int due_hour;
    private int due_minute;
    String start_time_string;
    private int start_year;
    private int start_month;
    private int start_day;
    private int start_hour;
    private int start_minute;

    String end_time_string;
    private int end_year;
    private int end_month;
    private int end_day;
    private int end_hour;
    private int end_minute;

    String freq_start_time_string;
    private int freq_start_year;
    private int freq_start_month;
    private int freq_start_day;
    private int freq_start_hour;
    private int freq_start_minute;

    String freq_end_time_string;
    private int freq_end_year;
    private int freq_end_month;
    private int freq_end_day;
    private int freq_end_hour;
    private int freq_end_minute;

    public List<Event> toBackendEvent(){
        // WTF Slesha, this is very nasty
        List<Event> events = new ArrayList<Event>();

        String name = event_title;
        String description = event_description;

        LocalDateTime t_start, t_end, t_deadline, t_freq_start, t_freq_end;
        Duration t_freq_duration;

        t_start = t_end = t_deadline = t_freq_start = t_freq_end = null;
        t_freq_duration = null;

        if (start_year != 0) {
            t_start = LocalDateTime.of(start_year, start_month, start_day, start_hour, start_minute);
        }
        if (end_year != 0) {
            t_end = LocalDateTime.of(end_year, end_month, end_day, end_hour, end_minute);
        }
        if (due_year != 0){
            t_deadline = LocalDateTime.of(due_year, due_month, due_day, due_hour, due_minute);
        }
        if (freq_start_year != 0){
            t_freq_start = LocalDateTime.of(freq_start_year, freq_start_month, freq_start_day, freq_start_hour, freq_start_minute);
        }
        if (freq_end_year != 0){
            t_freq_end = LocalDateTime.of(freq_end_year, freq_end_month, freq_end_day, freq_end_hour, freq_end_minute);
        }

        if (t_freq_start!=null && t_freq_end!=null) {
            t_freq_duration = Duration.between(t_freq_start, t_freq_end);
        }

        if(t_freq_duration != null){
            // I assume freq 0 is daily & 1 is weekly
            int step_size=1;
            if (frequency == 0){
                step_size = 1;
            }else if(frequency == 1){
                step_size = 7;
            }

            for (int i = 0; i < t_freq_duration.toDays()/step_size; i++) {
                events.add(
                    new Event(
                            name,
                            description,
                            t_start.plusDays(i * step_size),
                            t_end.plusDays(i * step_size),
                            t_deadline,
                            true
                    )
                );
            }
        }else{
            events.add(
                new Event(
                    name,
                    description,
                    t_start,
                    t_end,
                    t_deadline,
                    true
                )
            );
        }

        return events;
    }

    public void schedule(){
        List<Event> be_events = this.toBackendEvent();
        for(Event event: be_events){
            event.schedule();
        }
    }
    public void dynamic_schedule(){
        List<Event> be_events = this.toBackendEvent();
        // dynamic scheduling only work while frequency = -1
        if(frequency == -1 && be_events.size()==1){
            for(Event event: be_events){
                event.dynamic_schedule();
            }
        }
    }


    public FrontEndEvent(String title, String description, String start_time, String end_time, int duration, String freq_start_time,
                  String freq_end_time, int freq, String due_date) {

        /*
         * NOTE: start_time, end_time, freq_start_time, and freq_end_time MUST be in the
         * format "Year(int) Month(int) Day(int) Hour(int) Minute(int)" where everything
         * is only numbers. Example: "2019 12 02 17 35" -> December 2, 2019, 5:35pm
         * IMPORTANT: For empty date values, send the String "0 0 0 0 0" instead
         */

        set_event_title(title);
        set_event_description(description);
        set_event_duration(duration);
        set_frequency(freq);

        set_due_date_string(due_date);
        String due_date_array[] = due_date.split(" ");
        set_due_year(Integer.parseInt(due_date_array[0]));
        set_due_month(Integer.parseInt(due_date_array[1]));
        set_due_day(Integer.parseInt(due_date_array[2]));
        set_due_hour(Integer.parseInt(due_date_array[3]));
        set_due_minute(Integer.parseInt(due_date_array[4]));

        set_start_time_string(start_time);
        String start_time_array[] = start_time.split(" ");
        set_start_year(Integer.parseInt(start_time_array[0]));
        set_start_month(Integer.parseInt(start_time_array[1]));
        set_start_day(Integer.parseInt(start_time_array[2]));
        set_start_hour(Integer.parseInt(start_time_array[3]));
        set_start_minute(Integer.parseInt(start_time_array[4]));

        set_end_time_string(end_time);
        String end_time_array[] = end_time.split(" ");
        set_end_year(Integer.parseInt(end_time_array[0]));
        set_end_month(Integer.parseInt(end_time_array[1]));
        set_end_day(Integer.parseInt(end_time_array[2]));
        set_end_hour(Integer.parseInt(end_time_array[3]));
        set_end_minute(Integer.parseInt(end_time_array[4]));

        set_freq_start_time_string(freq_start_time);
        String freq_start_time_array[] = freq_start_time.split(" ");
        set_freq_start_year(Integer.parseInt(freq_start_time_array[0]));
        set_freq_start_month(Integer.parseInt(freq_start_time_array[1]));
        set_freq_start_day(Integer.parseInt(freq_start_time_array[2]));
        set_freq_start_hour(Integer.parseInt(freq_start_time_array[3]));
        set_freq_start_minute(Integer.parseInt(freq_start_time_array[4]));

        set_freq_end_time_string(freq_end_time);
        String freq_end_time_array[] = freq_end_time.split(" ");
        set_freq_end_year(Integer.parseInt(freq_end_time_array[0]));
        set_freq_end_month(Integer.parseInt(freq_end_time_array[1]));
        set_freq_end_day(Integer.parseInt(freq_end_time_array[2]));
        set_freq_end_hour(Integer.parseInt(freq_end_time_array[3]));
        set_freq_end_minute(Integer.parseInt(freq_end_time_array[4]));

        set_date();

    }

    public String int_to_month(int n) {

        String month = "";

        switch (n) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
            default:
                month = "";
                break;
        }

        return month;
    }

    public String format_time(int h, int m) {

        /* Takes values from 0:00 as 12:00 am or 24:00 as 12:00 am */

        int hour = 0;

        if (h >= 24) {
            hour = h - 24;
        } else if (h > 12) {
            hour = h - 12;
        } else {
            hour = h;
        }

        String time = "";

        if (h == 0 || h == 24) {

            if (m < 10) {
                time = "12" + ":0" + m + " a.m.";
            } else {
                time = "12" + ":" + m + " a.m.";
            }

        } else if (h > 11) {

            if (hour < 10) {
                if (m < 10) {
                    time = "0" + hour + ":0" + m + " p.m.";
                } else {
                    time = "0" + hour + ":" + m + " p.m.";
                }
            } else {
                if (m < 10) {
                    time = "" + hour + ":0" + m + " p.m.";
                } else {
                    time = "" + hour + ":" + m + " p.m.";
                }
            }

        } else {

            if (hour < 10) {
                if (m < 10) {
                    time = "0" + hour + ":0" + m + " a.m.";
                } else {
                    time = "0" + hour + ":" + m + " a.m.";
                }
            } else {
                if (m < 10) {
                    time = "" + hour + ":0" + m + " a.m.";
                } else {
                    time = "" + hour + ":" + m + " a.m.";
                }
            }
        }

        return time;
    }

    public void print_details() {
        System.out.printf("Event Title: %s\n", event_title);

        System.out.printf("  Event Duration: %d mins\n", event_duration);

        if ((event_description == "") == false) {
            System.out.printf("  Event Description: %s\n", event_description);
        }

        if (due_year > 0) {
            System.out.printf("  Due Date: %s %d, %d %s\n", int_to_month(due_month), due_day, due_year,
                    format_time(due_hour, due_minute));
        }

        if (start_year > 0) {
            System.out.printf("  Start Date: %s %d, %d %s\n", int_to_month(start_month), start_day, start_year,
                    format_time(start_hour, start_minute));
        }

        if (end_year > 0) {
            System.out.printf("  End Date: %s %d, %d %s\n", int_to_month(end_month), end_day, end_year,
                    format_time(end_hour, end_minute));
        }

        if (freq_start_year > 0) {
            System.out.printf("  Frequency Start Date: %s %d, %d %s\n", int_to_month(freq_start_month), freq_start_day,
                    freq_start_year, format_time(freq_start_hour, freq_start_minute));
        }

        if (freq_end_year > 0) {
            System.out.printf("  Frecuency End Date: %s %d, %d %s\n", int_to_month(freq_end_month), freq_end_day,
                    freq_end_year, format_time(freq_end_hour, freq_end_minute));
        }

    }

    public void set_event_title(String s) {
        event_title = s;
    }

    public String get_event_title() {
        return event_title;
    }

    public void set_event_description(String s) {
        event_description = s;
    }

    public String get_event_description() {
        return event_description;
    }

    public void set_event_duration(int n) {
        event_duration = n;
    }

    public int get_event_duration() {
        return event_duration;
    }

    public void set_frequency(int n) {
        frequency = n;
    }

    public int get_frequency() {
        return frequency;
    }

    public void set_due_date_string(String s) {
        due_date_string = s;
    }

    public String get_due_date_string() {
        return due_date_string;
    }

    public void set_due_year(int n) {
        due_year = n;
    }

    public int get_due_year() {
        return due_year;
    }

    public void set_due_month(int n) {
        due_month = n;
    }

    public int get_due_month() {
        return due_month;
    }

    public void set_due_day(int n) {
        due_day = n;
    }

    public int get_due_day() {
        return due_day;
    }

    public void set_due_hour(int n) {
        due_hour = n;
    }

    public int get_due_hour() {
        return due_hour;
    }

    public void set_due_minute(int n) {
        due_minute = n;
    }

    public int get_due_minute() {
        return due_minute;
    }

    public void set_start_year(int n) {
        start_year = n;
    }

    public int get_start_year() {
        return start_year;
    }

    public void set_start_month(int n) {
        start_month = n;
    }

    public int get_start_month() {
        return start_month;
    }

    public void set_start_day(int n) {
        start_day = n;
    }

    public int get_start_day() {
        return start_day;
    }

    public int get_start_hour() {
        return start_hour;
    }

    public int get_start_minute() {
        return start_minute;
    }

    public int get_end_year() {
        return end_year;
    }

    public int get_end_month() {
        return end_month;
    }

    public int get_end_day() {
        return end_day;
    }

    public int get_end_hour() {
        return end_hour;
    }

    public int get_end_minute() {
        return end_minute;
    }

    public void set_start_hour(int n) {
        start_hour = n;
    }

    public void set_start_minute(int n) {
        start_minute = n;
    }

    public void set_end_year(int n) {
        end_year = n;
    }

    public void set_end_month(int n) {
        end_month = n;
    }

    public void set_end_day(int n) {
        end_day = n;
    }

    public void set_end_hour(int n) {
        end_hour = n;
    }

    public void set_end_minute(int n) {
        end_minute = n;
    }

    public int get_freq_start_year() {
        return freq_start_year;
    }

    public int get_freq_start_month() {
        return freq_start_month;
    }

    public int get_freq_start_day() {
        return freq_start_day;
    }

    public int get_freq_start_hour() {
        return freq_start_hour;
    }

    public int get_freq_start_minute() {
        return freq_start_minute;
    }

    public int get_freq_end_year() {
        return freq_end_year;
    }

    public int get_freq_end_month() {
        return freq_end_month;
    }

    public int get_freq_end_day() {
        return freq_end_day;
    }

    public int get_freq_end_hour() {
        return freq_end_hour;
    }

    public int get_freq_end_minute() {
        return freq_end_minute;
    }

    public void set_freq_start_year(int n) {
        freq_start_year = n;
    }

    public void set_freq_start_month(int n) {
        freq_start_month = n;
    }

    public void set_freq_start_day(int n) {
        freq_start_day = n;
    }

    public void set_freq_start_hour(int n) {
        freq_start_hour = n;
    }

    public void set_freq_start_minute(int n) {
        freq_start_minute = n;
    }

    public void set_freq_end_year(int n) {
        freq_end_year = n;
    }

    public void set_freq_end_month(int n) {
        freq_end_month = n;
    }

    public void set_freq_end_day(int n) {
        freq_end_day = n;
    }

    public void set_freq_end_hour(int n) {
        freq_end_hour = n;
    }

    public void set_freq_end_minute(int n) {
        freq_end_minute = n;
    }

    public String get_start_time_string() {
        return start_time_string;
    }

    public String get_end_time_string() {
        return end_time_string;
    }

    public String get_freq_start_time_string() {
        return freq_start_time_string;
    }

    public String get_freq_end_time_string() {
        return freq_end_time_string;
    }

    public void set_start_time_string(String s) {
        start_time_string = s;
    }

    public void set_end_time_string(String s) {
        end_time_string = s;
    }

    public void set_freq_start_time_string(String s) {
        freq_start_time_string = s;
    }

    public void set_freq_end_time_string(String s) {
        freq_end_time_string = s;
    }

    public void set_date() {

        Calendar c = Calendar.getInstance();
        c.set(start_year, start_month-1, start_day, start_hour, start_minute);
        start_date = c.getTime();

        Calendar d = Calendar.getInstance();
        d.set(end_year, end_month-1, end_day, end_hour, end_minute);
        end_date = d.getTime();
    }


    public Date getDate() {
        return start_date;
    }

    public Date getEnd(){
        return end_date;
    }

    public String getMessage() {
        return String.format("%s - %s: %s", format_time(start_hour, start_minute), format_time(end_hour, end_minute), get_event_title());
    }

    public void setId(int n) {
        id = n;
    }

    public int getId() {
        return id;
    }


}