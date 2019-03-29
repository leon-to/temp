package com.ece496.genealgo.handler;

import java.time.Duration;
import java.time.LocalDateTime;

import com.ece496.genealgo.object.*;
import com.ece496.database.Event;

public class FitnessHandler {
    EventHandler h_event;
    TimeslotHandler h_timeslot;
    PopulationHandler h_population;
    
    public LocalDateTime now = LocalDateTime.now();

    public FitnessHandler(
        EventHandler h_event, 
        TimeslotHandler h_timeslot, 
        PopulationHandler h_population)
    {
        this.h_event = h_event;
        this.h_timeslot = h_timeslot;
        this.h_population = h_population;
    }
    public double compute_deadline_constraint(Individual individual){
        for(int idx_event=0; idx_event < individual.chrom.length; idx_event++) {
            int idx_timeslot = individual.chrom[idx_event];

            Event event = h_event.events.get(idx_event);
            Timeslot timeslot = h_timeslot.timeslots.get(idx_timeslot);

            if(event.t_deadline == null){
                continue;
            }

            if(event.t_deadline.isBefore(timeslot.datetime)){
                return 0;
            }
        }
        return 1;
    }

    public double compute_timeleft_fitness(Individual individual){
        double total_score=0, final_score;

        for(int idx_event=0; idx_event < individual.chrom.length; idx_event++){
            int idx_timeslot = individual.chrom[idx_event];

            Event event = h_event.events.get(idx_event);
            Timeslot timeslot = h_timeslot.timeslots.get(idx_timeslot);
            // if event has no deadline, always get score
            if(event.t_deadline == null) {
                total_score += 1;
                continue;
            }
            // if deadline is before timeslot, get -1 score
            if(event.t_deadline.isBefore(timeslot.datetime)){
                total_score -= 0.1;
                continue;
            }

            Duration t_total = Duration.between(now, event.get_deadline());
            Duration t_left = Duration.between(timeslot.datetime, event.get_deadline());

            total_score += t_left.toHours() / (float) t_total.toHours();
//                System.out.println("left: " + t_left.toHours() + " total: " + t_total.toHours());
        }

        final_score = total_score / individual.chrom.length;
//        System.out.println("total: " + total_score + " final: " + final_score);
        return final_score;
    }
    public double compute_relaxation_fitness(Individual individual){
        double total_score = 0, score=0;
        int[] chrom = individual.chrom;

        for(int i=0; i < chrom.length; i++){
            score = 1;
            for(int j=i+1; j < chrom.length; j++){
                Timeslot t_event1 = h_timeslot.timeslots.get(chrom[i]);
                Timeslot t_event2 = h_timeslot.timeslots.get(chrom[j]);

                Duration t_inbetween = Duration.between(t_event1.datetime, t_event2.datetime);
                
                if (t_inbetween.toHours()<=1){
                    score = 0;
                    break;
                }
            }
            total_score += score;
        }

        return total_score / chrom.length;
    }

    public double compute_overlap_fitness(Individual individual){
        double total_score = 0, score=0;
        int[] chrom = individual.chrom;

        for(int i=0; i < chrom.length; i++){
            score = 1;
            for(int j=i+1; j < chrom.length; j++){
                Timeslot t_event1 = h_timeslot.timeslots.get(chrom[i]);
                Timeslot t_event2 = h_timeslot.timeslots.get(chrom[j]);
                Event event1 = h_event.events.get(i);
                Event event2 = h_event.events.get(j);


                // event 1 overlaps with event 2 or vice versus, score = 0
                if(t_event1.datetime.isBefore(t_event2.datetime)){
                    Duration t_duration = Duration.between(t_event1.datetime, t_event2.datetime);

                    if(t_duration.toMinutes() < event1.t_duration.toMinutes()){
                        score = 0;
                        break;
                    }
                }else{
                    Duration t_duration = Duration.between(t_event2.datetime, t_event1.datetime);

                    if(t_duration.toMinutes() < event2.t_duration.toMinutes()){
                        score = 0;
                        break;
                    }
                }
            }
            total_score += score;
        }

        return total_score / chrom.length;
    }

    public void compute_fitness(Individual individual){
        // constraint
        double deadline_constraint = compute_deadline_constraint(individual);
        if(deadline_constraint == 0){
            individual.fitness = 0;
            return;
        }

        // fitness
        double timeleft_fitness = compute_timeleft_fitness(individual);
        double relaxation_fitness = compute_relaxation_fitness(individual);
        double overlap_fitness = compute_overlap_fitness(individual);

        int n_score = 3;

        individual.fitness = (timeleft_fitness + relaxation_fitness + overlap_fitness) / n_score;
    }
 
    public void compute_population_fitness(){
        for(int i=0; i<h_population.population.size(); i++)
            compute_fitness(h_population.population.get(i));
    }
    public void compute_children_fitness(){
        for(int i=0; i<h_population.children.length; i++)
            compute_fitness(h_population.children[i]);
    }

}