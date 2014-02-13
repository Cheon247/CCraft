/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.event.world;

/**
 *
 * @author Chingo
 */

    /**
     * Time based on http://minecraft.gamepedia.com/Day-night_cycle
     * where 1000 minecraft-ticks means 50 seconds of real time
     */
    public enum TIME{
        /**
         * Time 0 - 999 ticks sun rises, 
         * 06:00 - 06:59
         */
        SUNRISE,
        /**
         * Time 1000 - 2999 ticks, in minecraft 1000 is equal to the value of /time set day
         * 07:00 - 08:59
         */
        MORNING,
        /**
         * Time 3000 - 6000 ticks
         * 09:00 - 11:59
         */
        MIDDAY,
        /**
         * Time 6000 - 9000 ticks 
         * 12:00 - 14:59
         */
        DAY,
        /**
         * Time 9000 - 12000 ticks 
         * 15:00 - 17:59
         */
        LATE_DAY,
        /**
         * Time 12000 - 14000 ticks,  sun almost down
         * 18:00 - 20:00
         */
        SUNSET,
        /**
         * Time > 14000 ticks
         * 20:00 - 05:59
         */
        NIGHT;
        
        /**
         * Gets the time value for given serverticks
         * @param serverTicks The amount of serverTicks
         * @return TIME associated with the amount of server ticks based on http://minecraft.gamepedia.com/Day-night_cycle
         */
        public static TIME serverTicksToTIME(final long serverTicks) {
        if(serverTicks < 1000) {
            return TIME.SUNRISE;
        } else if (serverTicks < 3000) {
            return TIME.MORNING;
        } else if (serverTicks < 6000) {
            return TIME.MIDDAY;
        } else if (serverTicks < 9000) {
            return TIME.DAY;
        } else if (serverTicks < 12000) {
            return TIME.LATE_DAY;
        } else if (serverTicks < 14000) {
            return TIME.SUNSET;
        } else {
            return TIME.NIGHT;
        }
    }
        
    }
