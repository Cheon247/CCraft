/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.main;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.command.TestCommands;
import plugin.event.npc.NPCCombatEvents;
import plugin.event.world.WorldListener;

import plugin.listeners.item.NPCEggListener;
import plugin.listeners.npc.NPCInteractionListener;
import plugin.listeners.npc.NPCOwnerListener;
import plugin.worker.worker.WorkerTrait;

/**
 *
 * @author Chingo
 */
public final class CCraft extends JavaPlugin {
    


    @Override
    public void onEnable() {
        if (getServer().getPluginManager().getPlugin("Citizens") == null || getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(WorkerTrait.class).withName("worker"));

        getCommand("cctest").setExecutor(new TestCommands(this));
        this.getServer().getPluginManager().registerEvents(new NPCEggListener(this), this);
        this.getServer().getPluginManager().registerEvents(new NPCInteractionListener(this), this);
        this.getServer().getPluginManager().registerEvents(new NPCOwnerListener(this), this);
        this.getServer().getPluginManager().registerEvents(new NPCCombatEvents(this), this);
        this.getServer().getPluginManager().registerEvents(new WorldListener(this), this);
        
        
    }

    @Override
    public void onDisable() {
        getLogger().info("on disable invoked");
    }

    
}
