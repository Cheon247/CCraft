/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CommandSenderCreateNPCEvent;
import net.citizensnpcs.api.event.PlayerCreateNPCEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.npc.NPCSelector;
import net.citizensnpcs.api.trait.trait.MobType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import plugin.main.CCraft;
import plugin.trait.discoverer.Scout;
import plugin.trait.lumberer.LumbererJob;
import plugin.trait.worker.WorkerJob;
import plugin.trait.worker.WorkerTrait;
import plugin.util.Debug;
import plugin.util.NPCTool;

/**
 *
 * @author Chingo
 */
public class WorkerCommands implements CommandExecutor {

    private final NPCRegistry npcRegistry;
    private final NPCSelector npcSelector;
    private final NPCTool npcTool;
    private final CCraft ccraft;

    public WorkerCommands(CCraft plugin) {
        npcSelector = CitizensAPI.getDefaultNPCSelector();
        npcRegistry = CitizensAPI.getNPCRegistry();
        this.npcTool = new NPCTool(plugin);
        this.ccraft = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, org.bukkit.command.Command cmnd, String string, String[] args) {
        if (cs instanceof Player) {
            String modifier = args[0];
            switch (modifier) {
                case "create":
                    return processCreate((Player) cs, args);
                case "explore":
                    return processExplore((Player) cs, args);
                case "job":
                    return processJob((Player) cs, args);
                default:
                    cs.sendMessage("unknown modifier: " + modifier);
                    return false;
            }
        } else {
            cs.sendMessage("You must be a player");
            return false;
        }
    }

    private boolean processCreate(Player sender, String[] args) {
        if (args.length <= 2) {
            sender.sendMessage("too few arguments");
            return false;
        }

        int popcap = ccraft.getConfig().getInt("limits.popcap");
        if (popcap != 0 && npcTool.getNPCS(sender).size() == popcap) {
            sender.sendMessage("popcap reached: " + String.valueOf(npcTool.getNPCS(sender).size()) + "/" + String.valueOf(popcap));
        }

        String trait = args[1];
        String name = args[2];

        NPC npc = npcRegistry.createNPC(EntityType.PLAYER, name);
        Location loc = sender.getLocation();
        npc.spawn(loc);
        npc.getTrait(MobType.class).setType(EntityType.PLAYER);
        npc.addTrait(WorkerTrait.class);

        switch (trait) {
            case "lumberer":
                npc.getTrait(WorkerTrait.class).setJob(new LumbererJob(npc));
                npcTool.selectNPC(sender, npc);
                break;

            case "scout":
                npc.getTrait(WorkerTrait.class).setJob(new Scout(npc));
                npcTool.selectNPC(sender, npc);
                break;
            default:
                sender.sendMessage("unknown job: " + trait);
                npc.destroy();
        }

        CommandSenderCreateNPCEvent event = new PlayerCreateNPCEvent(sender, npc);
        Bukkit.getPluginManager().callEvent(event);

        return true;
    }

    private boolean processExplore(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage("too few arguments");
            return false;
        }

        NPC npc = npcTool.getSelected(player);
        if (npc == null) {
            player.sendMessage("You must first select a npc");
            return false;
        }

       

        if (!npc.hasTrait(WorkerTrait.class)) {
            player.sendMessage(npc.getName() + ": does not have the worker trait");
            return false;
        }

        if (npc.getTrait(WorkerTrait.class).getJob() == null) {
            player.sendMessage("Worker doesnt have a job");
            return false;
        }

        WorkerJob w = npc.getTrait(WorkerTrait.class).getJob();

        if (w.getName().equals("scout")) {
            Scout s = (Scout) w;

          
            s.explore();

            return true;
        }
        player.sendMessage(npc.getName() + ": not a scout");
        return false;

    }

    private boolean processJob(Player player, String[] args) {
        if (args.length != 1) {
            player.sendMessage("Too many arguments!");
            return false;
        }

        NPC npc = npcTool.getSelected(player);
        if (npc == null) {
            player.sendMessage("You must first select a npc");
            return false;
        }

        if (!npc.hasTrait(WorkerTrait.class)) {
            player.sendMessage("NPC is not a worker");
            return false;
        }

        WorkerJob job = npc.getTrait(WorkerTrait.class).getJob();

        String jobName;
        if (job == null) {
            jobName = " no job";
        } else {
            jobName = job.getName();
        }
        player.sendMessage(npc.getName() + ": " + jobName);

        return true;
    }

}
