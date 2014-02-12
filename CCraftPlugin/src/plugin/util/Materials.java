/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Material;

/**
 *
 * @author Chingo
 */
public class Materials {

    public static final Set<Material> AXES;
    public static final Set<Material> PICKAXES;
    public static final Set<Material> HOES;
    public static final Set<Material> SPADES;

    public static final Set<Material> BOOTS;
    public static final Set<Material> LEGGINGS;
    public static final Set<Material> CHESTPLATES;
    public static final Set<Material> HELMETS;
    
    public static final Set<Material> SWORDS;

    static {
        // TOOLS
        AXES = new HashSet<>();
        AXES.addAll(Arrays.asList(
                Material.WOOD_AXE,
                Material.STONE_AXE,
                Material.IRON_AXE,
                Material.GOLD_AXE,
                Material.DIAMOND_AXE
        ));
        PICKAXES = new HashSet<>();
        PICKAXES.addAll(Arrays.asList(
                Material.WOOD_PICKAXE,
                Material.STONE_PICKAXE,
                Material.IRON_PICKAXE,
                Material.GOLD_PICKAXE,
                Material.DIAMOND_PICKAXE
        ));
        HOES = new HashSet<>();
        HOES.addAll(Arrays.asList(
                Material.WOOD_HOE,
                Material.STONE_HOE,
                Material.IRON_HOE,
                Material.GOLD_HOE,
                Material.DIAMOND_HOE
        ));
        SPADES = new HashSet<>();
        SPADES.addAll(Arrays.asList(
                Material.WOOD_SPADE,
                Material.STONE_SPADE,
                Material.IRON_SPADE,
                Material.GOLD_SPADE,
                Material.DIAMOND_SPADE
        ));

        // Armor
        BOOTS = new HashSet<>();
        BOOTS.addAll(Arrays.asList(
                Material.CHAINMAIL_BOOTS,
                Material.IRON_BOOTS,
                Material.GOLD_BOOTS,
                Material.DIAMOND_BOOTS
        ));

        LEGGINGS = new HashSet<>();
        LEGGINGS.addAll(Arrays.asList(
                Material.CHAINMAIL_LEGGINGS,
                Material.IRON_LEGGINGS,
                Material.GOLD_LEGGINGS,
                Material.DIAMOND_LEGGINGS
        ));
        CHESTPLATES = new HashSet<>();
        CHESTPLATES.addAll(Arrays.asList(
                Material.CHAINMAIL_CHESTPLATE,
                Material.IRON_CHESTPLATE,
                Material.GOLD_CHESTPLATE,
                Material.DIAMOND_CHESTPLATE
        ));

        HELMETS = new HashSet<>();
        HELMETS.addAll(Arrays.asList(
                Material.CHAINMAIL_HELMET,
                Material.IRON_HELMET,
                Material.GOLD_HELMET,
                Material.DIAMOND_HELMET
        ));
        
        SWORDS = new HashSet<>();
        SWORDS.addAll(Arrays.asList(
                Material.WOOD_SWORD,
                Material.STONE_SWORD,
                Material.IRON_SWORD,
                Material.GOLD_SWORD,
                Material.DIAMOND_SWORD
        ));
                

    }

    public static boolean isTool(Material material) {
        return (AXES.contains(material)
                || PICKAXES.contains(material)
                || HOES.contains(material)
                || SPADES.contains(material));
    }
    
    public static boolean isArmor(Material material) {
        return (HELMETS.contains(material)
                || CHESTPLATES.contains(material)
                || LEGGINGS.contains(material)
                || BOOTS.contains(material));
    }

}
