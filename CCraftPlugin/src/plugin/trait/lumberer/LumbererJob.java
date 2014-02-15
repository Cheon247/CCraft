/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.lumberer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import plugin.trait.worker.WorkerJob;
import plugin.trait.worker.WorkerState;
import plugin.trait.worker.WorkerTrait;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public class LumbererJob extends WorkerJob {
    
    public LumbererJob(NPC npc) {
        super("lumberer", npc);
        if (!npc.hasTrait(WorkerTrait.class)) {
            throw new IllegalArgumentException("NPC have the worker trait");
        }
        
    }
    
    @Override
    protected void defaultSunRiseAction() {
        Debug.info(npc.getName() + ": default sunrise action");
        if (wt.getCurrentState() == WorkerState.SLEEPING) {
            wt.setCurrentState(WorkerState.IDLE);
            
        }
    }
    
    @Override
    protected void defaultMorningAction() {
        
        Debug.info(npc.getName() + ": default morning action");
        Debug.info(Arrays.toString(npc.getEntity().getWorld().getChunkAt(npc.getEntity().getLocation()).getEntities()));
        
        if (wt.getCurrentState() == WorkerState.IDLE) {
            wt.setCurrentState(WorkerState.GOING_TO_WORK);
            
        }
    }
    
    @Override
    protected void defaultMiddayAction() {
        //
    }
    
    @Override
    protected void defaultDayAction() {
        Debug.info(npc.getName() + ": default day action");
    }
    
    @Override
    protected void defaultLateDayAction() {
        Debug.info(npc.getName() + ": default late day action");
    }
    
    @Override
    protected void defaultSunsetAction() {
        Debug.info(npc.getName() + ": default sunset action");
        if (wt.getCurrentState() == WorkerState.WORKING) {
            wt.setCurrentState(WorkerState.GOING_HOME);
        }
    }
    
    @Override
    protected void defaultNightAction() {
        Debug.info(npc.getName() + ": default night action");
        if (wt.getCurrentState() == WorkerState.AT_SAFE_LOCATION) {
            wt.setCurrentState(WorkerState.SLEEPING);
        }
    }
    
    @Override
    protected void onStateChanged() {
        Debug.info(npc.getName() + ": state changed! " + wt.getCurrentState());
    }
    
    public final int findWood(int threshold, int extra, int depth, Location start, HashSet<Block> discovered, int researched) {
        depth--;
        if(depth <= 0) return researched;
        if(discovered.size() > 5000) return researched;
        HashMap<Location, Boolean> area = new HashMap<>();
        
        researched += putExtra(area, start.getBlock().getRelative(BlockFace.UP, 4).getLocation(), threshold, extra, researched);
        
        for (Location l : area.keySet()) {
             l.getBlock().setType(Material.REDSTONE_BLOCK);
            if (area.get(l)) {
                discovered.add(l.getBlock());
                
                researched += findWood(threshold, extra, depth, l.getBlock().getRelative(BlockFace.DOWN, 4).getLocation(), discovered, researched);
                
            } else {
//                l.getBlock().setType(Material.AIR);
            }
        }
        return researched;
    }
    
    
    
    private void checkNeighBours(Block block, HashMap<Location, Boolean> area) {
        for(BlockFace face : BlockFace.values()) {
            if(isWoodOrLeave(block.getRelative(face).getLocation().getBlock())){
                area.put(block.getRelative(face).getLocation(), isWoodOrLeave(block.getRelative(face)));
            }
        }
    }
    
    
    public int putExtra(HashMap<Location, Boolean> area, Location origin, int threshold, int extra, int researched) {
        if(threshold == 1 || extra == 1) return researched;
        for (BlockFace face : BlockFace.values()) {
            if (face == BlockFace.DOWN || face == BlockFace.UP) {
                continue;
            }
            area.put(origin, isWoodOrLeave(origin.getBlock().getRelative(face, threshold)));
            researched++;
            for (int i = 0; i < extra; i++) {
                area.put(origin.getBlock().getRelative(face, i * threshold).getLocation(), isWoodOrLeave(origin.getBlock().getRelative(face, i * threshold)));
                checkNeighBours(origin.getBlock(), area);
                researched++;
            }
            
     
        }
        return researched;
    }
    
    public Boolean isWoodOrLeave(Block block) {
        return block.getType() == Material.WOOD || block.getType() == Material.LEAVES;
    }
    
}
