/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.listeners.npc;

import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import plugin.main.CCraft;

/**
 *
 * @author Chingo
 */
public class NPCListener {
    private final CCraft ccraft;
    
    public NPCListener(CCraft ccraft) {
        this.ccraft = ccraft;
    }

    @EventHandler
    public void onNPCDeathEvent(NPCDeathEvent nde) {
        NPC npc = nde.getNPC();
        Player owner = ccraft.getServer().getPlayer(npc.getTrait(Owner.class).getOwner());
        if (owner != null && owner.isOnline()) {
            owner.sendMessage(npc.getName() + ": has died");
        }

        final int npcDeathOption = ccraft.getConfig().getInt("events.npc.death");
        switch (npcDeathOption) {
            case 0: // NPC respawns at owner's bed location or the spawn
                if (npc.hasTrait(SentryTrait.class)) {
                    npc.getTrait(SentryTrait.class).getInstance().setGuardTarget(null, true);
                }
                if (owner != null && owner.getBedSpawnLocation() != null) {
                    npc.spawn(owner.getBedSpawnLocation());
                } else if (owner != null && owner.getBedSpawnLocation() == null) {
                    npc.spawn(owner.getWorld().getSpawnLocation());
                }
                break;
            case 1:

        }
    }
}
