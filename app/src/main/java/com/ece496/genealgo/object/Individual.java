package com.ece496.genealgo.object;

import java.util.Arrays;

// Individual = A human
public class Individual implements Comparable<Individual>{ 
    public int[] chrom;
    public double fitness;

    public Individual(int[] chrom){
        this.chrom = chrom;
        this.fitness = 0;
    }
    @Override
    public String toString(){
        return "Chromosome: " + Arrays.toString(chrom) + " Fitness score:" + fitness;
    }
    @Override
    public int compareTo(Individual individual){
        double delta = this.fitness - individual.fitness;
        if (delta < 0){
            return 1;
        }else if (delta > 0){
            return -1;
        }else{
            return 0;
        }
    }
}