/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hierarchy.agency.knowledge;

import model.administration.FieldTree;
import model.hierarchy.agency.Agency;
import model.hierarchy.devisions.entity.MunicipalityLeader;
import model.hierarchy.devisions.urban.Municipality;
import static net.minecraft.server.v1_7_R1.MobEffectList.z;

/**
 * A scout agency will be monitored by the Village Elder or Mayor
 * @author Chingo
 */
public class ScoutAgency extends Agency {
  private final int SCOUT_FIELD_SIZE = 512;
  private final FieldTree map;
  
  

  public ScoutAgency(Municipality municipality) {
    super(municipality, MunicipalityLeader.class, MunicipalityLeader.class); // Townleader is Employer and Stakeholder
    this.map = new FieldTree(municipality.getLocation().getBlockX() - SCOUT_FIELD_SIZE/2, 
            municipality.getLocation().getBlockZ() - SCOUT_FIELD_SIZE/2, 128, SCOUT_FIELD_SIZE);
  }
  
  
}
