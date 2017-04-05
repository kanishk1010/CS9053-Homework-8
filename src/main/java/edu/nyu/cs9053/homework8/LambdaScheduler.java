package edu.nyu.cs9053.homework8;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kanishk on 3/30/17.
 */
public class LambdaScheduler implements Iterable<LambdaScheduler>{

    private static final List<LambdaScheduler> INPUT_JOBS = new LinkedList<>();
    private static final List<LambdaScheduler> SELECTED_JOBS = new LinkedList<>();

    private final LocalTime startTime;
    private final LocalTime endTime;

    private LambdaScheduler(String startTime, String endTime){
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
    }

    public LocalTime getStartTime(){
        return startTime;
    }

    public LocalTime getEndTime(){
        return endTime;
    }
    //add jobs to the scheduler
    public static void add(String startTime, String endTime) {
        INPUT_JOBS.add(new LambdaScheduler(startTime, endTime));
    }
    //optimally schedule the jobs once all the tasks are added
    public static void optimize(){
        if (INPUT_JOBS.size()==0){
            throw new IllegalStateException("No jobs to schedule");
        }
        INPUT_JOBS.sort(Comparator.comparing(LambdaScheduler::getEndTime));
        SELECTED_JOBS.add(INPUT_JOBS.get(0));
        for (int i=1,j=0; i<INPUT_JOBS.size(); i++){
            if (isCompatible (INPUT_JOBS.get(i), INPUT_JOBS.get(j))) {
                SELECTED_JOBS.add(INPUT_JOBS.get(i));
                i--;
                j++;
            }
        }
        System.out.println("No. of jobs scheduled = "+ SELECTED_JOBS.size());
    }

    private static boolean isCompatible(LambdaScheduler i, LambdaScheduler j){
        return (!(i.getStartTime().isBefore(j.getEndTime())));
    }

    public static void runScheduledJobs(){
        //jobs run and completed on cluster
        SELECTED_JOBS.clear();
    }

    public static List<LambdaScheduler> getPendingJobs(){
        List<LambdaScheduler> pendingJobs = INPUT_JOBS;
        pendingJobs.removeAll(SELECTED_JOBS);
        return pendingJobs;
    }

    public static List<LambdaScheduler> getScheduledJobs(){
        return SELECTED_JOBS;
    }

    public static void printScheduledJobs(){
        for(LambdaScheduler element: SELECTED_JOBS){
            System.out.printf("%s,%s%n",element.getStartTime(), element.getEndTime());
        }
    }

    @Override
    public Iterator<LambdaScheduler> iterator() {
        return SELECTED_JOBS.listIterator();
    }
}
