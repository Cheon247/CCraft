/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hierarchy.agency;

import model.hierarchy.devisions.urban.Municipality;
import model.profession.Profession;

/**
 * An agency make sure that parties with the same points of interest will share information,
 * an agency may also assign jobs to stakeholders, telling where a scout should scout or which trees a lumberer
 * should lumber.
 * @author Chingo
 */
public abstract class Agency {
  private final Municipality municipality;
  private final Class stakeholder;
  private final Class employer;
  
  public enum Type {
    /**
     * Military Agencies share info about enemy positions, patrol info, planned missions etc
     */
    MILITARY, 
    /**
     * Knowledge agencies share info about:
     *  - What they are researching at the moment
     *  - What they have researched and stored in books
     *  - Envirionmnetal knowledge (resources like wood, wheat, sugar canes)
     * Knowledge agencies also know which parties are intrested in certain knowledge and will share
     * info with other agency types.      
     * 
     * e.g. a scout produces a report of it's exploration. Economic building wanna know about resources that were found
     * but only the ones they care about. Administration building wanna know what area he has scouted.
     */
    KNOWLEDGE,
    /**
     * Economic agencies or Industrial agencies want to know where to get their resources.
     * e.g. a lumberer wants to know where he could find trees, therefore information of tree location is needed
     * A lumberjack Agency shares info about all trees to all lumberers it also makes sure that two
     * npc won't try to lumber the same tree at the same time. 
     */
    ECONOMY   
  }
  
  public Agency(Municipality municipality, Class<? extends Profession> employer,  Class<? extends Profession> stakeholder) {
    this.municipality = municipality;
    this.stakeholder = stakeholder;
    this.employer = employer;
  }
  
}
