/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.Worker;

import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        if (!ndbe.getNPC().hasTrait(WorkerTrait.class)) {
            return;
        }
        if (!ndbe.getNPC().hasTrait(Owner.class)) {
            throw new IllegalStateException("Worker with no owner");
        }

        final NPC worker = ndbe.getNPC();
        final WorkerTrait wt = worker.getTrait(WorkerTrait.class);

        // Check if you must reteat, if there is no location to retreat to, the unit will be forced to engage combat
        if(wt.getCurrentState() != WorkerState.RETREAT
                && wt.getCurrentState() != WorkerState.IN_COMBAT
                && wt.getCurrentState() != WorkerState.FLEE) {
            if(wt.isAvoidingCombat()) {
                wt.setCurrentState(WorkerState.RETREAT);
                wt.goToClosestRetreatLocation(ndbe.getDamager());
            } else {
                if(worker.hasTrait(SentryTrait.class) && ndbe.getDamager() instanceof LivingEntity) {
                    LivingEntity damager = (LivingEntity) ndbe.getDamager();
                    SentryTrait st = worker.getTrait(SentryTrait.class);
                    st.getInstance().setTarget(damager, true);
                    wt.setCurrentState(WorkerState.IN_COMBAT);
                }
            }
        } 
    }

}
