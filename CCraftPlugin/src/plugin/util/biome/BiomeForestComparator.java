/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.util.biome;

import java.util.Comparator;
import org.bukkit.Location;
import org.bukkit.block.Biome;

/**
 *
 * @author Chingo
 */
public class BiomeForestComparator implements Comparator<Biome>{

    /**
     * Determines which biome has a higher forest score.
     * @param a This biome 
     * @param b The other biome
     * @return Returns 1 if Biome a has a higher score than Biome b, 
     * returns -1 if a has lower score than Biome b, 
     * returns 0 if Both biomes have an equal forestscore
     */
    @Override
    public int compare(Biome a, Biome b) {
        if(BiomeUtil.getForestScore(a) > 
                BiomeUtil.getForestScore(b)){
            return 1;
        } else if (BiomeUtil.getForestScore(a) 
                == BiomeUtil.getForestScore(b)){
            return 0;
        }
        return -1;
    }
}
