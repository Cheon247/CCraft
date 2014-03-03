/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.devision;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import model.profession.Profession;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;

/**
 * Town, City, Village, Fort, Hideout or any other kind of subdevision of a faction
 *
 * @author Chingo
 */
public abstract class Settlement {

  private final Location location;
  private final String id;
  private Faction faction;
  private final HashMap<Class <? extends Profession>, Set<NPC>> inhabitants;

  public Settlement(Faction faction, Location location) {
    this.faction = faction;
    this.location = location;
    this.id = UUID.randomUUID().toString();
    this.inhabitants = new HashMap<>();
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
