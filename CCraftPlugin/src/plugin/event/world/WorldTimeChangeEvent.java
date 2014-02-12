/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.event.world;

import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;

/**
 * Call this event when time has changed
 * @author Chingo
 */
public class WorldTimeChangeEvent extends WorldEvent  {

    /**
     * Time in minecraft following the guidelines of http://minecraft.gamepedia.com/Day-night_cycle
     * where 1000 minecraft-ticks means 50 seconds of real time
     */
    public enum TIME{
        /**
         * Time 0 - 1000 ticks sun rises
         */
        DAWN,
        /**
         * Time 1000 - 3000 ticks, in minecraft 1000 is equal to the value of /time set day
         */
        MORNING,
        /**
         * Time 3000 - 6000 ticks
         */
        DAY,
        /**
         * Time 6000 - 9000 ticks 
         */
        MIDDAY,
        /**
         * Time 9000 - 12000 ticks 
         */
        AFTHER_MIDDAY,
        /**
         * Time 12000 - 14000 ticks,  sun almost down
         */
        DUSK,
        /**
         * Time > 14000 ticks
         */
        NIGHT;
    }
    
    WorldTimeChangeEvent(World world) {
        super(world);
    }

    public final TIME getTime() {
        long time = getWorld().getTime();
        if(time < 1000) {
            return TIME.DAWN;
        } else if (time < 3000) {
            return TIME.MORNING;
        } else if (time < 6000) {
            return TIME.DAY;
        } else if (time < 9000) {
            return TIME.MIDDAY;
        } else if (time < 12000) {
            return TIME.AFTHER_MIDDAY;
        } else if (time < 14000) {
            return TIME.DUSK;
        } else {
            return TIME.NIGHT;
        }
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
