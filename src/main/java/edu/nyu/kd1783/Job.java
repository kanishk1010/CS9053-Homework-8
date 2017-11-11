package edu.nyu.kd1783;

import java.time.LocalTime;

/**
 * Created by Kanishk on 4/21/17.
 */
public class Job {

    private final LocalTime startTime;
    private final LocalTime endTime;

    public Job(String startTime, String endTime){
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
    }

    public LocalTime getStartTime(){
        return startTime;
    }

    public LocalTime getEndTime(){
        return endTime;
    }

    public boolean isCompatibleTo(Job j){
        return (!(this.getStartTime().isBefore(j.getEndTime())));
    }
}
