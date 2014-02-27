/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worker.job.elder.assignment.administration;

import model.quest.Assignment;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.LivingEntity;

/**
 *
 * @author Chingo
 */
public abstract class SearchAssignment extends Assignment{
    
    private final int startX;
    private final int startZ;
    private final int dimension; 

    public SearchAssignment(NPC employer, int startX, int startZ, int dimension) {
        super(employer);
        this.startX = startX;
        this.startZ = startZ;
        this.dimension = dimension;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartZ() {
        return startZ;
    }

    public int getDimension() {
        return dimension;
    }
    
    
    
    
}
