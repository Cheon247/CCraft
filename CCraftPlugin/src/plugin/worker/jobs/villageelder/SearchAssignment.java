/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.worker.jobs.villageelder;

/**
 *
 * @author Chingo
 */
public class SearchAssignment {
    public static final int MIN_SEARCHFIELD_SIZE = 128;
    
    private final FieldTree administration;
    private final SEARCH_TYPE type;
    
    public enum SEARCH_TYPE {
        WOOD,
        PLANTS, // Melons, Pumpkins, Cocoa Beans, Sugar Cane, 
        ANIMALS,
    }

    public SearchAssignment(int posXStart, int posZStart, SEARCH_TYPE type) {
        this.administration =  new FieldTree(posXStart, posZStart, MIN_SEARCHFIELD_SIZE, MIN_SEARCHFIELD_SIZE*8);
        this.type = type;
    }
    

    
    
   
}
