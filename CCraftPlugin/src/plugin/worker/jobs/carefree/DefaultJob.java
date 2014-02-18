/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.worker.jobs.carefree;

import plugin.worker.worker.Job;

/**
 * When an NPC doesnt have a job he still has a routine in its day, 
 * it can still be attacked by other entities, react to Day Cycle events
 * @author Chingo
 */
public class DefaultJob implements Job{

    @Override
    public void onStateChanged() {
        
    }

    @Override
    public void defaultAcion() {
        
    }

    
}
