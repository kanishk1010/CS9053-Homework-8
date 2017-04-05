package edu.nyu.cs9053.homework8;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kanishk on 4/3/17.
 */
public class LambdaWeightedScheduler implements Iterable<LambdaWeightedScheduler>{

    private static final List<LambdaWeightedScheduler> INPUT_JOBS = new LinkedList<>();
    private static final List<LambdaWeightedScheduler> SELECTED_JOBS = new LinkedList<>();
    private static double dynamicProfit[];
    private static int previousCompatibleJob[];

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final double weight;

    private LambdaWeightedScheduler(String startTime, String endTime, double weight){
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
        this.weight = weight;
    }

    public LocalTime getStartTime(){
        return startTime;
    }

    public LocalTime getEndTime(){
        return endTime;
    }

    public double getWeight() {
        return weight;
    }

    //add jobs to the scheduler
    public static void add(String startTime, String endTime, double weight) {
        INPUT_JOBS.add(new LambdaWeightedScheduler(startTime, endTime, weight));
    }

    //schedule the jobs once all the tasks are added to maximize profit
    public static void optimize() {
        if (INPUT_JOBS.size() == 0) {
            throw new IllegalStateException("No jobs to schedule");
        }
        INPUT_JOBS.sort(Comparator.comparing(LambdaWeightedScheduler::getEndTime));
        dynamicProfit = new double[INPUT_JOBS.size()];
        dynamicProfit[0] = INPUT_JOBS.get(0).getWeight();
        previousCompatibleJob = new int[INPUT_JOBS.size()];
        for (int i = 1; i < INPUT_JOBS.size(); i++) {
            dynamicProfit[i] = Math.max(INPUT_JOBS.get(i).getWeight(), dynamicProfit[i - 1]);
            for (int j = i - 1; j >= 0; j--) {
                if (isCompatible (INPUT_JOBS.get(i), INPUT_JOBS.get(j))) {
                    //if(jobs[j].end <= jobs[i].start){
                    previousCompatibleJob[i] = j;
                    dynamicProfit[i] = Math.max(dynamicProfit[i], INPUT_JOBS.get(i).getWeight() + dynamicProfit[j]);
                    break;
                }
            }
        }
        printMaxProfit();
        scheduleJobs(INPUT_JOBS.size()-1);
    }

    private static void printMaxProfit(){
        double maxProfit = dynamicProfit[0];
        for (int i=1; i <dynamicProfit.length; i++){
            if (dynamicProfit[i]> maxProfit){
                maxProfit = dynamicProfit[i];
            }
        }
        System.out.println("Maximized Profit ="+ maxProfit);
    }
    private static boolean isCompatible(LambdaWeightedScheduler i, LambdaWeightedScheduler j){
        return (!(i.getStartTime().isBefore(j.getEndTime())));
    }
    private static void scheduleJobs(int j){
        if(j==0) {
            if (isCompatible(SELECTED_JOBS.get(SELECTED_JOBS.size()-1), INPUT_JOBS.get(0))) {
                SELECTED_JOBS.add(INPUT_JOBS.get(j));
            }
                return;
        }
        if (INPUT_JOBS.get(j).getWeight() + dynamicProfit[previousCompatibleJob[j]] > dynamicProfit[j-1]) {
            SELECTED_JOBS.add(INPUT_JOBS.get(j));
            scheduleJobs(previousCompatibleJob[j]);
        }
        else
            scheduleJobs(j-1);
    }


    public static void runScheduledJobs(){
        //jobs run and completed on cluster
        SELECTED_JOBS.clear();
    }

    public static List<LambdaWeightedScheduler> getPendingJobs(){
        List<LambdaWeightedScheduler> pendingJobs = INPUT_JOBS;
        pendingJobs.removeAll(SELECTED_JOBS);
        return pendingJobs;
    }

    public static List<LambdaWeightedScheduler> getScheduledJobs(){
        return SELECTED_JOBS;
    }

    public static void printScheduledJobs(){
        for(LambdaWeightedScheduler element: SELECTED_JOBS){
            System.out.printf("%s,%s,%f%n",element.getStartTime(), element.getEndTime(), element.getWeight());
        }
    }

    @Override
    public Iterator<LambdaWeightedScheduler> iterator() {
        return SELECTED_JOBS.listIterator();
    }
}
