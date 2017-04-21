package edu.nyu.cs9053.homework8;

/**
 * Created by Kanishk on 4/21/17.
 */
public class WeightedJob extends Job {

    private final double weight;

    public WeightedJob(String startTime, String endTime, double weight){
        super (startTime, endTime);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

}
