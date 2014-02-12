/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.listeners.item;

import java.util.logging.Logger;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CommandSenderCreateNPCEvent;
import net.citizensnpcs.api.event.PlayerCreateNPCEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.MobType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import plugin.main.CCraft;

import plugin.interaction.SentryInteraction;
import plugin.recipe.EggRecipes;

/**
 *
 * @author Chingo
 */
public class NPCEggListener implements Listener {

    private final CCraft plugin;
    private final Logger log;
    private final NPCRegistry npcRegistry;

    public NPCEggListener(CCraft plugin) {
        this.plugin = plugin;
        this.log = plugin.getLogger();

        npcRegistry = CitizensAPI.getNPCRegistry();
    }

    @EventHandler
    public void onEggInteraction(PlayerInteractEvent pie) {
        Player eggThrower = pie.getPlayer();

        if (!eggThrower.getItemInHand().isSimilar(EggRecipes.NPC_EGG_RECIPE.getRecipe().getResult())) {
            return; // Nothing to do here...
        }

        log.info("it was an Egg-Throw-Event!");

        int itemAmount = pie.getItem().getAmount();
        if (itemAmount > 0) {

            NPC npc = npcRegistry.createNPC(EntityType.PLAYER, "Adventurer");
            Location loc = eggThrower.getLocation();
            npc.spawn(loc);

            SentryInteraction si = new SentryInteraction();
            si.makeOwner(eggThrower, npc);
            si.makeSentry(eggThrower, npc, true);
            si.targetMonsters(eggThrower, npc);
            si.setArmor(npc, 5);
            si.setStrength(npc, 5);
            npc.getTrait(MobType.class).setType(EntityType.PLAYER);

            CommandSenderCreateNPCEvent event = new PlayerCreateNPCEvent(eggThrower, npc);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                npc.destroy();
                String reason = "Couldn't create npc.";
                if (!event.getCancelReason().isEmpty()) {
                    reason += event.getCancelReason();
                }
                eggThrower.sendMessage(reason);
            } else {
                eggThrower.sendMessage("You created " + npc.getName());
                eggThrower.getInventory().getItemInHand().setAmount(--itemAmount); 
                if (itemAmount <= 0 && pie.getItem() != null) {
                    eggThrower.getInventory().remove(eggThrower.getItemInHand());
                    log.info("item depleted and removed from inventory");
                }
            }
        } else {
            throw new IllegalStateException("item in hand, but amount was 0");
        }
    }

}
