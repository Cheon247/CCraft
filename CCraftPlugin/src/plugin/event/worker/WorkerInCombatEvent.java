package plugin.event.worker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import net.citizensnpcs.api.event.NPCEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import plugin.trait.Worker.WorkerTrait;

/**
 *
 * @author Chingo
 */
public class WorkerInCombatEvent extends NPCEvent {
    
    private final NPC worker;
    private final Entity attacker;
    
    /**
     * Constructor
     * @param worker The npc with the WorkerTrait
     * @param attacker The living entity who attacked this npc
     */
    public WorkerInCombatEvent(NPC worker, Entity attacker) {
        super(worker);
        if(!worker.hasTrait(WorkerTrait.class)) throw new IllegalArgumentException("not a worker");
        if(attacker == null || worker == null) throw new NullPointerException(); // obviously...
        
        this.worker = worker;
        this.attacker = attacker;
        
        
    }

        @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets a copy of the worker who was attacked
     * @return copy of the worker
     */
    public final NPC getWorker() {
        return worker.clone();  // To ensure the worker still has the trait when this event was invoked
    }

    /**
     * Gets the LivingEntity who attacked the worker
     * @return LivingEntity The attacker
     */
    public final Entity getAttacker() {
        return attacker;
    }
    
    
    
}
