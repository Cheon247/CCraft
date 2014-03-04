/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.profession.devision.settlement;

import model.profession.Profession;
import model.profession.devision.contractor.scout.ScoutContractor;
import net.citizensnpcs.api.npc.NPC;

/**
 *
 * @author Chingo
 */
public class SettlementLeader extends Profession {
  private final ScoutContractor contractor;
  
  public SettlementLeader(NPC npc) {
    super("Settlement Leader", npc, true);
    this.contractor = new ScoutContractor(npc);
  }

  
  

}
