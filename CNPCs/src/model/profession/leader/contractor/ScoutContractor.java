/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.profession.leader.contractor;

import model.devision.Settlement;
import model.profession.leader.settlement.FieldTree;
import model.profession.scout.Scout;

/**
 *
 * @author Chingo
 */
public class ScoutContractor {
  private FieldTree woodExploration;
  private final int MAX_SEARCH_SIZE = 2048;
  private final int MIN_DIMENSION = 128;
  
  public ScoutContractor(Settlement settlement) {
    int woodDimension = 512;
    
    
    int x = settlement.getLocation().getBlockX();
    int z = settlement.getLocation().getBlockZ();
    woodExploration = new FieldTree(x-woodDimension, z-woodDimension, MIN_DIMENSION, woodDimension);
  }

  public void assign(Scout scout) {
    if(woodExploration == null) {
      scout.dismiss();
    } else if(!woodExploration.hasFreeDimension(MIN_DIMENSION) && woodExploration.getDimension() == MAX_SEARCH_SIZE) {
      woodExploration = null;
      scout.dismiss();
    } 
  }
  

  
}
