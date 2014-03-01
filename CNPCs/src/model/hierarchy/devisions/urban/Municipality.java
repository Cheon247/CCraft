/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.hierarchy.devisions.urban;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Location;

/**
 * Town, City, Village, Fort, Hideout or any other kind of subdevision of a faction
 *
 * @author Chingo
 */
public abstract class Municipality {

  private final Location location;
  private final String id;
  private Faction faction;

  public Municipality(Faction faction, Location location) {
    this.faction = faction;
    this.location = location;
    this.id = UUID.randomUUID().toString();
  }

  public String getId() {
    return id;
  }

  public Faction getFaction() {
    return faction;
  }

  public Location getLocation() {
    return location;
  }

  public void setFaction(Faction faction) {
    this.faction = faction;
  }

}
