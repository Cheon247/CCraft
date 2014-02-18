/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.util;

import org.bukkit.Chunk;

/**
 *
 * @author Chingo
 */
public class Comp {
    
    public static boolean compareChunks(Chunk a, Chunk b) {
        return a.getX() == b.getX() && a.getZ() == b.getZ();
    }
}
