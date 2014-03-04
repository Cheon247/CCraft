/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.profession.idle;

import model.profession.Profession;
import net.citizensnpcs.api.npc.NPC;

/**
 *
 * @author Chingo
 */
public class IdleProfession extends Profession{

  public IdleProfession(NPC npc, boolean isUnique) {
    super("idle", npc, isUnique);
  }
  
}
