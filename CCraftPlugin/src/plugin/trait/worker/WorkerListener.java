/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.worker;

import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import plugin.event.world.WorldDayCycleEvent;
import plugin.main.CCraft;
import plugin.util.NPCTool;

/**
 *
 * @author Chingo
 */
public class WorkerListener implements Listener {

    private final NPCTool npcTool;

    public WorkerListener(CCraft ccraft) {
        npcTool = new NPCTool(ccraft);
    }

    @EventHandler
    public void onNPCAttacked(NPCDamageByEntityEvent ndbe) {
       
    }
    
    @EventHandler
    public void onDayCycleEvent(WorldDayCycleEvent wdce) {
        for (NPC npc : npcTool.getWorkers()) {
            if(npc.getTrait(WorkerTrait.class).getJob() != null) {
                npc.getTrait(WorkerTrait.class).getJob().takeDefaultAction();
            }
        }
    }

}
