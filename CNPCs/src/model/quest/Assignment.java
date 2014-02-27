/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.quest;

/**
 *
 * @author Chingo
 */
public abstract class Assignment {

    private Reward reward;
    
    public enum AssignmentType {
        TREE_EXPLORATION,
        GATHER_ASSIGNMENT // Search and Gather items (like seeds of melons or sugar canes);
    }
}
