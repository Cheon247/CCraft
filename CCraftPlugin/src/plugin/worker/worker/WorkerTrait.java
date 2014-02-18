/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin.worker.worker;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;

/**
 *
 * @author Chingo
 */
public class WorkerTrait extends Trait {

    private Worker worker;

    @Persist("jobname")
    private String job;

    public WorkerTrait() {
        super("worker");
    }

    @Override
    public void load(DataKey key) {
//        job = key.getString("jobname");
    }

    // Save settings for this NPC (optional). These values will be persisted to the Citizens saves file
    @Override
    public void save(DataKey key) {
//        key.setString("jobname", job);
    }

    @Override
    public void onSpawn() {
        if (job != null) {
            this.worker = new Worker(npc, job);
        }
    }

    @Override
    public void onAttach() {
        this.worker = new Worker(npc, job);
    }
    
    

    public Worker getWorker() {
        return worker;
    }

}
