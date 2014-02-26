/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package peasant.event.npc;

import java.util.List;
import net.citizensnpcs.api.event.NPCEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 *  Called when an npc kills an entity
 * @author Chingo
 */
public class NPCKillEvent extends NPCEvent  {
    private final Entity victim;
    private final int droppedExp;
    private final List<ItemStack> drops;
    
    public NPCKillEvent(NPC killer, Entity victim, int droppedExp, List<ItemStack> drops) {
        super(killer);
        
        this.victim = victim;
        this.droppedExp = droppedExp;
        this.drops = drops;
    }

    public Entity getVictim() {
        return victim;
    }

    public int getDroppedExp() {
        return droppedExp;
    }

    public List<ItemStack> getDrops() {
        return drops;
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
