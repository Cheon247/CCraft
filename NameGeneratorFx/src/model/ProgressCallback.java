/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.TreeSet;

/**
 *
 * @author Chingo
 */
public interface ProgressCallback {
    
    public void onProgressUpdate(double progress);
    public void onProgress(TreeSet<String> result);
}
