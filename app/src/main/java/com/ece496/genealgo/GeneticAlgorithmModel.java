package com.ece496.genealgo;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.*;

import com.ece496.genealgo.object.*;
import com.ece496.genealgo.handler.*;

public class GeneticAlgorithmModel{
    private static final Logger logger = Logger.getGlobal();
    public static final Random random = new Random();
    
    public PopulationHandler h_population;
    public FitnessHandler h_fitness;

    public GeneticAlgorithmModel(
        PopulationHandler h_population, 
        FitnessHandler h_fitness)
    {
        this.h_population = h_population;
        this.h_fitness = h_fitness;
    }

    public void train(
        int n_pop, // population size 
        double p_mutation, // probability of mutation
        int epochs
    ){
        // initialize population population
        h_population.random_initialize(n_pop);
        h_fitness.compute_population_fitness();

        // while(true)
        for(int i=0; i<epochs; i++){
            h_population.selection();
            h_population.crossover();
            h_population.mutation(p_mutation);
            h_fitness.compute_children_fitness();
            h_population.replace();

            System.out.println("Epoch: " + i + "\nPair of Children" + Arrays.toString(h_population.children));
        }
    }
}