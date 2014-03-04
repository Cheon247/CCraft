/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.profession.devision.contractor.scout;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Chingo
 */
public class ExplorationContinuedEvent extends Event {
    private final ScoutContractor contractor;
  
    ExplorationContinuedEvent(ScoutContractor contractor) {
      this.contractor = contractor;
    }
    
    public ScoutContractor getContractor() {
      return contractor;
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
