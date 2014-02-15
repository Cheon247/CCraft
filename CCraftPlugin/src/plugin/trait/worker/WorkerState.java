/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.trait.worker;

/**
 *
 * @author Chingo
 */
    public enum WorkerState {
        /**
         * When an NPC is either home or at a safelocation, TIME MUST BE NIGHT
         */
        SLEEPING,
        /**
         * When an NPC has no job or assignment he is working on at the moment
         */
        IDLE,
        /**
         * When an NPC has a job and goes to work
         */
        GOING_TO_WORK,
        /**
         * When an NPC has a job and is working on it
         */
        WORKING,
        /**
         * When an NPC is on it's way home
         */
        GOING_HOME,
        /**
         * When an NPC is in combat, wheter he chose for it or not
         */
        IN_COMBAT,
        /**
         * When an NPC was attacked and choose not to fight, but to retreat
         * NPC's can only retreat when they have a safelocation otherwise they flee
         */
        RETREATING,
        /**
         * When an NPC was attacked and choose not to fight and has no location to retreat to
         */
        FLEEING,
        /**
         * When an NPC is specifically ask what to do
         */
        ASSIGNED_MOVE_BY_PLAYER,
        /**
         * When an NPC has arrived at a safe destination
         */
        AT_SAFE_LOCATION,
        /**
         * When Attacked, doesnt get in this state afther changing into IN_COMBAT, RETREATING or FLEEING
         */
        ATTACKED
    }
