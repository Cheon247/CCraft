/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.worker.worker;

import net.citizensnpcs.api.npc.NPC;
import plugin.event.world.TIME;

/**
 *
 * @author Chingo
 */
public abstract class WorkerJob {
    protected final NPC npc;
    protected final String name;
    protected boolean avoidingCombat = true;
    protected final WorkerTrait wt;

    public WorkerJob(String jobName, NPC npc) {
        this.name = jobName;
        this.npc = npc;
        this.wt = npc.getTrait(WorkerTrait.class);
    }

    protected NPC getNPC() {
        return npc;
    }

    public String getName() {
        return name;
    }

    
    /**
     * Will take the defaultAction according to the TIME
     */
    public final void takeDefaultAction() {
        TIME currentTime = TIME.serverTicksToTIME(npc.getEntity().getWorld().getTime());
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
    
    
    protected abstract void onStateChanged();
        
    
    
    
}
