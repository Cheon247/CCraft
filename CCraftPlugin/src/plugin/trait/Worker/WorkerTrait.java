/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.Worker;

import plugin.event.worker.WorkerInCombatEvent;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Arc;
import net.citizensnpcs.api.ai.goals.MoveToGoal;
import net.citizensnpcs.api.ai.tree.Behavior;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import plugin.event.worker.WorkerStateChangeEvent;

/**
 *
 * @author Chingo
 */
public abstract class WorkerTrait extends Trait {

    private Location home;

    public enum WorkerState {
        SLEEPING,
        IDLE,
        GOING_TO_WORK,
        WORKING,
        GOING_HOME,
        IN_COMBAT,
        FLEEING
    }

    public WorkerState currentState;

    private final List<Location> safeLocations;

    public final Player getOwner() {
        if (!getNPC().hasTrait(Owner.class)) {
            throw new IllegalStateException("no owner");
        }
        return getNPC().getEntity().getServer().getPlayer(getNPC().getTrait(Owner.class).getOwner());
    }

    public WorkerTrait() {
        super("worker");
        safeLocations = new ArrayList<>();
        if (home != null) {
            safeLocations.add(home);
        }
        if (getOwner().getBedSpawnLocation() != null) {
            safeLocations.add(getOwner().getBedSpawnLocation());
        }
    }

    public boolean addSafeLocation(Location location) {
        if (safeLocations.contains(location) || location.equals(getOwner().getBedSpawnLocation())) {
            return false;
        }
        safeLocations.add(location);
        return true;
    }

    public boolean removeSafeLocation(Location location) {
        if(location.equals(getOwner().getBedSpawnLocation())) return false;
        return safeLocations.remove(location);
    }

    public final Location getClosestSafeLocation() {

        Location npcLocation = npc.getEntity().getLocation();
        if (safeLocations.isEmpty()) {
            return null;
        }
        if (safeLocations.size() == 1) {
            return safeLocations.get(0); // only one location...
        }
        Location currentTarget = safeLocations.get(0);

        for (int i = 1; i < safeLocations.size(); i++) {
            if (npcLocation.distance(safeLocations.get(i)) < npcLocation.distance(currentTarget)) {
                currentTarget = safeLocations.get(i);
            }
        }
        if(npcLocation.distance(getOwner().getBedSpawnLocation()) < npcLocation.distance(currentTarget))
            currentTarget = getOwner().getBedSpawnLocation();
        
        return currentTarget;
    }

    public void setHome(Location location) {
        if (home != null && safeLocations.contains(home)) {
            safeLocations.remove(home);
        }
        this.home = location;
        safeLocations.add(home);
    }


    public Location getHome() {
        return home;
    }

    @Override
    public void onAttach() {
        super.onAttach();

    }

    @Override
    public void onSpawn() {
        setState(WorkerState.IDLE);

        if (isNight()) {
            setState(WorkerState.GOING_HOME);

        }

    }

    public final void setState(WorkerState newState) {
        this.currentState = newState;
        Bukkit.getPluginManager().callEvent(new WorkerStateChangeEvent(npc));
    }

    public boolean isNight() {
        return (getNPC().getEntity().getWorld().getTime() > 12500);
    }

    public void goToClosestRetreatLocation(Entity attacker) {
        final WorkerTrait wt = npc.getTrait(WorkerTrait.class);
        Location retreat = wt.getClosestSafeLocation();
        if (retreat == null) {
            npc.getTrait(WorkerTrait.class).currentState = WorkerState.IN_COMBAT;
            wt.getOwner().sendMessage(getName() + ": i'm fighting for my life!");
            Bukkit.getPluginManager().callEvent(new WorkerInCombatEvent(npc, attacker));
            return;
        }

        if (retreat.equals(wt.getOwner().getBedSpawnLocation())) {
            if (wt.getOwner().isOnline()) {
                wt.getOwner().sendMessage(this.getName() + ": i am attacked and heading for your home!");
            }
        }
        MoveToGoal retreatGoal = new MoveToGoal(this.getNPC(), retreat);
        npc.getDefaultGoalController().addGoal(retreatGoal, 100);
        npc.getTrait(WorkerTrait.class).setState(WorkerState.FLEEING);
    }

    public boolean goToClosestSafeLocation() {
        final WorkerTrait wt = npc.getTrait(WorkerTrait.class);
        Location safe = wt.getClosestSafeLocation();
        if (safe == null) {
            return false;
        }
        MoveToGoal retreatGoal = new MoveToGoal(this.getNPC(), safe);
        npc.getDefaultGoalController().addGoal(retreatGoal, 100);
        npc.getTrait(WorkerTrait.class).setState(WorkerState.FLEEING);
        return true;
    }

}
