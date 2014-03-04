/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.profession.scout;

import model.profession.Profession;
import model.profession.devision.contractor.scout.ScoutContractor;
import model.profession.devision.contractor.scout.FieldTree;
import net.citizensnpcs.api.npc.NPC;

/**
 *
 * @author Chingo
 */
public class Scout extends Profession {
  
  
  private ScoutContractor contractor;

  public Scout(String name, NPC npc, boolean isUnique) {
    super(name, npc, isUnique);
  }

  public boolean dismiss(ScoutContractor contractor) {
    if(this.contractor.equals(contractor)) {
      this.contractor = null;
      return true;
    } 
    return false;
  }

  public boolean assign(ScoutContractor contractor, FieldTree field) {
    if(!this.contractor.equals(contractor) || contractor == null) {
      Assignment assignment = new Assignment(field);
      return true;
    } 
    return false;
  }

  public ScoutContractor getContractor() {
    return contractor;
  }
  
  

  
  private class Assignment {
    private final int CHUNKSIZE = 16;
    
    public Assignment(FieldTree field) {
      FieldTree f = new FieldTree(field.getX(), field.getZ(), CHUNKSIZE, field.getDimension());
    }
    
  }

  
}
