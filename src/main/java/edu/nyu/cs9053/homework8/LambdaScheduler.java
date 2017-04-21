package edu.nyu.cs9053.homework8;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kanishk on 3/30/17.
 */
public class LambdaScheduler<T extends Job> implements Iterable<T>{

    private final List<T> inputJobs = new LinkedList<>();
    private final List<T> selectedJobs = new LinkedList<>();

    public LambdaScheduler(){}

    public LambdaScheduler(List<T> jobs){
        if (jobs != null && !jobs.isEmpty()){
            inputJobs.addAll(jobs);
        }
    }
    //add jobs to the scheduler
    public void add(T job) {
        inputJobs.add(job);
    }

    public List<T> getInputJobs(){
        return inputJobs;
    }
    //optimally schedule the jobs once all the tasks are added
    public void optimize(){
        if (inputJobs.size()==0){
            throw new IllegalStateException("No jobs to schedule");
        }
        inputJobs.sort(Comparator.comparing(Job::getEndTime));
        selectedJobs.add(inputJobs.get(0));
        for (int i=1,j=0; i<inputJobs.size(); i++){
            if (inputJobs.get(i).isCompatibleTo(selectedJobs.get(j))) {
                selectedJobs.add(inputJobs.get(i));
                j++;
            }
        }
        System.out.println("No. of jobs scheduled = "+ selectedJobs.size());
    }

    
    public void runScheduledJobs(){
        //jobs run and completed on cluster
        selectedJobs.clear();
    }

    public List<T> getPendingJobs(){
        List<T> pendingJobs = inputJobs;
        pendingJobs.removeAll(selectedJobs);
        return pendingJobs;
    }

    public List<T> getSelectedJobs(){
        return selectedJobs;
    }

    public void printScheduledJobs(){
        for(T element: selectedJobs){
            System.out.printf("%s,%s%n",element.getStartTime(), element.getEndTime());
        }
    }

    @Override
    public Iterator<T> iterator() {
        return selectedJobs.listIterator();
    }
}
