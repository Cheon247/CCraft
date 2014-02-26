/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worker.job;

import worker.job.scout.JobScout;

/**
 *
 * @author Chingo
 */
public class JobFactory {
  
  public enum JOB {
    SCOUT
  }
    
  public Job getJob(JOB job) {
    switch (job) {
      case SCOUT: return new JobScout();
      default: throw new UnsupportedOperationException();
    }
  }
  
}
