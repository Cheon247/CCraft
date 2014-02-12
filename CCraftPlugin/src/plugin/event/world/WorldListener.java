/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.event.world;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitScheduler;
import plugin.event.world.WorldTimeChangeEvent.TIME;
import plugin.main.CCraft;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public class WorldListener implements Listener {

    private final HashMap<String, TIME> times; // multiple worlds, different times
    private final CCraft ccraft;
    private final long INTERVAL_25_SECONDS = 500L; 

    public WorldListener(CCraft plugin) {
        this.ccraft = plugin;
        this.times = new HashMap<>();

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(ccraft, new Runnable() {
            @Override
            public void run() {
                for (World world : ccraft.getServer().getWorlds()) {
                    if (world.getEnvironment() == World.Environment.NORMAL) {
                        Bukkit.getPluginManager().callEvent(new WorldTimeChangeEvent(world)); // Fire a Time Change event every 20 sec for each NORMAL world
                    }
                }
            }
        }, 0L, INTERVAL_25_SECONDS); // NO DELAY
    }

    @EventHandler()
    public void onTimeChange(WorldTimeChangeEvent wtce) {
        String worldID = wtce.getWorld().getUID().toString();
        if (times.containsKey(worldID)) {
            if (times.get(worldID) != wtce.getTime()) {
                times.put(worldID, wtce.getTime());
                Bukkit.getPluginManager().callEvent(new WorldDayCycleEvent(wtce.getWorld()));
            }
        } else {
            times.put(worldID, wtce.getTime());
            Bukkit.getPluginManager().callEvent(new WorldDayCycleEvent(wtce.getWorld()));
        }
    }
    
    @EventHandler()
    public void onPlayerTimeCommand(PlayerCommandPreprocessEvent pcpe) {
        if(pcpe.getPlayer().isOp()
                && pcpe.getMessage().contains("time")
                && pcpe.getPlayer().getWorld().getEnvironment() == World.Environment.NORMAL) {
            // Will check if time has changed enough to declare a WorldDayCycleEvent
            Bukkit.getPluginManager().callEvent(new WorldTimeChangeEvent(pcpe.getPlayer().getWorld()));
        }
    }
    
    @EventHandler() 
    public void onDayCycleEvent(WorldDayCycleEvent wdce) {
        Debug.info(wdce.getTime().name());
    }
    
}
