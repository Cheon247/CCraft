package ccnpcs.event.npc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import ccnpcs.event.npc.NPCDamagedAnEntityEvent;
import ccnpcs.event.npc.NPCKillEvent;
import ccnpcs.event.npc.NPCTargetChangedEvent;

import util.debug.Debug;

/**
 *
 * @author Chingo
 */
public class NPCCombatEvents implements Listener {
    
    private final NPCRegistry npcRegistry;
    
    public NPCCombatEvents() {
        this.npcRegistry = CitizensAPI.getNPCRegistry();
    }
    
    @EventHandler
    void onDamageDealed(EntityDamageByEntityEvent edbe) {
        if(edbe.getDamager().hasMetadata("NPC")) {
            NPC npc = npcRegistry.getNPC(edbe.getDamager());
            Debug.info(npc.getName() + ": did some damage!");
            
            NPCDamagedAnEntityEvent event = new NPCDamagedAnEntityEvent(npc, edbe.getEntity());
            Bukkit.getPluginManager().callEvent(event);
        }
    }
    
    @EventHandler
    void onKill(EntityDeathEvent ede) {
        if(ede.getEntity().getKiller() != null && ede.getEntity().getKiller().hasMetadata("NPC")) {
            Debug.info(ede.getEntity().getKiller().getPlayerListName() + ": killed someone");
            
            // Get the npc which is the killer
            Entity entity = (Entity) ede.getEntity().getKiller();
            NPC killer = npcRegistry.getNPC(entity);
            
            if(killer == null) throw new AssertionError("No NPC found");
             
            NPCKillEvent event = new NPCKillEvent(killer, ede.getEntity(), ede.getDroppedExp(), ede.getDrops());
            Bukkit.getPluginManager().callEvent(event);
        }
    }
    
    
    @EventHandler
    void onNPCTargetChanged(EntityTargetEvent ete) {
        if(ete.getEntity().hasMetadata("NPC")) {
            Entity entity = ete.getEntity();
            NPC npc = npcRegistry.getNPC(entity);
            NPCTargetChangedEvent event = new NPCTargetChangedEvent(npc, ete.getTarget());
            Bukkit.getPluginManager().callEvent(event);
        }
    }
    
}
