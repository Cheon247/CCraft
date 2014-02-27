/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worker.job.idle;

import worker.job.Job;
import world.WorldDayCycleEvent;

/**
 *
 * @author Chingo
 */
public class JobIdle implements Job {

    @Override
    public void defaultAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStateChanged() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onTimeChangeEvent(WorldDayCycleEvent wdce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
