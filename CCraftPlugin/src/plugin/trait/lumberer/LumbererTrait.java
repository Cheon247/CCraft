/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.trait.lumberer;

import plugin.trait.Worker.WorkerState;
import plugin.trait.Worker.WorkerTrait;

/**
 *
 * @author Chingo
 */
public class LumbererTrait extends WorkerTrait {

    @Override
    protected void defaultSunRiseAction() {
        if (getCurrentState() == WorkerState.SLEEPING) {
            setCurrentState(WorkerState.IDLE);
        }
    }

    @Override
    protected void defaultMorningAction() {
        if (getCurrentState() == WorkerState.IDLE) {
            setCurrentState(WorkerState.GOING_TO_WORK);
        }
    }

    @Override
    protected void defaultMiddayAction() {
        //
    }

    @Override
    protected void defaultDayAction() {
        //
    }

    @Override
    protected void defaultLateDayAction() {
        //
    }

    @Override
    protected void defaultSunsetAction() {
        if (getCurrentState() == WorkerState.WORKING) {
            setCurrentState(WorkerState.GOING_HOME);
        }
    }

    @Override
    protected void defaultNightAction() {
        if(getCurrentState() == WorkerState.AT_SAFE_LOCATION) {
            setCurrentState(WorkerState.SLEEPING);
        }
    }

}
