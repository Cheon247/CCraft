/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api.behindTheName;

import model.GenderOption;

/**
 *
 * @author Chingo
 */
public class PeopleNameOption {
    public final String genre;
    public final GenderOption gender;
    
    public PeopleNameOption(String genre, GenderOption gender) {
        this.gender = gender;
        this.genre = genre;
    }
}
