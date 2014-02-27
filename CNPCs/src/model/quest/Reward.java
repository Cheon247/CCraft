/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.quest;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Chingo
 */
public class Reward {
    private int credits;
    private int experience;
    private final Set<ItemStack> items;
    
    public Reward(int credits, int experience) {
        this.credits = credits;
        this.experience = experience;
        this.items = new HashSet<>();
    }
    
    public boolean addRewardItem(ItemStack item) {
        return items.add(item);
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
    
    
}
