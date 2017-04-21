package edu.nyu.cs9053.homework8;

import java.time.LocalTime;
import java.util.*;

/**
 * Created by Kanishk on 4/3/17.
 */
public class LambdaWeightedScheduler extends LambdaScheduler<WeightedJob>{

    private double dynamicProfit[];
    private int previousCompatibleJob[];

    public LambdaWeightedScheduler(){
    }

    public LambdaWeightedScheduler(List<WeightedJob> weightedJobs){
        super(weightedJobs);
    }


    //schedule the jobs once all the tasks are added to maximize profit
    @Override
    public void optimize() {
        if (getInputJobs().size() == 0) {
            throw new IllegalStateException("No jobs to schedule");
        }
        getInputJobs().sort(Comparator.comparing(Job::getEndTime));
        dynamicProfit = new double[getInputJobs().size()];
        dynamicProfit[0] = getInputJobs().get(0).getWeight();
        previousCompatibleJob = new int[getInputJobs().size()];
        for (int i = 1; i < getInputJobs().size(); i++) {
            dynamicProfit[i] = Math.max(getInputJobs().get(i).getWeight(), dynamicProfit[i - 1]);
            for (int j = i - 1; j >= 0; j--) {
                if (getInputJobs().get(i).isCompatibleTo(getInputJobs().get(j))) {
                    //if(jobs[j].end <= jobs[i].start){
                    previousCompatibleJob[i] = j;
                    dynamicProfit[i] = Math.max(dynamicProfit[i], getInputJobs().get(i).getWeight() + dynamicProfit[j]);
                    break;
                }
            }
        }
        printMaxProfit();
        scheduleJobs(getInputJobs().size()-1);
        Collections.reverse(getSelectedJobs());
    }

    private void printMaxProfit(){
        double maxProfit = dynamicProfit[0];
        for (int i=1; i <dynamicProfit.length; i++){
            if (dynamicProfit[i]> maxProfit){
                maxProfit = dynamicProfit[i];
            }
        }
        System.out.println("Maximized Profit ="+ maxProfit);
    }

    private void scheduleJobs(int j){
        if(j==0) {
            if (getSelectedJobs().get(getSelectedJobs().size()-1).isCompatibleTo(getInputJobs().get(0))) {
                getSelectedJobs().add(getInputJobs().get(j));
            }
                return;
        }
        if (getInputJobs().get(j).getWeight() + dynamicProfit[previousCompatibleJob[j]] > dynamicProfit[j-1]) {
            getSelectedJobs().add(getInputJobs().get(j));
            scheduleJobs(previousCompatibleJob[j]);
        }
        else
            scheduleJobs(j-1);
    }


    @Override
    public void printScheduledJobs(){
        for(WeightedJob element: getSelectedJobs()){
            System.out.printf("%s,%s,%f%n",element.getStartTime(), element.getEndTime(), element.getWeight());
        }
    }

}
