/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.devision;

import model.profession.Profession;
import model.profession.idle.IdleProfession;
import net.citizensnpcs.api.npc.NPC;

/**
 *
 * @author Chingo
 */
public class Peasant {

  private final NPC npc;
  private final Profession profession;
  private Settlement settlement;

  public Peasant(NPC npc, Settlement settlement) {
    this.npc = npc;
    this.settlement = settlement;
    this.profession = new IdleProfession(npc, false);
  }

  public Peasant(NPC npc, Settlement settlement, Profession profession) {
    this.npc = npc;
    this.settlement = settlement;
    this.profession = profession;
  }

  public NPC getNpc() {
    return npc;
  }

  public Settlement getSettlement() {
    return settlement;
  }

  public void setSettlement(Settlement settlement) {
    this.settlement = settlement;
  }

  public Profession getProfession() {
    return profession;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Peasant))  {
      return false;
    } else {
      Peasant p = (Peasant) o;
      return p.getNpc().getId() == getNpc().getId();
    }
  }

}
