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
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import plugin.main.CCraft;
import plugin.util.NPCTool;

/**
 *
 * @author Chingo
 */
public class NPCOwnerListener implements Listener {

    private final CCraft ccraft;
    private final NPCTool npcTool;

    public NPCOwnerListener(CCraft ccraft) {
        this.ccraft = ccraft;
        this.npcTool = new NPCTool(ccraft);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent pde) {
        Player player = pde.getEntity();
        for (NPC guard : npcTool.getBodyGuard(player)) {
            if (player.getBedSpawnLocation() != null) {
                guard.teleport(player.getBedSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            } else if (player.getLocation() != null) {
                guard.teleport(player.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            } else {
                guard.teleport(player.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent pte) {
        Player player = pte.getPlayer();
        for (NPC guard : npcTool.getBodyGuard(player)) {
            guard.teleport(player.getLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pqe) {
        Player player = pqe.getPlayer();
        for (NPC npc : npcTool.getNPCS(player)) {
            npc.spawn(npc.getStoredLocation());
        }

        if (!npcTool.getSentries(player).isEmpty()) {
            ccraft.getServer().broadcastMessage(player.getPlayerListName() + " has returned and took his people with him");
        }
    }

    @EventHandler
    public void onPlayerOffline(PlayerQuitEvent pqe) {
        Player player = pqe.getPlayer();
        for (NPC guard : npcTool.getSentries(player)) {
            guard.despawn();
        }

        if (!npcTool.getSentries(player).isEmpty()) {
            ccraft.getServer().broadcastMessage(player.getPlayerListName() + " has left and took his soldiers with him");
        }
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
