/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.worker.worker;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;

/**
 *
 * @author Chingo
 */
public class SafeLocation extends Location implements Comparable<Location> {
    private final NPC worker;

    public SafeLocation(Location location, NPC worker) {
        super(location.getWorld(), location.getX(), location.getY(), location.getZ());
        this.worker = worker;
    }

    @Override
    public int compareTo(Location o) {
        Location currentWorkerLocation = worker.getEntity().getLocation();
        double myDistance = distance(currentWorkerLocation);
        double othDistance = o.distance(currentWorkerLocation);
        
        if(myDistance > othDistance) {
            return 1;
        } else if(myDistance == othDistance){
            return 0;
        } else {
            return -1;
        }
    }
    
}
