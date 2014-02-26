/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import org.bukkit.World;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Chingo
 */
public class WorldDayCycleEvent extends WorldTimeChangeEvent {

    WorldDayCycleEvent(World world) {
        super(world);
        if(world.getEnvironment() != World.Environment.NORMAL) throw new IllegalArgumentException("only normal worlds know daycycles");
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
