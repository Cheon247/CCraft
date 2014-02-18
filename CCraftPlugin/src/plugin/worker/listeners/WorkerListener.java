/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.worker.listeners;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.Listener;
import plugin.event.world.WorldDayCycleEvent;
import plugin.main.CCraft;
import plugin.util.NPCTool;
import plugin.worker.worker.WorkerTrait;

/**
 *
 * @author Chingo
 */
public class WorkerListener implements Listener {
    
    private final NPCTool npcTool;
    
    public WorkerListener(CCraft ccraft) {
        this.npcTool = new NPCTool(ccraft);
    }

    public void onDayTimeEvent(WorldDayCycleEvent wdce) {
        for(NPC npc : npcTool.getWorkers()) {
            npc.getTrait(WorkerTrait.class).getWorker().getJob().defaultAcion();
        }
    }
    
}
