/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.quest;

import net.citizensnpcs.api.npc.NPC;

/**
 *
 * @author Chingo
 */
public class Assignment {

    /**
     * The entity that made the assignment
     */
    private final NPC employer;

    public Assignment(NPC employer) {
        this.employer = employer;
    }

    public NPC getEmployer() {
        return employer;
    }


}
