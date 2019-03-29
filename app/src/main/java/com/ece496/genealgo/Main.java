package com.ece496.genealgo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.logging.*;

import com.ece496.genealgo.handler.*;
import com.ece496.genealgo.object.*;

public class Main{
    public static void main(String[] args) throws Exception {
        EventHandler h_event = new EventHandler();
        TimeslotHandler h_timeslot = new TimeslotHandler();
        PopulationHandler h_population = new PopulationHandler(h_event, h_timeslot);
        FitnessHandler h_fitness = new FitnessHandler(h_event, h_timeslot, h_population);
        GeneticAlgorithmModel model = new GeneticAlgorithmModel(h_population, h_fitness);

        h_event
            .add_event("Math", Duration.ofHours(2), LocalDateTime.of(2019, 3, 19, 9, 0))
            .add_event("English", Duration.ofHours(3), LocalDateTime.of(2019, 3, 21, 9, 0))
            .add_event("Programming", Duration.ofHours(1), LocalDateTime.of(2019, 3, 19, 9, 0));

        int month = 3;
        h_timeslot
            .add_timeslot(2019, month, 18, 7)
            .add_timeslot(2019, month, 18, 8)
            .add_timeslot(2019, month, 18, 9)
            .add_timeslot(2019, month, 19, 10)
            .add_timeslot(2019, month, 20, 7)
            .add_timeslot(2019, month, 20, 12);

        h_population.set_characteristics(
            10, // max population size
            h_event.events.size(), // chromosome length
            h_timeslot.timeslots.size() // number of gene type
        );
        model.train(
            10, // number of initialized population
            0.3, // probability of mutation
            30 //epochs
        );
        System.out.println("Best fit: " + h_population.get_bestfit());
    }
}