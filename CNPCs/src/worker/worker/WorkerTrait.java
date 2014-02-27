/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worker.worker;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import worker.job.Job;
import worker.job.JobFactory;

/**
 * @author Chingo
 */
public class WorkerTrait extends Trait {

    
    @Persist("peasant_job")
    private String jobName;

    private Job job;

    public WorkerTrait() {
        super("worker");
    }

    @Override
    public void load(DataKey key) {
        this.jobName = key.getString("peasant_job");
        this.job = JobFactory.getJob(JobFactory.JOB.valueOf(jobName));
    }

    // Save settings for this NPC (optional). These values will be persisted to the Citizens saves file
    @Override
    public void save(DataKey key) {
        key.setString("peasant_job", jobName);
    }

    @Override
    public void onSpawn() {
        if (jobName != null) {
            this.job = JobFactory.getJob(JobFactory.JOB.valueOf(jobName));
            this.job.defaultAction();
        } else {
            this.job = JobFactory.getJob(JobFactory.JOB.IDLE);
        }
    }

    @Override
    public void onAttach() {
        if(this.jobName == null)
        this.job = JobFactory.getJob(JobFactory.JOB.IDLE);
    }
    
    


}
