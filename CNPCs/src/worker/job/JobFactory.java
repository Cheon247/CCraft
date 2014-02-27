/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worker.job;

import worker.job.elder.JobVillageElder;
import worker.job.idle.JobIdle;
import worker.job.scout.JobScout;

/**
 *
 * @author Chingo
 */
public class JobFactory {
  
  public enum JOB {
    SCOUT,
    IDLE,
    VILLAGE_ELDER
  }
  
  
    
  public static Job getJob(JOB job) {
    switch (job) {
      case IDLE : return new JobIdle();
      case SCOUT: return new JobScout();
      case VILLAGE_ELDER: return new JobVillageElder();
      default: throw new UnsupportedOperationException(job.name() + ": not supported");
    }
  }
  
}
