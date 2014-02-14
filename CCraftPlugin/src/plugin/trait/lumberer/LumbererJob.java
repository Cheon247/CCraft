/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.lumberer;

import java.util.Arrays;
import net.citizensnpcs.api.npc.NPC;
import plugin.trait.worker.WorkerJob;
import plugin.trait.worker.WorkerState;
import plugin.trait.worker.WorkerTrait;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public class LumbererJob extends WorkerJob {
    
    
    
    public LumbererJob(NPC npc) {
        super("lumberer", npc);
        if(!npc.hasTrait(WorkerTrait.class)) throw new IllegalArgumentException("NPC have the worker trait");
        
    }

    @Override
    protected void defaultSunRiseAction() {
        Debug.info(npc.getName() + ": default sunrise action");
        if (wt.getCurrentState() == WorkerState.SLEEPING) {
            wt.setCurrentState(WorkerState.IDLE);
            
        }
    }

    @Override
    protected void defaultMorningAction() {
        
        Debug.info(npc.getName() + ": default morning action");
        Debug.info(Arrays.toString(npc.getEntity().getWorld().getChunkAt(npc.getEntity().getLocation()).getEntities()));
        
        if (wt.getCurrentState() == WorkerState.IDLE) {
            wt.setCurrentState(WorkerState.GOING_TO_WORK);
            
        }
    }

    @Override
    protected void defaultMiddayAction() {
        //
    }

    @Override
    protected void defaultDayAction() {
        Debug.info(npc.getName() + ": default day action");
    }

    @Override
    protected void defaultLateDayAction() {
       Debug.info(npc.getName() + ": default late day action");
    }

    @Override
    protected void defaultSunsetAction() {
        Debug.info(npc.getName() + ": default sunset action");
        if (wt.getCurrentState() == WorkerState.WORKING) {
            wt.setCurrentState(WorkerState.GOING_HOME);
        }
    }

    @Override
    protected void defaultNightAction() {
        Debug.info(npc.getName() + ": default night action");
        if(wt.getCurrentState() == WorkerState.AT_SAFE_LOCATION) {
            wt.setCurrentState(WorkerState.SLEEPING);
        }
    }

    @Override
    protected void onStateChanged() {
        Debug.info(npc.getName() + ": state changed! " + wt.getCurrentState());
    }

}
