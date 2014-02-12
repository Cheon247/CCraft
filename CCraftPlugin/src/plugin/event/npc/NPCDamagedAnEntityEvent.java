/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.event.npc;

import net.citizensnpcs.api.event.NPCEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Chingo
 */
public class NPCDamagedAnEntityEvent extends NPCEvent {
    
    private final Entity victim;
    
    public NPCDamagedAnEntityEvent(NPC damagerDealer, Entity victim) {
        super(damagerDealer);
        this.victim = victim;
    }

    public Entity getVictim() {
        return victim;
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
