/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.recipe;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 *
 * @author Chingo
 */
public enum EggRecipes {
    NPC_EGG_RECIPE(new CShapedRecipe(new ItemStack(Material.MONSTER_EGG),"NPC EGG")
            .shape("D","D","D")
            .setIngredient('D', Material.DIRT)
            .getRecipe()
    );
   

    private final Recipe recipe;

    private EggRecipes(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}

