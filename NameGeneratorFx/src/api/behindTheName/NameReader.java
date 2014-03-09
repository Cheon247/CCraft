package api.behindTheName;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Chingo
 */
class NameReader {
    
    static HashMap<String,String> load() {
        HashMap<String,String> data = new HashMap<>();
        
        File file = new File("options.txt");
        
        try {
            List<String> lines = FileUtils.readLines(file);
            for(String s : lines) {
                if(s.contains("#")) continue;
                String htk = s.split("\\s")[0];
                String cntry = s.substring(htk.length()+1);
                data.put(cntry, htk);
            }
        } catch (IOException ex) {
            Logger.getLogger(NameReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
        
    }
    
}
