/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.command;

import java.util.Iterator;
import java.util.logging.Logger;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import plugin.main.CCraft;
import plugin.util.Debug;
import plugin.worker.actions.WorkerMovement;
import plugin.util.NPCTool;
import plugin.worker.worker.Job;
import plugin.worker.worker.JobFactory;
import plugin.worker.worker.WorkerTrait;

/**
 *
 * @author Chingo
 */
public class TestCommands implements CommandExecutor {

    private final NPCTool npcTool;
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    public TestCommands(CCraft ccraft) {
        this.npcTool = new NPCTool(ccraft);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        String testMove = args[0];
        if (!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED + "[CCTEST]: ONLY PLAYERS CAN PERFORM THIS COMMAND");
        } else if (!cs.isOp()) {
            cs.sendMessage(ChatColor.RED + "[CCTEST]: ONLY OPs CAN PERFORM THIS COMMAND");
        }

        switch (testMove) {
            case "create":
                return createTester((Player) cs);
            case "moveto":
                return testMove((Player) cs, args);
            case "make":
                return assignJob((Player) cs, args);
            case "follow":
                return testFollow();
            case "explore" : return testExplore((Player) cs);
            default:
                cs.sendMessage(ChatColor.RED + "[CCTEST]: TEST NOT RECOGNIZED");
                return false;
        }

    }

    private boolean testMove(Player ply, String[] args) {
        NPC npc = npcTool.getSelected(ply);
        if (npc == null) {
            ply.sendMessage(ChatColor.RED + "[CCTEST]: NO NPC SELECTED");
            return false;
        }

        if (args.length == 2) {
            if (args[1].equals("me")) {
                WorkerMovement.move(npc, ply.getLocation());
                return true;
            }
        } else if (args.length == 3) {
            if (args[1].equals("me")) {
                float speed;
                try {
                    speed = Float.parseFloat(args[2]);
                } catch (NumberFormatException e) {
                    ply.sendMessage(ChatColor.RED + "[CCTEST]: NO VALID SPEED");
                    return false;
                }
                WorkerMovement.move(npc, ply.getLocation(), speed);
                return true;
            }
        }
        return false;

    }

    private boolean assignJob(Player ply, String[] args) {
        if (args.length != 2) {
            ply.sendMessage(ChatColor.RED + "[CCTEST]: TOO FEW ARGUMENTS");
            return false;
        } else if (npcTool.getSelected(ply) == null) {
            ply.sendMessage(ChatColor.RED + "[CCTEST]: NO NPC SELECTED");
            return false;
        } else if (!npcTool.getSelected(ply).hasTrait(WorkerTrait.class)) {
            ply.sendMessage(ChatColor.RED + "[CCTEST]: SELECTED NPC IS NOT A WORKER");
            return false;
        }

        String jobname = args[1];
        Job job = JobFactory.getJob(jobname);
        NPC npc = npcTool.getSelected(ply);
        if (job != null) {
            npcTool.getSelected(ply).getTrait(WorkerTrait.class).getWorker().setJob(jobname);
            ply.sendMessage(ChatColor.GREEN + "[CCTEST]: " + npc.getName().toUpperCase() + " IS NOW A " + jobname.toUpperCase());
            return true;
        }
        ply.sendMessage(ChatColor.RED + "[CCTEST]: NO JOB FOUND FOR " + jobname);
        return false;
    }

    private boolean testFollow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean createTester(Player ply) {

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "TESTER");
        npc.spawn(ply.getLocation());
        npc.addTrait(WorkerTrait.class);
        Debug.info(npc.getName() + ": Traits start");
        for (Iterator<Trait> it = npc.getTraits().iterator(); it.hasNext();) {
            Trait t = it.next();
            Debug.info(t.getName());
        }
        Debug.info(npc.getName() + ": Traits end");

        npcTool.selectNPC(ply, npc);
        ply.sendMessage(ChatColor.BLUE + "[CCTEST]: TESTER CREATED");
        return true;
    }

    private boolean testExplore(Player player) {
        NPC npc = npcTool.getSelected(player);
        if(npc != null) {
           
            return true;
        }
        player.sendMessage(ChatColor.RED + "[CCTEST]: YOU MUST FIRST SELECT AN NPC");
        return false;
    }

}
