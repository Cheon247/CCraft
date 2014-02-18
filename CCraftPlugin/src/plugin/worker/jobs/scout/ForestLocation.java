/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.worker.jobs.scout;

import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author Chingo
 */
public class ForestLocation extends Location implements Comparable<ForestLocation>{

    public ForestLocation(World world, double x, double y, double z) {
        super(world, x, y, z);
    }
    
    public ForestLocation(Location loc) {
        super(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
    }

    
    
    @Override
    public int compareTo(ForestLocation o) {
        if(ForestScore.getForestScore(getBlock().getBiome()) > ForestScore.getForestScore(o.getBlock().getBiome())){
            return 1;
        } else if (ForestScore.getForestScore(getBlock().getBiome()) == ForestScore.getForestScore(o.getBlock().getBiome())){
            return 0;
        } else {
            return -1;
        }
    }
    
    
    
}
