/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hierarchy;

/**
 * A Town belongs to a faction, and may get conquered by other factions
 * @author Chingo
 */
public class Town extends Municipality {
    private String name;

    public Town(String name, Faction faction) {
        super(faction);
    }
}
