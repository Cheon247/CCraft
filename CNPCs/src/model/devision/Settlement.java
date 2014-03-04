/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.devision;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import model.profession.devision.settlement.SettlementLeader;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Town, City, Village, Fort, Hideout or any other kind of subdevision of a faction
 *
 * @author Chingo
 */
public abstract class Settlement {
  private Player owner;
  private final Location location;
  private final UUID id;
  private final HashMap<String, Set<Peasant>> inhabitants;

  public Settlement(Location location, NPC leader, Player owner) {
    if(!leader.hasTrait(Owner.class)) throw new IllegalArgumentException("npc must have an owner");
    this.location = location;
    this.id = UUID.randomUUID();
    this.inhabitants = new HashMap<>();
    this.owner = owner;
    
    leader.setName("Settlement Leader - " + leader.getName());
    Peasant sl = new Peasant(leader, this, new SettlementLeader(leader));
    this.addPeasant(sl);
  }
  
  public Player getOwner() {
    return owner;
  }
  
  public void setOwner(Player newOwner) {
    this.owner = newOwner;
  }
  
  public final boolean addPeasant(Peasant peasant) {
    if(peasant.getSettlement() != this) return false;
    String profession = peasant.getProfession().getName();
    if(inhabitants.get(profession) == null) {
      inhabitants.put(profession, new HashSet<Peasant>());
      return inhabitants.get(profession).add(peasant);
    } else {
      return inhabitants.get(profession).add(peasant);
    }
  }
  

  public UUID getId() {
    return id;
  }

  public Location getLocation() {
    return location;
  }

}
