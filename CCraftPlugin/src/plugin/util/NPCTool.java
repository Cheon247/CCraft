/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.util;

import java.util.ArrayList;
import java.util.List;
import net.aufdemrand.sentry.SentryInstance;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Owner;
import net.citizensnpcs.npc.NPCSelector;
import org.bukkit.entity.Player;
import plugin.main.CCraft;
import plugin.trait.worker.WorkerTrait;

/**
 *
 * @author Chingo
 */
public class NPCTool {

    private final NPCRegistry npcRegistry;
    private final NPCSelector npcSelector;

    public NPCTool(CCraft ccraft) {
        this.npcRegistry = CitizensAPI.getNPCRegistry();
        Citizens citizens = (Citizens) ccraft.getServer().getPluginManager().getPlugin("Citizens");
        npcSelector = citizens.getNPCSelector();
    }

    public List<NPC> getNPCS() {
        List<NPC> npcs = new ArrayList<>();
        for (NPC npc : npcRegistry.sorted()) {
            npcs.add(npc);
        }
        return npcs;
    }

    public List<NPC> getNPCS(Player owner) {
        List<NPC> npcs = new ArrayList<>();
        for (NPC npc : npcRegistry.sorted()) {
            if (npc.getTrait(Owner.class).isOwnedBy(owner)) {
                npcs.add(npc);
            }
        }
        return npcs;
    }
    
    public List<NPC> getWorkers() {
        List<NPC> npcs = new ArrayList<>();
        for (NPC npc : npcRegistry.sorted()) {
            if (npc.hasTrait(WorkerTrait.class)) {
                npcs.add(npc);
            }
        }
        return npcs;
    }

    public List<NPC> getSentries(Player owner) {
        List<NPC> npcs = new ArrayList<>();
        for (NPC npc : npcRegistry.sorted()) {
            if (npc.hasTrait(SentryTrait.class)) {
                npcs.add(npc);
            }
        }
        return npcs;
    }
    

    /**
     * Dissmisses the sentries that were guarding this target
     */
    public void dissmissSentries() {
        for (NPC npc : npcRegistry.sorted()) {
            if (npc.hasTrait(SentryTrait.class)) {
                SentryInstance si = npc.getTrait(SentryTrait.class).getInstance();
                npc.getTrait(SentryTrait.class).getInstance().setGuardTarget(null, true);
            }
        }

    }

    public List<NPC> getBodyGuard(Player target) {
        List<NPC> npcs = new ArrayList<>();
        for (NPC npc : npcRegistry.sorted()) {
            if (npc.hasTrait(SentryTrait.class)) {
                SentryInstance si = npc.getTrait(SentryTrait.class).getInstance();
                if (si.getGuardTarget() == target) {
                    npcs.add(npc);
                }
            }
        }
        return npcs;
    }

    public void selectNPC(Player owner, NPC selected) {
        npcSelector.select(owner, selected);
    }
    
    

}
