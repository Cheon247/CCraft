/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.behindTheName;

import api.PeopleNameApi;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import main.MainController;

import model.GenderOption;
import model.ProgressCallback;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Chingo
 */
public class BehindTheNameApi implements PeopleNameApi {

    private final static int MAX_SIZE = 125;
    private final static int MAX_SEARCH = 5;
    private final String BEHIND_THE_NAME_KEY = "ch795519";
    private final static HashMap<String, String> genres;
    private final float[] behindTheNameProgressValues;

    public BehindTheNameApi() {
        behindTheNameProgressValues = new float[MAX_SEARCH];
        for (int i = 0; i < MAX_SEARCH; i++) {
            behindTheNameProgressValues[i] = (float) (i * 100 / MAX_SEARCH) / 100;
        }
    }

    static {
        genres = NameReader.load();
    }

    public static HashMap<String,String> getGenres() {
        return new HashMap<>(genres);
    }
    
    @Override
    public void Search(final PeopleNameOption option, ProgressCallback callback) {
        Search(new HashSet<>(), 0, option, callback);
    }

    private void Search(final HashSet<String> set, int counter, final PeopleNameOption option, final ProgressCallback callback) {
        final int c = counter;

        System.out.println("Counter: " + counter);
        callback.onProgressUpdate(behindTheNameProgressValues[c]);
        if(!set.isEmpty()) callback.onProgress(new TreeSet<>(set));
        
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                String nation = genres.get(option.genre);
                String gender = getGenderKey(option.gender);
                String url = "http://www.behindthename.com/api/random.php?number=6&usage=" + nation + "&gender=" + gender + "&key=" + BEHIND_THE_NAME_KEY;

                final File file = new File("Temp.xml");
                try {
                    URL u = new URL(url);
                    FileUtils.copyURLToFile(u, file);

                    List<String> lines = FileUtils.readLines(file);
                    for (String s : lines) {
                        if (s.contains("<name>")) {
                            System.out.println(s);
                            s = s.substring(s.indexOf(">") + 1, s.lastIndexOf("</"));
                            set.add(s);
                        }
                    }

                } catch (MalformedURLException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (set.size() == MAX_SIZE || c == MAX_SEARCH - 1 || set.isEmpty()) {
                    callback.onProgressUpdate(1.0f);
                    
                    return;
                }
                
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Search(set, c + 1, option, callback);
                    }
                });
                

                

            }
        });

    }

    private String getGenderKey(GenderOption gender) {
        switch (gender) {
            case MALE:
                return "m";
            case FEMALE:
                return "f";
            case AMBIGOUS:
                return "u";
            default:
                return "both";
        }
    }

    @Override
    public Set<String> getPeopleOptions() {
        return new HashSet<>(genres.keySet());
    }

    @Override
    public boolean supports(PeopleNameOption option) {
        return genres.containsKey(option.genre);
    }

}
