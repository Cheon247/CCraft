/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worker.job;

import world.WorldDayCycleEvent;

/**
 *
 * @author Chingo
 */
public interface Job {
  
    public abstract void defaultAction();
    public abstract void onStateChanged();
    public abstract void onTimeChangeEvent(WorldDayCycleEvent wdce);
    
}
