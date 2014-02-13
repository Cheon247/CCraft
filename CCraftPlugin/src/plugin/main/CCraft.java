/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.main;

import java.util.Arrays;
import java.util.logging.Level;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.command.WorkerCommands;
import plugin.event.npc.NPCCombatEvents;
import plugin.event.world.WorldListener;

import plugin.listeners.item.NPCEggListener;
import plugin.listeners.npc.NPCInteractionListener;
import plugin.listeners.npc.NPCOwnerListener;
import plugin.trait.Worker.WorkerListener;
import plugin.trait.lumberer.LumbererTrait;
import plugin.util.Debug;

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
            
            getCommand("worker").setExecutor(new WorkerCommands(this));
            return;
        }
        

        this.getServer().getPluginManager().registerEvents(new NPCEggListener(this), this);
        this.getServer().getPluginManager().registerEvents(new NPCInteractionListener(this), this);
        this.getServer().getPluginManager().registerEvents(new NPCOwnerListener(this), this);
        this.getServer().getPluginManager().registerEvents(new NPCCombatEvents(this), this);
        this.getServer().getPluginManager().registerEvents(new WorkerListener(this), this);
        this.getServer().getPluginManager().registerEvents(new WorldListener(this), this);
        
//        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(LumbererTrait.class));
        RecipeLoader.load(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("on disable invoked");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String theCommand = command.getName().toLowerCase();

        if (sender instanceof Player) {
            switch (theCommand) {
                case "worker":
                    Debug.info("worker command!");
                    Debug.info(args.length + "");
                    Debug.info(Arrays.toString(args));
                    
                    return true;
                default:
                    return false;
            }
        } else {
            return false;
        }

    }

}
