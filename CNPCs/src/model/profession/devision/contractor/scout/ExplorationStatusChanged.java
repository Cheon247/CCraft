/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.profession.devision.contractor.scout;

import org.bukkit.event.HandlerList;

/**
 *
 * @author Chingo
 */
public class ExplorationStatusChanged extends ExplorationEvent {

  private final ScoutContractor.STATE status;
  
  ExplorationStatusChanged(ScoutContractor contractor, ScoutContractor.STATE status) {
    super(contractor);
    this.status = status;
  }

  public ScoutContractor.STATE getStatus() {
    return status;
  }
  
  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  private static final HandlerList handlers = new HandlerList();

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
