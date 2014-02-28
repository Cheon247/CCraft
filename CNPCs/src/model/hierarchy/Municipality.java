/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hierarchy;

import java.util.UUID;

/**
 * Town, City, Village, Fort, Hideout or any other kind of subdevision of a faction
 * @author Chingo
 */
public abstract class Municipality {
    
    private final String id;
    private Faction faction;
    
    public Municipality(Faction faction) {
        this.faction = faction;
        this.id = UUID.randomUUID().toString();
    }
    
    public String getId() {
        return id;
    }

    public Faction getFaction() {
        return faction;
    }
    
    

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    
    
    
    
    
    
}
