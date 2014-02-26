/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;

/**
 * Call this event when time has changed
 * @author Chingo
 */
public class WorldTimeChangeEvent extends WorldEvent  {

    
    WorldTimeChangeEvent(World world) {
        super(world);
    }

    public final WorldTime getTime() {
        return WorldTime.serverTicksToTIME(getWorld().getTime());
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
