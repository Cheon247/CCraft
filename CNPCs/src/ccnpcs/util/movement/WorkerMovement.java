/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ccnpcs.util.movement;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import util.debug.Debug;
import util.debug.Debug;

/**
 *
 * @author Chingo
 */
public class WorkerMovement {
    
    public static void move(NPC npc , Location destination) {
        if(npc.getNavigator().isNavigating()) {
            npc.getNavigator().setPaused(true);
            npc.getNavigator().cancelNavigation();
        }
        Debug.info("trying to move npc to location");
        npc.getNavigator().getLocalParameters().baseSpeed(0.3f);
        npc.getNavigator().setTarget(destination);
        npc.getNavigator().setPaused(false);
    }
    
    public static void move(NPC npc , double x, double y, double z) {
        move(npc, new Location(npc.getEntity().getWorld(), x, y, z));
    }
    
    public static void move(NPC npc , Location destination, float speed) {
        Debug.info("trying to move npc to location");
        if(npc.getNavigator().isNavigating()) {
            npc.getNavigator().setPaused(true);
            npc.getNavigator().cancelNavigation();
        }
        npc.getNavigator().getLocalParameters().baseSpeed(speed);
        npc.getNavigator().setTarget(destination);
        npc.getNavigator().setPaused(false);
    }
    
    public static void move(NPC npc , double x, double y, double z, float speed) {
        move(npc, new Location(npc.getEntity().getWorld(), x, y, z), speed);
    }
    
    
   
}
