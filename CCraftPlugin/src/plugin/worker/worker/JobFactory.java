/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package plugin.worker.worker;

import plugin.worker.jobs.carefree.DefaultJob;
import plugin.worker.jobs.scout.Scout;

/**
 *
 * @author Chingo
 */
public class JobFactory {
    
    public static Job getJob(String job) {
        if(job == null) {
            return new DefaultJob();
        }
        
        switch (job){
            case "scout" : return new Scout();
            case "village elder" : return new Scout();
            default: return null;    
        }
    }
    
}
