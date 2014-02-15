/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.discoverer;

import java.util.Arrays;
import java.util.HashSet;
import org.bukkit.block.Biome;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public final class ForestScore {

    private static final HashSet<Biome> NO;
    private static final HashSet<Biome> VERY_LOW;
    private static final HashSet<Biome> LOW;
    private static final HashSet<Biome> NORMAL;
    private static final HashSet<Biome> HIGH;
    private static final HashSet<Biome> TOO_DAMN_HIGH;
 
    static {
        NO = new HashSet<>();
        NO.addAll(Arrays.asList(
                Biome.DEEP_OCEAN,
                Biome.OCEAN,
                Biome.FROZEN_OCEAN,
                Biome.SKY,
                Biome.HELL
        ));

        VERY_LOW = new HashSet<>();
        VERY_LOW.addAll(Arrays.asList(
                Biome.DESERT,
                Biome.DESERT_HILLS,
                Biome.DESERT_MOUNTAINS,
                Biome.EXTREME_HILLS,
                Biome.EXTREME_HILLS_MOUNTAINS,
                Biome.EXTREME_HILLS_PLUS,
                Biome.EXTREME_HILLS_PLUS_MOUNTAINS,
                Biome.ICE_MOUNTAINS,
                Biome.ICE_PLAINS,
                Biome.ICE_PLAINS_SPIKES,
                Biome.FROZEN_RIVER,
                Biome.COLD_BEACH,
                Biome.STONE_BEACH,
                Biome.MUSHROOM_ISLAND,
                Biome.MUSHROOM_SHORE,
                Biome.BEACH,
                Biome.MESA,
                Biome.MESA_BRYCE,
                Biome.MESA_PLATEAU,
                Biome.MESA_PLATEAU_FOREST,
                Biome.MESA_PLATEAU_FOREST_MOUNTAINS,
                Biome.MESA_PLATEAU_MOUNTAINS
        ));

        LOW = new HashSet<>();
        LOW.addAll(Arrays.asList(
                Biome.SWAMPLAND,
                Biome.SWAMPLAND_MOUNTAINS,
                Biome.RIVER,
                Biome.SMALL_MOUNTAINS,
                Biome.PLAINS,
                Biome.SUNFLOWER_PLAINS
        ));

        NORMAL = new HashSet<>();
        NORMAL.addAll(Arrays.asList(
                Biome.SAVANNA,
                Biome.SAVANNA_MOUNTAINS,
                Biome.SAVANNA_PLATEAU,
                Biome.SAVANNA_PLATEAU_MOUNTAINS,
                Biome.TAIGA,
                Biome.TAIGA_HILLS,
                Biome.TAIGA_MOUNTAINS,
                Biome.MEGA_TAIGA,
                Biome.MEGA_SPRUCE_TAIGA_HILLS,
                Biome.MEGA_SPRUCE_TAIGA,
                Biome.MEGA_TAIGA_HILLS
        ));
        
        HIGH = new HashSet<>();
        HIGH.addAll(Arrays.asList(
            Biome.BIRCH_FOREST,
            Biome.BIRCH_FOREST_HILLS,
            Biome.BIRCH_FOREST_HILLS_MOUNTAINS,
            Biome.BIRCH_FOREST_MOUNTAINS,
            Biome.FOREST,
            Biome.FLOWER_FOREST,
            Biome.JUNGLE,
            Biome.JUNGLE_EDGE,
            Biome.JUNGLE_EDGE_MOUNTAINS,
            Biome.JUNGLE_HILLS,
            Biome.ROOFED_FOREST_MOUNTAINS
        ));
        
        TOO_DAMN_HIGH = new HashSet<>();
        TOO_DAMN_HIGH.addAll(Arrays.asList(
            Biome.ROOFED_FOREST
        ));
        
        
    }
    
    public static final int NO_SCORE = 0;
    public static final int VERY_LOW_SCORE = 20;
    public static final int LOW_SCORE = 40;
    public static final int NORMAL_SCORE = 60;
    public static final int HIGH_SCORE = 80;
    public static final int TOO_DAMN_HIGH_SCORE = 100;
    

    
    
    public static final int getForestScore(Biome b) {
        if(TOO_DAMN_HIGH.contains(b)) return TOO_DAMN_HIGH_SCORE;
        else if(HIGH.contains(b)) return HIGH_SCORE;
        else if(NORMAL.contains(b)) return NORMAL_SCORE;
        else if (LOW.contains(b)) return LOW_SCORE;
        else if (NO.contains(b)) return NO_SCORE;
        else {
            return VERY_LOW_SCORE;
        }
    }

}
