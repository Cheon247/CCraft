/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.recipe;

import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 *
 * @author Chingo
 */
public class CShapedRecipe {
    private final ShapedRecipe recipe;
    
    public CShapedRecipe(ItemStack result, String displayname) {
        ItemMeta im = result.getItemMeta();
        im.setDisplayName(displayname);
        result.setItemMeta(im);
        this.recipe = new ShapedRecipe(result);
    }
    
    public CShapedRecipe(ItemStack result, String displayname, List<String> lore) {
        ItemMeta im = result.getItemMeta();
        im.setLore(lore);
        im.setDisplayName(displayname);
        result.setItemMeta(im);
        this.recipe = new ShapedRecipe(result);
    }

    public final CShapedRecipe shape(String... shape) {
        this.recipe.shape(shape);
        return this;
    }

    public final CShapedRecipe setIngredient(char key, MaterialData ingredient) {
        this.recipe.setIngredient(key, ingredient);
        return this;
    }

    public final CShapedRecipe setIngredient(char key, Material ingredient) {
        this.recipe.setIngredient(key, ingredient);
        return this;
    }

    public final Map<Character, ItemStack> CShapedRecipe() {
        return recipe.getIngredientMap();
    }

    public final String[] getShape() {
        return recipe.getShape();
    }

    public final ItemStack getResult() {
        return recipe.getResult();
    }
    
    public final ShapedRecipe getRecipe() {
        return this.recipe;
    }
    
    
    
    
}
