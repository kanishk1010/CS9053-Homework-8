# CS9053-Homework-8

LambdaScheduler:
This is to optimize the cluster of EC2 machines which are running the Lambda jobs in containers by creating a scheduler of Lambda jobs which maximizes the number of jobs a single container can accept.
Each Lambda job consists of the following: a starting time _s_ until a final time _t_.  
Given a list of jobs, the LambdaScheduler accepts a subset of the jobs, rejecting all others, so that the accepted jobs do not overlap in time.       

LambdaWeightedScheduler:
Instead of optimizing for the number of jobs, LambdaWeightedScheduler maximizes for the total value of the job.
