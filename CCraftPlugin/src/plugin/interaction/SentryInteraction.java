/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.interaction;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.aufdemrand.sentry.SentryInstance;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import plugin.util.Debug;
import plugin.util.Materials;

/**
 *
 * @author Chingo
 */
public class SentryInteraction extends NPCInteraction {


    
    
    public SentryInteraction() {
        super();
    }

    public final void guardTarget(Player commander, Player target, NPC npc) {
        if (npc.hasTrait(SentryTrait.class)) {
            return; // We are done!
        }
        
        if(target == null) {
            commander.sendMessage("No target");
            return;
        }
        
        SentryInstance si = npc.getTrait(SentryTrait.class).getInstance();
        si.setGuardTarget(target.getName(), true);
    }
    
    
    public final void giveEquipment(Player commander, NPC npc, final ItemStack item) {
        if(item == null) {
            Debug.info("item was null");
            return;
        }
        
        Equipment equipTrait = (Equipment) npc.getTrait(Equipment.class);
        Material type = item.getType();
        
        int slot;
        
        if(Materials.isArmor(type)) {
            if(Materials.HELMETS.contains(type)){
                slot = 1;
            } else if (Materials.CHESTPLATES.contains(type)) {
                slot = 2;
            } else if (Materials.LEGGINGS.contains(type)) {
                slot = 3;
            } else {
                slot = 4;
            }
            equipTrait.set(slot, item);
        }
        
    }
    
    public final void targetMonsters(final Player player, final NPC npc) {
        if(!npc.getTrait(Owner.class).isOwnedBy(player))
            player.sendMessage("u are not the owner of this npc");
        
        if(!npc.hasTrait(SentryTrait.class))
            player.sendMessage(npc.getName() + " is not a sentry");
           
        SentryInstance is = npc.getTrait(SentryTrait.class).getInstance();
        if(is.validTargets.contains("ENTITY:MONSTER")){
            player.sendMessage(npc.getName() + ": already targets monsters");
        } else {
            is.validTargets.add("ENTITY:MONSTER");
            is.processTargets();
        }
    }
    
    public final void setStrength(final NPC npc, final int strength) {
        if(!npc.hasTrait(SentryTrait.class)) throw new IllegalArgumentException("npc is not a sentry");
        
        SentryInstance si = npc.getTrait(SentryTrait.class).getInstance();
        si.Strength = strength;
    }
    
    public final void setArmor(final NPC npc, final int armor) {
        if(!npc.hasTrait(SentryTrait.class)) throw new IllegalArgumentException("npc is not a sentry");
        
        SentryInstance si = npc.getTrait(SentryTrait.class).getInstance();
        si.Armor = armor;
    }
    
        /**
     * Makes a sentry from an npc
     * @param guard
     * @param npc
     * @param commander The npc that makes the request. 
     */
    public final void makeSentry(final Player commander, final NPC npc, final boolean guard) {
        if(!npc.getTrait(Owner.class).getOwner().equals(commander.getPlayerListName())){
           commander.sendMessage(npc.getName() + ": " );
        }
        npc.addTrait(SentryTrait.class);
        if(guard){
            SentryInstance si = npc.getTrait(SentryTrait.class).getInstance();
            si.setGuardTarget(commander.getPlayerListName(), true);
        }
    }
    
    

}
