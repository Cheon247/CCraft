/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.worker.jobs.scout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import plugin.util.Debug;

/**
 * A scout will generate fielddata, field data is the information gathered from 1 chunk
 * @author Chingo
 */
public final class FieldData {
    private final ChunkLocation chunk;
    private final HashMap<Material, Integer> count;
    private final HashMap<Material, Set<ChunkLocation>> field;
    
    public FieldData(Location chunk) {
        this.count = new HashMap<>();
        this.field = new HashMap<>();
        this.chunk = new ChunkLocation(chunk);
    }
    
    public final void write(final Location location) {
        Material type = location.getBlock().getType();
        if(!count.containsKey(type)) count.put(type, 0);
        if(!field.containsKey(type)) field.put(type, new HashSet<>());
        
        count.put(type, count.get(type) + 1);
        if(field.get(type).add(new ChunkLocation(location))){
            Debug.info("Duplicate location!");
        }
    }
    
    public final HashMap<Material,Integer> getCountData() {
        return count;
    }
    
    public final HashMap<Material, Set<ChunkLocation>> getFieldData() {
        return field;
    }
    
    public final ChunkLocation getChunkLocation() {
        return chunk;
    }
    
    
    
    
    
    
    
}
