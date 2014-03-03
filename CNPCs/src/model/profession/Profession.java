/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.profession;

import net.citizensnpcs.api.npc.NPC;

/**
 *
 * @author Chingo
 */
public abstract class Profession {
  
  

  private final String name;
  

  public Profession(String name, NPC npc) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
  protected abstract void handleCustomer(Profession customer);



}
