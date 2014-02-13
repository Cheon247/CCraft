/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.trait.lumberer;

import org.bukkit.event.Listener;
import plugin.event.worker.WorkerStateChangeEvent;

/**
 *
 * @author Chingo
 */
public class LumbererListener implements Listener {
    
    public void onLumbererStateChanged(WorkerStateChangeEvent wsce) {
        if(wsce.getNPC().hasTrait(LumbererTrait.class)) { // only listen to lumberers
            LumbererTrait lt = wsce.getNPC().getTrait(LumbererTrait.class);
            
        }
    }
    
}
