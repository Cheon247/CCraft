/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hierarchy.devisions.entity;

import net.citizensnpcs.api.npc.NPC;

/**
 * Peasants live in a Municipality
 * @author Chingo
 */
public abstract class Peasant {
    
    private final NPC npc;
    
    public Peasant(NPC npc) {
        this.npc = npc;
    }
    
}
