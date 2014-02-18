/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.worker.jobs.scout;

import java.util.HashMap;
import java.util.HashSet;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Material;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public class FieldPaper {

    private final Location start;
    private final NPC writer;

    private final HashSet<ChunkLocation> debug;
    // 
    private final HashMap<ChunkLocation, FieldData> fields;

    public FieldPaper(Location start, NPC writer) {
        this.start = start;
        this.debug = new HashSet<>();
        this.fields = new HashMap<>(16);
        this.writer = writer;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 128; y++) {
                    start.getChunk().getBlock(x, y, z).setType(Material.DIAMOND_BLOCK);
                }
            }
        }

        //TODO REMOVE DEBUG
        for (int x = 0; x < 128; x += 16) {
            for (int z = 0; z < 128; z += 16) {
                debug.add(new ChunkLocation(start.clone().add(x, 0, z)));
            }
        }

        Debug.info("**EXPECTED**");
        for (ChunkLocation cl : debug) {
            Debug.info(cl.toString());
            Location l = new Location(writer.getEntity().getWorld(), cl.x, (writer.getEntity().getLocation().getBlockY() + 10), cl.z);
            l.getBlock().setType(Material.GLOWSTONE);
        }
        Debug.info("************");
    }

    /**
     * Writes data to the field paper
     *
     * @param chunkloc The location of the chunk where Y coordinate is irrelevant
     * @param data The data gathered from that chunk
     */
    public void write(Location chunkloc, FieldData data) {

    }

}
