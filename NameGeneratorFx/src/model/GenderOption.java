/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Chingo
 */
public enum GenderOption {

    MALE(0),
    FEMALE(1),
    AMBIGOUS(2),
    EITHER(3);

    public final int id;

    GenderOption(int id) {
        this.id = id;
    }

    public static GenderOption getGender(int id) {
        switch (id) {
            case 0:
                return GenderOption.MALE;
            case 1:
                return GenderOption.FEMALE;
            case 2:
                return GenderOption.AMBIGOUS;
            case 3:
                return GenderOption.EITHER;
            default:
                throw new IllegalArgumentException(id + " not recognized");
        }

    }

    @Override
    public String toString() {
        return name().toUpperCase();
    }
    
    
}
