/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.npc;

import java.util.ArrayList;
import java.util.List;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Owner;
import net.citizensnpcs.npc.NPCSelector;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


/**
 *
 * @author Chingo
 */
public class NPCTool {

    private final NPCRegistry npcRegistry;
    private final NPCSelector npcSelector;

    public NPCTool(JavaPlugin plugin) {
        this.npcRegistry = CitizensAPI.getNPCRegistry();
        Citizens citizens = (Citizens) plugin.getServer().getPluginManager().getPlugin("Citizens");
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
    

    public void selectNPC(Player owner, NPC selected) {
        npcSelector.select(owner, selected);
    }
    
    public NPC getSelected(Player owner) {
        return npcSelector.getSelected(owner);
    }

}
