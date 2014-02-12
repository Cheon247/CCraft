/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.main;

import org.bukkit.Server;
import plugin.recipe.EggRecipes;

/**
 *
 * @author Chingo
 */
class RecipeLoader {
    
    static void load(CCraft plugin) {
        Server server = plugin.getServer();
        for(EggRecipes egg : EggRecipes.values()){
            server.addRecipe(egg.getRecipe());
        }
    }
    
}
