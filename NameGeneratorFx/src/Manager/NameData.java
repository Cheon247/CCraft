/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import java.util.UUID;
import model.GenderOption;

/**
 *
 * @author Chingo
 */
public class NameData {

    public enum TYPE {

        FIRST_NAME(0),
        MIDDLE_NAME(1),
        LAST_NAME(2);

        public final int id;

        TYPE(int id) {
            this.id = id;
        }

        public static TYPE getType(int id) {
            switch (id) {
                case 0:
                    return FIRST_NAME;
                case 1:
                    return MIDDLE_NAME;
                case 2:
                    return LAST_NAME;
                default:
                    throw new IllegalArgumentException(id + " not recognized");
            }
        }

    }



    private final GenderOption gender;
    private final String genre;
    private final String name;
    private final TYPE type;

    public NameData(String name, GenderOption gender, TYPE type, String genre) {
        this.genre = genre;
        this.name = name;
        this.type = type;
        this.gender = gender;
    }

    public NameData(String name, int gender, int type, String genre) {
        this.name = name;
        this.gender = GenderOption.getGender(gender);
        this.type = TYPE.getType(type);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public TYPE getType() {
        return type;
    }

    public GenderOption getGender() {
        return gender;
    }

    
}
