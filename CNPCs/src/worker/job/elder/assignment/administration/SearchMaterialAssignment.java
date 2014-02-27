/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package worker.job.elder.assignment.administration;

import java.util.Set;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;

/**
 *
 * @author Chingo
 */
public class SearchMaterialAssignment extends SearchAssignment{
    
    private final Set<Material> materials;

    public SearchMaterialAssignment(NPC employer, int startX, int startZ, int dimension, Set<Material> materials) {
        super(employer, startX, startZ, dimension);
        this.materials = materials;
    }
    
    public final Set<Material> getMaterials() {
        return materials;
    }

}
