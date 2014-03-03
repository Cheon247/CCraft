/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.trait;

import model.profession.Profession;
import net.citizensnpcs.api.trait.Trait;

/**
 *
 * @author Christian
 */
public class PeasantTrait extends Trait {
  private Profession profession;
  
  public PeasantTrait() {
    super("peasant");
    
  }
}
