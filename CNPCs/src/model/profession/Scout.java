/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.profession;

import net.citizensnpcs.api.npc.NPC;

/**
 *
 * @author Christian
 */
public class Scout extends Profession {

  public Scout(NPC npc) {
    super("scout", npc);
  }

  @Override
  public void handleCustomer(Profession customer) {
    
  }

  
}
