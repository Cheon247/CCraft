/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ccnpcs.event.worker;

import net.citizensnpcs.api.event.NPCEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.HandlerList;
import worker.worker.WorkerTrait;

/**
 *
 * @author Chingo
 */
public class WorkerStateChangeEvent extends NPCEvent {
    
    private final NPC worker;
    
    /**
     * Constructor
     * @param worker The npc with the WorkerTrait
     */
    public WorkerStateChangeEvent(NPC worker) {
        super(worker);
        if(!worker.hasTrait(WorkerTrait.class)) throw new IllegalArgumentException("not a worker");

        this.worker = worker;
    }

        @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    

    
    
    
}
