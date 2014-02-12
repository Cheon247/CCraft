/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.listeners.npc;

import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import plugin.main.CCraft;
import plugin.trait.Worker.WorkerTrait;
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
        if (!ndbe.getNPC().hasTrait(WorkerTrait.class)) {
            return;
        }
        if (!ndbe.getNPC().hasTrait(Owner.class)) {
            throw new IllegalStateException("Worker with no owner");
        }

        final NPC worker = ndbe.getNPC();
        final WorkerTrait wt = worker.getTrait(WorkerTrait.class);

        switch (wt.currentState) {
            case FLEEING:
                break;
            case GOING_TO_WORK:
                wt.goToClosestRetreatLocation(ndbe.getDamager());
                break;
            case GOING_HOME:
                wt.goToClosestRetreatLocation(ndbe.getDamager());
                break;
            default:
                break;
        }
    }

}
