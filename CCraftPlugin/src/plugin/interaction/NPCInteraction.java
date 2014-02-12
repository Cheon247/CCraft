/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.interaction;

import net.aufdemrand.sentry.SentryInstance;
import net.aufdemrand.sentry.SentryTrait;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.entity.Player;

/**
 *
 * @author Chingo
 */
public class NPCInteraction {
    
    public final void makeOwner(final Player commander, final NPC npc) {
        Owner ownerTrait = npc.getTrait(Owner.class);
        String ownerName = commander.getPlayerListName();
        
        if(ownerTrait.isOwnedBy(commander)){
            commander.sendMessage("You already own this npc");
            return;
        }
        
        ownerTrait.setOwner(ownerName);
    }
    

    
    
    
}
