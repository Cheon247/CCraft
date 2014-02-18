/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.worker.worker;

import java.util.TreeSet;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import plugin.event.worker.WorkerStateChangeEvent;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public class Worker {

    private final NPC worker;
    private Job myJob;

    private WorkerState currentState;
    private TreeSet<SafeLocation> safeLocations;
    private SafeLocation home;
    
    

    Worker(NPC worker, String job) {
        this.myJob = JobFactory.getJob(job);
        this.worker = worker;
    }
    
    public Job getJob() {
        return myJob;
    }
    
    public void setJob(String job) {
        myJob = JobFactory.getJob(job);
    }

    public final WorkerState getCurrentState() {
        return currentState;
    }

    public final void setCurrentState(WorkerState newState) {
        this.currentState = newState;
        Bukkit.getPluginManager().callEvent(new WorkerStateChangeEvent(worker));
        Debug.info(worker.getName() + ": " + getCurrentState().name());
        if (myJob != null) {
            myJob.onStateChanged();
        }
    }

    public final void setHome(Location location) {
        if (home != null && safeLocations.contains(home)) {
            safeLocations.remove(home);
        }
        this.home = new SafeLocation(location, worker);
        safeLocations.add(home);
    }

    public final SafeLocation getHome() {
        return home;
    }

    public TreeSet<SafeLocation> getSafeLocations() {
        return safeLocations;
    }

    public void setSafeLocations(TreeSet<SafeLocation> safeLocations) {
        this.safeLocations = safeLocations;
    }

    public final Player getOwner() {
        if (!worker.hasTrait(Owner.class)) {
            throw new IllegalStateException("no owner");
        }
        return worker.getEntity().getServer().getPlayer(worker.getTrait(Owner.class).getOwner());
    }

    public final Location getClosestSafeLocation() {
        SafeLocation target = null;

        if (!safeLocations.isEmpty()) {
            safeLocations = new TreeSet<>(safeLocations); // performs insertion sort
            target = safeLocations.first();     // first element is closest
        }

        if (home != null && (target == null || target.compareTo(home) == 1)) {
            target = home;
        } else if (getOwner().getBedSpawnLocation() != null && (target == null || target.compareTo(getOwner().getBedSpawnLocation()) == 1)) {
            target = new SafeLocation(getOwner().getBedSpawnLocation(), worker);
        }
        Debug.info(worker.getName() + ": closest safelocation was " + target);
        return target;
    }

    public final boolean addSafeLocation(Location location) {
        if (Bukkit.getPluginManager().isPluginEnabled("CCraft")) {
            throw new AssertionError("CCraft wasn't enabled");
        }
        int limit = Bukkit.getPluginManager().getPlugin("CCraft").getConfig().getInt("limits.safelocation_limit");

        if (limit != 0 && limit == safeLocations.size()) {
            Debug.info("Safelocation limit reached for " + worker.getName());
            return false; // LIMIT REACHED!
        }

        boolean added = safeLocations.add(new SafeLocation(location, worker));
        Debug.info(worker.getName() + ": added safelocation was " + added);
        return added;
    }

    public final boolean removeSafeLocation(Location location) {
        boolean removed = safeLocations.remove(new SafeLocation(location, worker));
        Debug.info(worker.getName() + ": remove safelocation was " + removed);
        return removed;
    }

    public final void clearSafeLocations() {
        Debug.info(worker.getName() + ": clearing safelocations");
        safeLocations.clear();
    }

   
}
