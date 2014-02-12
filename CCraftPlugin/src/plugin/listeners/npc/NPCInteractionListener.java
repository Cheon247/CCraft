/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.listeners.npc;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.npc.NPCSelector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import plugin.main.CCraft;
import plugin.interaction.SentryInteraction;
import plugin.util.Materials;

/**
 *
 * @author Chingo
 */
public class NPCInteractionListener implements Listener {
    
    private final NPCSelector nselector;
    private final NPCRegistry nregister;
    private final CCraft plugin;
    private final Logger LOGGER;
    
    public NPCInteractionListener(CCraft plugin) {
        this.nselector = CitizensAPI.getDefaultNPCSelector();
        this.nregister = CitizensAPI.getNPCRegistry();
        this.plugin = plugin;
        this.LOGGER = Logger.getLogger(getClass().getName());
    }
    
    @EventHandler
    public void onRightClickNPC(NPCRightClickEvent rce) {
        NPC npc = rce.getNPC();
        Player player = rce.getClicker();
        
        if(npc.hasTrait(SentryTrait.class)) {
            SentryInteraction si = new SentryInteraction();
            if(Materials.isArmor(player.getItemInHand().getType())){ // if player has armor in hand
                LOGGER.log(Level.INFO, "{0}: wants to give armor to {1}", new Object[]{player.getPlayerListName(), npc.getName()});
                si.giveEquipment(player, npc, player.getItemInHand().clone());
                player.getInventory().remove(player.getItemInHand());
            }
        }
    }
    

    
   
    
    
    
    
    
    
    
    
    
    
    
    
}
