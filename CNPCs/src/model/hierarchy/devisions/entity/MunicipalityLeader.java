/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hierarchy.devisions.entity;

import model.profession.Profession;
import model.profession.Scout;
import net.citizensnpcs.api.npc.NPC;
import util.debug.Debug;

/**
 *
 * @author Chingo
 */
public class MunicipalityLeader extends Profession {

  public MunicipalityLeader(String name, NPC npc) {
    super(name, npc);
  }

  @Override
  protected void handleCustomer(Profession customer) {
    if(customer instanceof Scout) {
      
    } else {
      Debug.info(customer.getName() + ": not supported ");
    }
  }
  
  private void handleScout(Scout scout) {
    
  }
}
