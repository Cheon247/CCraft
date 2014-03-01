/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hierarchy.devisions.urban;

import model.hierarchy.devisions.entity.MunicipalityLeader;
import org.bukkit.Location;

/**
 * A Town belongs to a faction, and may get conquered by other factions
 * @author Chingo
 */
public class Town extends Municipality {
    private String name;
    
    private MunicipalityLeader villageElder;

    public Town(String name, Faction faction, Location center) {
        super(faction, center);
    }
}
