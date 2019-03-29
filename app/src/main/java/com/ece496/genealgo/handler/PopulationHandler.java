package com.ece496.genealgo.handler;

import java.util.Random;
import java.util.Vector;

import com.ece496.genealgo.object.Individual;

public class PopulationHandler{
    private static final Random rand = new Random();

    EventHandler h_event;
    TimeslotHandler h_timeslot;

    public Vector<Individual> population;
    int n_pop;
    int n_curr_pop;
    int n_gene;
    int n_type;

    public Individual[] parents = new Individual[2];
    public Individual[] children = new Individual[2];

    public PopulationHandler(EventHandler h_event, TimeslotHandler h_timeslot){
        this.h_event = h_event;
        this.h_timeslot = h_timeslot;

        population = new Vector<Individual>();
    }
    public void set_characteristics(int n_pop, int n_gene, int n_type){// population size, number of genes in chromosome, gene type
        this.n_pop = n_pop;
        this.n_gene = n_gene;
        this.n_type = n_type;
    }
    
    public void random_initialize(int n){
        int[] chrom;

        while(n-- > 0){
            // create random chromosome
            chrom = new int[n_gene];
            for(int i=0; i<n_gene; i++){
                chrom[i] = rand.nextInt(n_type);
            }
            // add schedule to list
            if (n_curr_pop < n_pop){
                add_schedule(new Individual(chrom));
                n_curr_pop++;
            }
        }
    }
    public void selection(){
        int i, j;
        // select pair of parents through tournament
        i = tournament();
        do{
            j = tournament();
        }while(i==j);
        
        parents[0] = population.get(i);
        parents[1] = population.get(j);
    }
    public void crossover(){
        for(int i=0; i<children.length; i++)
            children[i] = crossover_onepoint();
    }
    public void mutation(double p_mutation){
        for(int i=0; i<children.length; i++)
            mutation_onepoint(children[i], p_mutation);
    }
    public void replace(){
        for(int i=0; i<children.length; i++){
            for(int j=0; j<n_pop; j++){
                if(children[i].fitness > population.get(j).fitness)
                    population.set(j, children[i]);
            }
        }
    }

    public Individual get_bestfit(){
        Individual bestfit = population.firstElement();

        for(int i=1; i < population.size(); i++){
            if (population.get(i).fitness > bestfit.fitness)
                bestfit = population.get(i);
        }
        return bestfit;
    }

    private void add_schedule(Individual schedule){
        population.addElement(schedule);
    }
    private Individual crossover_onepoint(){
        int crossover_point = rand.nextInt(n_gene);
        
        Individual child = new Individual(new int[n_gene]);
        for(int i=0; i<n_gene; i++){
            child.chrom[i] = (i<crossover_point) ? parents[0].chrom[i] : parents[1].chrom[i];
        }

        return child;
    }
    private void mutation_onepoint(Individual child, double p_mutation){
        if (rand.nextDouble() > p_mutation)
            return;

        int mutation_gene = rand.nextInt(n_gene);
        child.chrom[mutation_gene] = rand.nextInt(n_gene);

    }
    private int tournament(){
        int i, j; 
        
        i = rand.nextInt(n_curr_pop);

        do{
            j = rand.nextInt(n_curr_pop);
        }while(i==j);
        
        return population.get(i).fitness > population.get(j).fitness ? i:j; 
    }
}