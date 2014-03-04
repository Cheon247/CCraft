/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.profession.devision.contractor.scout;

import model.devision.Peasant;
import model.devision.Settlement;
import model.profession.scout.Scout;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;

/**
 *
 * @author Chingo
 */
public class ScoutContractor {

  private final NPC contractor;
  private FieldTree exploration;
  private final int MAX_SEARCH_SIZE = 4096;
  private final int MIN_DIMENSION = 128;

  public enum STATE {
    NO_EXPLORING,
    EXPLORING,
    ALL_ASSIGNED,
    ALL_EXPLORED
  }

  private STATE status = STATE.NO_EXPLORING;

  public ScoutContractor(NPC contractor) {
    this.contractor = contractor;
  }
  
  public boolean check(FieldTree assignement, Scout scout) {
    if(!scout.getContractor().equals(this)) return false;
    if(assignement.getStatus() != FieldTree.STATUS.COMPLETE) return false;
    return exploration.setField(assignement);
  }

  public boolean assign(Scout scout) {
    if(!scout.getContractor().getNPC().equals(this.getNPC())) {
      return false;
    }
    if (status == STATE.NO_EXPLORING || status == STATE.ALL_ASSIGNED) {
      return false;
    } else {
      FieldTree field = exploration.pollField(MIN_DIMENSION);
      if (field == null && exploration.getDimension() == MAX_SEARCH_SIZE) {
        changeState(STATE.ALL_ASSIGNED);
        return false;
      } else if(field == null && exploration.getDimension() < MAX_SEARCH_SIZE) {
        exploration.grow();
        changeState(STATE.EXPLORING);
        return assign(scout);
      }
      scout.assign(this, field);
      return true;
    } 
  }

  public void changeState(STATE status) {
    ExplorationStatusChanged event = new ExplorationStatusChanged(this, status);
    Bukkit.getPluginManager().callEvent(event);
  }

  public boolean startExploration(Settlement start) {
    if (exploration != null) {
      return false;
    }

    int explorationDimension = 512;
    int x = start.getLocation().getBlockX();
    int z = start.getLocation().getBlockZ();
    exploration = new FieldTree(x - explorationDimension, z - explorationDimension, MIN_DIMENSION, explorationDimension);
    changeState(STATE.EXPLORING);
    return true;
  }

  public void endExploration() {
    status = STATE.NO_EXPLORING;
    exploration = null;
    ExplorationStatusChanged event = new ExplorationStatusChanged(this, status);
    Bukkit.getPluginManager().callEvent(event);
  }

  public NPC getNPC() {
    return contractor;
  }
  
  
  
}
