/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hierarchy;

import java.util.HashMap;
import java.util.UUID;
import net.minecraft.util.com.google.common.base.Preconditions;

/**
 *
 * @author Chingo
 */
public class Faction {
    
    
    private final Kingdom kingdom;
    private final HashMap<String, Municipality> municipalities;
    private final String name;
    private final String id;
    
    public Faction(String name, Kingdom kingdom) {
        Preconditions.checkNotNull(kingdom);
        Preconditions.checkNotNull(name);
        this.kingdom = kingdom;
        this.name = name;
        this.municipalities = new HashMap<>();
        this.id = UUID.randomUUID().toString();
    }
    
    
    public Kingdom getKingdom() {
        return kingdom;
    }
    
    
    public String getId() {
        return id;
    }

    
    public boolean addMunicipality(Municipality municipality) {
        if(municipalities.containsKey(municipality.getId())) {
            return false;
        } else {
            municipalities.put(municipality.getId(), municipality);
            return true;
        }
    }
    
    
}
