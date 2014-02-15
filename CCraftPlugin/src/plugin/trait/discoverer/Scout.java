/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.discoverer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import net.aufdemrand.sentry.SentryInstance;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.ai.goals.MoveToGoal;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.util.NMS;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import plugin.trait.worker.WorkerGoalPriority;
import plugin.trait.worker.WorkerJob;
import plugin.trait.worker.WorkerState;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public class Scout extends WorkerJob {

    private HashSet<Location> explored;

    public Scout(NPC npc) {
        super("scout", npc);
    }

    @Override
    protected void defaultSunRiseAction() {

    }

    @Override
    protected void defaultMorningAction() {
        if (wt.getCurrentState() == WorkerState.IDLE) {
            explore();
        }
    }

    @Override
    protected void defaultMiddayAction() {

    }

    @Override
    protected void defaultDayAction() {

    }

    @Override
    protected void defaultLateDayAction() {

    }

    @Override
    protected void defaultSunsetAction() {

    }

    @Override
    protected void defaultNightAction() {

    }

    @Override
    protected void onStateChanged() {

    }

    public void explore() {
        wt.setCurrentState(WorkerState.WORKING);
        Location location = npc.getEntity().getLocation();
        if (location.getWorld().getEnvironment() != World.Environment.NORMAL) {
            wt.setCurrentState(WorkerState.IDLE);
            return;
        }

        explored = new HashSet<>();
        int score = ForestScore.getForestScore(location.getBlock().getBiome());
        Debug.info("score was: " + score);

        if (score <= 40) {
            TreeMap<ForestLocation, Integer> locations = new TreeMap<>();

            for (BlockFace face : BlockFace.values()) {
                if (face == BlockFace.DOWN && face == BlockFace.SELF && face == BlockFace.UP) {
                    continue;
                }
                Location l = location.getBlock().getRelative(face, 200).getLocation();

                Block debug = l.getBlock().getRelative(BlockFace.UP, 40).getLocation().getBlock();
                if (debug.isEmpty() || debug.isLiquid()) {
                    debug.setType(Material.GLOWSTONE);
                }
                locations.put(new ForestLocation(l), ForestScore.getForestScore(l.getBlock().getBiome()));

            }

            Debug.info("\n");
            int counter = 0;
            for (ForestLocation l : locations.descendingKeySet()) {
                Debug.info((counter++) + "# :" + l.getBlock().getBiome().name());
            }

            Location des = locations.descendingKeySet().first();

            int threshold = 10;
            int speed = 10;

            npc.getNavigator().getLocalParameters().baseSpeed(speed);
            npc.getNavigator().getLocalParameters().pathDistanceMargin(threshold);

            if (!npc.getDefaultGoalController().isPaused()) {
                npc.getDefaultGoalController().setPaused(true);
            }
            
            npc.getNavigator().setTarget(des);

            npc.getDefaultGoalController().setPaused(false);
            
            Debug.info("destination: " + des + "," + des.getBlock().getBiome());
        }

    }

}
