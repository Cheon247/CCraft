/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

/**
 *
 * @author Chingo
 */
public class ProgressUtil {
     public static float[] getProgressValues(int amount) {
        float[] values = new float[amount+1];
        for(int i = 1; i <= amount; i++) values[i] = (float) (i * 100 / amount) / 100;
        return values;
    }
}
