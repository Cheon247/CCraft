/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.GenderOption;


/**
 *
 * @author Chingo
 */
public final class DataManager {

    private static DataManager instance;

    private DataManager() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS names(NAME,GENDER,TYPE,GENRE);";
            Connection conn = getConnection();
            PreparedStatement s = conn.prepareStatement(sql);
            s.execute();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public final Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:PeopleNamesData.db");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean nameExists(String name, String genre, Manager.NameData.TYPE type, GenderOption gender) throws SQLException {
        Connection conn = getConnection();
        String sql = "SELECT 1 FROM items WHERE(NAME = \"" + name + "\" AND GENRE = \""+genre+" AND TYPE = \""+type.id + " AND SEX = \"" + gender.id + ");";
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            ResultSet rs = s.executeQuery();
            return rs.next();
        }
    }

    public final boolean insert(Manager.NameData data) throws SQLException {
        if(nameExists(data.getName(), data.getGenre(), data.getType(), data.getGender())) return false;
        String sql = "INSERT INTO items VALUES(?,?,?,?);";
        Connection conn = getConnection();
        try (PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, data.getName());
            s.setInt(2, data.getGender().id);
            s.setInt(3, data.getType().id);
            s.setString(4, data.getGenre().toLowerCase());
            return true;
        }
    }


    
}
