/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.worker.jobs.scout;

import java.util.Objects;
import org.bukkit.Location;

/**
 * 
 * @author Chingo
 */
public final class ChunkLocation {
    public final int x;
    public final int z;
    public final String world;
    
    public ChunkLocation(Location location) {
        this.x = location.getBlockX();
        this.z = location.getBlockZ();
        this.world = location.getWorld().getUID().toString();
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof ChunkLocation)) return false;
        ChunkLocation o = (ChunkLocation) other;
        if(this == o) return true;
        if(!this.world.equals(other)) return false;
        return (this.x != o.x && this.z == o.z);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.x;
        hash = 83 * hash + this.z;
        hash = 83 * hash + Objects.hashCode(this.world);
        return hash;
    }

    @Override
    public String toString() {
        return "( X:" + x + "\t Z:" + z + ")"; 
    }
    
    



    
    
}
