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
public class NPCTargetChangedEvent extends NPCEvent {
    private final Entity target;

    public NPCTargetChangedEvent(NPC npc, Entity newTarget) {
        super(npc);
        this.target = newTarget;
    }

    public Entity getTarget() {
        return target;
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
