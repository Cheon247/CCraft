/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.Worker;

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
import plugin.event.world.TIME;
import plugin.util.Debug;

/**
 *
 * @author Chingo
 */
public abstract class WorkerTrait extends Trait {

    @Persist private SafeLocation home;
    @Persist protected boolean avoidingCombat = true;
    @Persist private WorkerState currentState;
    @Persist private TreeSet<SafeLocation> safeLocations;
    



    public WorkerTrait() {
        super("worker");
        safeLocations = new TreeSet<>();
    }

    @Override
    public void onAttach() {
        super.onAttach();

    }

    @Override
    public void onSpawn() {
        forgetOverflowingAmount();
        takeDefaultAction();
    }

    public final boolean addSafeLocation(Location location) {
        if(Bukkit.getPluginManager().isPluginEnabled("CCraft")) throw new AssertionError("CCraft wasn't enabled");
        int limit = Bukkit.getPluginManager().getPlugin("CCraft").getConfig().getInt("limits.safelocation_limit");
        
        if(limit != 0 && limit == safeLocations.size()) {
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
    
    private void forgetOverflowingAmount() {
        if(Bukkit.getPluginManager().isPluginEnabled("CCraft")) throw new AssertionError("CCraft wasn't enabled");
            
        int limit = Bukkit.getPluginManager().getPlugin("CCraft").getConfig().getInt("limits.safelocation_limit");
        if(safeLocations.size() > limit && limit != 0) {
            Debug.info("safelocation limit was reached, safelocations with the highest distance");
            safeLocations = new TreeSet<>(safeLocations);
            while(safeLocations.size() > limit && safeLocations.size() > 1) {
                safeLocations.pollLast(); // remove the highest element
            }
        }   
        
    }

    /**
     * Will take the defaultAction according to the TIME 
     */
    public final void takeDefaultAction() {
        TIME currentTime = TIME.serverTicksToTIME(getNPC().getEntity().getWorld().getTime());
        switch (currentTime) {
            case SUNRISE:
                defaultSunRiseAction();
                break;
            case MORNING:
                defaultMorningAction();
                break;
            case MIDDAY:
                defaultMiddayAction();
                break;
            case DAY:
                defaultDayAction();
                break;
            case LATE_DAY:
                defaultLateDayAction();
                break;
            case SUNSET:
                defaultSunsetAction();
                break;
            case NIGHT:
                defaultNightAction();
                break;
            default:
                throw new UnsupportedOperationException(currentTime + " not supported");
        }
    }

    /**
     * Default action to take on sunrise, also called on npc spawn
     */
    protected abstract void defaultSunRiseAction();

    /**
     * Default action to take when TIME is MORNING, also called on npc spawn
     */
    protected abstract void defaultMorningAction();

    /**
     * Default action to take when TIME is MIDDAY, also called on npc spawn
     */
    protected abstract void defaultMiddayAction();

    /**
     * Default action to take when TIME is DAY, also called on npc spawn
     */
    protected abstract void defaultDayAction();

    /**
     * Default action to take when TIME is LATE_DAY, also called on npc spawn
     */
    protected abstract void defaultLateDayAction();

    /**
     * Default action to take when TIME is SUNSET, also called on npc spawn
     */
    protected abstract void defaultSunsetAction();

    /**
     * Default action to take when TIME is NIGHT, also called on npc spawn
     */
    protected abstract void defaultNightAction();

    /**
     * An npc is retreating when it has an attacker, the NPC becomes in fleeing state and will ignore all other goals
     * @param attacker 
     */
    public void goToClosestRetreatLocation(Entity attacker) {
        final WorkerTrait wt = npc.getTrait(WorkerTrait.class);
        Location retreat = wt.getClosestSafeLocation();
        if (retreat == null) {
            setCurrentState(WorkerState.FLEE);
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
        npc.getTrait(WorkerTrait.class).setCurrentState(WorkerState.RETREAT);
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

    public boolean isAvoidingCombat() {
        return avoidingCombat;
    }

    public void setAvoidingCombat(boolean avoidingCombat) {
        this.avoidingCombat = avoidingCombat;
    }

    public TreeSet<SafeLocation> getSafeLocations() {
        return safeLocations;
    }

    public void setSafeLocations(TreeSet<SafeLocation> safeLocations) {
        this.safeLocations = safeLocations;
    }
        
    
}
