/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worker.job.elder.assignment.administration;

import model.administration.FieldTree;
import net.citizensnpcs.api.npc.NPC;

/**
 * Keeps track of assignments and their progress
 *
 * @author Chingo
 */
public class AssignmentAdministration {

    private final int MIN_SEARCH_ASSIGNMENT_SIZE = 128;
    private FieldTree resourceMap;

    public AssignmentAdministration(int startX, int startZ) {
        resourceMap = new FieldTree(startX, startZ, MIN_SEARCH_ASSIGNMENT_SIZE, MIN_SEARCH_ASSIGNMENT_SIZE * 4);
    }

    public void save() {
        // Save progress
    }

    public void load() {
        // Store progress
    }

    
    
}
