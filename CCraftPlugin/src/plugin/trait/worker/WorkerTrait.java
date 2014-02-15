/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.worker;

import java.util.TreeSet;
import plugin.event.worker.WorkerInCombatEvent;
import net.citizensnpcs.api.ai.goals.MoveToGoal;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import plugin.event.worker.WorkerStateChangeEvent;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public class WorkerTrait extends Trait {

    @Persist
    private SafeLocation home;

    @Persist
    private WorkerState currentState;
    @Persist
    private TreeSet<SafeLocation> safeLocations;
    
    
    private WorkerJob myJob;
    
    @Persist
    private String myJobName; 

    public WorkerTrait() {
        super("worker");
        safeLocations = new TreeSet<>();
    }
    
    
    @Override
    public void onSpawn() {
        forgetOverflowingAmount();
        if(myJob != null) {
            myJob.takeDefaultAction();
        }
    }
    
    public final WorkerJob getJob() {
        return myJob;
    }
    
    public final void setJob(final WorkerJob myJob) {
        this.myJob = myJob;
        this.myJobName = myJob.getName();
        if(this.myJob != null) {
            this.myJob.takeDefaultAction();
            Debug.info(getNPC().getName() + ": i have become a " + myJob.getName());
        }
        setCurrentState(WorkerState.IDLE);
    }

    private void forgetOverflowingAmount() {
        int limit = Bukkit.getPluginManager().getPlugin("CCraft").getConfig().getInt("limits.safelocation_limit");
        if (safeLocations.size() > limit && limit != 0) {
            Debug.info("safelocation limit was reached, safelocations with the highest distance");
            safeLocations = new TreeSet<>(safeLocations);
            while (safeLocations.size() > limit && safeLocations.size() > 1) {
                safeLocations.pollLast(); // remove the highest element
            }
        }

    }

    public final boolean addSafeLocation(Location location) {
        if (Bukkit.getPluginManager().isPluginEnabled("CCraft")) {
            throw new AssertionError("CCraft wasn't enabled");
        }
        int limit = Bukkit.getPluginManager().getPlugin("CCraft").getConfig().getInt("limits.safelocation_limit");

        if (limit != 0 && limit == safeLocations.size()) {
            Debug.info("Safelocation limit reached for " + npc.getName());
            return false; // LIMIT REACHED!
        }

        boolean added = safeLocations.add(new SafeLocation(location, npc));
        Debug.info(npc.getName() + ": added safelocation was " + added);
        return added;
    }

    public final boolean removeSafeLocation(Location location) {
        boolean removed = safeLocations.remove(new SafeLocation(location, npc));
        Debug.info(npc.getName() + ": remove safelocation was " + removed);
        return removed;
    }

    public final void clearSafeLocations() {
        Debug.info(npc.getName() + ": clearing safelocations");
        safeLocations.clear();
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
            target = new SafeLocation(getOwner().getBedSpawnLocation(), npc);
        }
        Debug.info(npc.getName() + ": closest safelocation was " + target);
        return target;
    }

    

    /**
     * An npc is retreating when it has an attacker, the NPC becomes in fleeing state and will
     * ignore all other goals
     *
     * @param attacker
     */
    public void goToClosestRetreatLocation(Entity attacker) {
        final WorkerTrait wt = npc.getTrait(WorkerTrait.class);
        Location retreat = wt.getClosestSafeLocation();
        if (retreat == null) {
            setCurrentState(WorkerState.FLEEING);
            wt.getOwner().sendMessage(getName() + ": i'm fleeing!");
            Bukkit.getPluginManager().callEvent(new WorkerInCombatEvent(npc, attacker));
            return;
        }

        if (retreat.equals(wt.getOwner().getBedSpawnLocation())) {
            if (wt.getOwner().isOnline()) {
                wt.getOwner().sendMessage(this.getName() + ": i am attacked and heading for your home!");
            }
        }
        MoveToGoal retreatGoal = new MoveToGoal(this.getNPC(), retreat);
        npc.getDefaultGoalController().addGoal(retreatGoal, WorkerGoalPriority.TOO_DAMN_HIGH);
        npc.getTrait(WorkerTrait.class).setCurrentState(WorkerState.RETREATING);
    }

    public boolean goToClosestSafeLocation() {
        final WorkerTrait wt = npc.getTrait(WorkerTrait.class);
        Location safe = wt.getClosestSafeLocation();
        if (safe == null) {
            return false;
        }
        MoveToGoal retreatGoal = new MoveToGoal(this.getNPC(), safe);
        npc.getDefaultGoalController().addGoal(retreatGoal, WorkerGoalPriority.VERY_HIGH);
        npc.getTrait(WorkerTrait.class).setCurrentState(WorkerState.ASSIGNED_MOVE_BY_PLAYER);

        return true;
    }

    public final WorkerState getCurrentState() {
        return currentState;
    }

    public final void setCurrentState(WorkerState newState) {
        this.currentState = newState;
        Bukkit.getPluginManager().callEvent(new WorkerStateChangeEvent(npc));
        Debug.info(getNPC().getName() + ": " + getCurrentState().name());
        if(myJob != null) myJob.onStateChanged();
    }

    public final void setHome(Location location) {
        if (home != null && safeLocations.contains(home)) {
            safeLocations.remove(home);
        }
        this.home = new SafeLocation(location, npc);
        safeLocations.add(home);
    }

    public final Location getHome() {
        return home;
    }

    public final Player getOwner() {
        if (!getNPC().hasTrait(Owner.class)) {
            throw new IllegalStateException("no owner");
        }
        return getNPC().getEntity().getServer().getPlayer(getNPC().getTrait(Owner.class).getOwner());
    }

    public void setHome(SafeLocation home) {
        this.home = home;
    }

    public TreeSet<SafeLocation> getSafeLocations() {
        return safeLocations;
    }

    public void setSafeLocations(TreeSet<SafeLocation> safeLocations) {
        this.safeLocations = safeLocations;
    }

    
}
