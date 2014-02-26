/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ccraft;

import java.util.logging.Level;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.plugin.java.JavaPlugin;
import worker.worker.WorkerTrait;
import world.WorldListener;

/**
 *
 * @author Chingo
 */
public class CCraft extends JavaPlugin {
    
        @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("Citizens") == null || getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(WorkerTrait.class).withName("worker"));
        
        getServer().getPluginManager().registerEvents(new WorldListener(this), this);
        
        
        //TODO Register Command executors
        //TODO Register Listeners
        //TODO Register Recipes?
        
        
    }

    @Override
    public void onDisable() {
        getLogger().info("on disable invoked");
    }
    
}
