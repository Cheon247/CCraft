/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.hierarchy;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Chingo
 */
public class Kingdom {

    private final String id;
    private final String name;
    private final Universe universe;
    private final HashMap<String, Faction> factions;

    public Kingdom(Universe universe, String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.factions = new HashMap<>();
        this.universe = universe;
    }

    public String getName() {
        return name;
    }
    
    public Universe getUniverse() {
        return universe;
    }

    boolean removeFaction(Faction faction) {
        return this.removeFaction(faction);
    }

    boolean addFaction(Faction faction) {
        if (factions.containsKey(faction.getId())) {
            return false;
        } else {
            factions.put(faction.getId(), faction);
            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Kingdom)) {
            return false;
        }
        Kingdom o = (Kingdom) other;
        if (o == this) {
            return true;
        }
        return this.name.equals(o.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

}
