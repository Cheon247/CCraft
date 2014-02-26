/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peasant.util.movement;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import util.debug.Debug;

/**
 *
 * @author Chingo
 */
public class WorkerExplore {
    private static int counter = 0;
    private static Map<Material, Integer> map = new HashMap<>();

    public static void findNearestChunk(NPC npc) {
        map.clear();
        counter = 0;
        Chunk chunk = npc.getEntity().getLocation().getChunk();
        long start = System.nanoTime();
        
        for (int y = npc.getEntity().getLocation().getBlockY(); y < 128; y+= 2) {
            if (sliceUp(chunk, y)) {
                break;
            }
        }

        for (int y = npc.getEntity().getLocation().getBlockY() - 1; y > 0; y-= 2) {
            if (sliceDown(chunk, y)) {
                break;
            }
        }
        long stop = System.nanoTime();
        
        for(Entry e : map.entrySet()) {
            Debug.info(e.getKey() + ":" + e.getValue());
        }
        
        Debug.info(counter + " blocks in " + ((stop - start)/1E6) + " ms");
        
        
        

    }

    private static boolean sliceUp(Chunk chunk, int y) {
        boolean isAllAir = true;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block b = chunk.getBlock(x, y, z);
                counter++;
                if (b.getType() != Material.AIR) {
                    if(!map.containsKey(b.getType())) {
                        map.put(b.getType(), 1);
                    } else {
                         map.put(b.getType(), map.get(b.getType()) + 1);
                    }
                    isAllAir = false;
                }
                b.setType(Material.REDSTONE_BLOCK);
            }
        }
        return isAllAir;
    }

    private static boolean sliceDown(Chunk chunk, int y) {
        boolean isAllDirtOrRock = true;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block b = chunk.getBlock(x, y, z);
                counter++;
                if (b.getType() != Material.DIRT
                        && b.getType() != Material.STONE
                        && b.getType() != Material.SAND) {
                    isAllDirtOrRock = false;
                    if(!map.containsKey(b.getType())) {
                        map.put(b.getType(), 1);
                    } else {
                         map.put(b.getType(), map.get(b.getType()) + 1);
                    }
                }
                 b.setType(Material.REDSTONE_BLOCK);
            }
        }
        return isAllDirtOrRock;
    }

}
