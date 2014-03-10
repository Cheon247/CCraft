/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.wiki;

import api.PeopleNameApi;
import api.behindTheName.PeopleNameOption;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import json.JSONArray;
import json.JSONObject;
import main.ProgressUtil;
import model.GenderOption;
import model.ProgressCallback;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Chingo
 */
public class WikiNameApi2 implements PeopleNameApi {

    private final String BASE_URL = "http://en.wikipedia.org/w/api.php?format=json&action=query";
    private static final String GIVEN_NAME_BY_CULTURE = "Category:Given_names_by_culture";
    private final DocumentBuilderFactory dbFactory;
    private DocumentBuilder documentBuilder;

    private final TreeMap<String, String> cultures;

    public WikiNameApi2() {
        dbFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(WikiNameApi2.class.getName()).log(Level.SEVERE, null, ex);
        }
        cultures = getCategoryOption(GIVEN_NAME_BY_CULTURE);
    }

    private TreeMap<String, String> getCategoryOption(String title) {
        String query = BASE_URL + "&list=categorymembers&cmlimit=500&cmtitle=" + title + "&cmtype=subcat&cmprop=title";
        TreeMap<String, String> values = new TreeMap<>();
        try {
            URL url = new URL(query);
            File file = File.createTempFile("WIKI_", title);
            FileUtils.copyURLToFile(url, file);
            String s = FileUtils.readFileToString(file);

            JSONArray json = new JSONObject(s).getJSONObject("query").getJSONArray("categorymembers");
            for (int i = 0; i < json.length(); i++) {
                String value = json.getJSONObject(i).getString("title");
                String key = value.replaceAll("Category:", "").replaceAll("given names", "").trim();
                values.put(key, value);
            }

        } catch (IOException ex) {
            Logger.getLogger(WikiNameApi2.class.getName()).log(Level.SEVERE, null, ex);
        }

        return values;
    }

    private TreeMap<String, String> getGenderNameContinue(String title, String continueString) {
        String query = BASE_URL + "&list=categorymembers&cmlimit=500&cmtitle=" + title.replaceAll("\\s+", "_") + "&cmprop=title&cmcontinue=" + continueString;
        TreeMap<String, String> values = new TreeMap<>();
        try {
            URL url = new URL(query);
            File file = File.createTempFile("WIKI_", title);
            FileUtils.copyURLToFile(url, file);
            String s = FileUtils.readFileToString(file);
            JSONObject j = new JSONObject(s);
            if (j.has("query-continue")) {
                values.putAll(getGenderNameContinue(title, j.getJSONObject("query-continue").getJSONObject("categorymembers").getString("cmcontinue")));
            }
            JSONArray json = j.getJSONObject("query").getJSONArray("categorymembers");
            for (int i = 0; i < json.length(); i++) {
                String value = json.getJSONObject(i).getString("title");
                String key = value;
                if (key.contains("(")) {
                    key = key.substring(0, key.indexOf("("));
                }
                values.put(key, value);
            }

        } catch (IOException ex) {
            Logger.getLogger(WikiNameApi2.class.getName()).log(Level.SEVERE, null, ex);
        }

        return values;
    }

    private TreeMap<String, String> getGenderNames(String title) {
        String query = BASE_URL + "&list=categorymembers&cmlimit=500&cmtitle=" + title.replaceAll("\\s+", "_") + "&cmprop=title";
        TreeMap<String, String> values = new TreeMap<>();
        try {
            URL url = new URL(query);
            File file = File.createTempFile("WIKI_", title);
            FileUtils.copyURLToFile(url, file);
            String s = FileUtils.readFileToString(file);
            JSONObject j = new JSONObject(s);
            if (j.has("query-continue")) {
                values.putAll(getGenderNameContinue(title, j.getJSONObject("query-continue").getJSONObject("categorymembers").getString("cmcontinue")));
            }
            JSONArray json = j.getJSONObject("query").getJSONArray("categorymembers");
            for (int i = 0; i < json.length(); i++) {
                String value = json.getJSONObject(i).getString("title");
                String key = value;
                if (key.contains("(")) {
                    key = key.substring(0, key.indexOf("("));
                }
                values.put(key, value);
            }

        } catch (IOException ex) {
            Logger.getLogger(WikiNameApi2.class.getName()).log(Level.SEVERE, null, ex);
        }

        return values;
    }

    private boolean containsMaleKey(String s) {
        return s.contains("masculine given names");
    }

    private boolean containsFemaleKey(String s) {
        return s.contains("feminine given names");
    }

    private boolean containsUniKey(String s) {
        return s.contains("unisex given names");
    }

    private boolean containeAnyKey(String s) {
        return containsFemaleKey(s) || containsMaleKey(s) || containsUniKey(s);
    }

    private boolean containsGenderKey(TreeMap<String, String> values) {
        for (String s : values.values()) {
            if (containeAnyKey(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<String> getPeopleOptions() {
        return cultures.keySet();
    }

    @Override
    public boolean supports(PeopleNameOption option) {
        return cultures.keySet().contains(option.genre);
    }

    @Override
    public void Search(PeopleNameOption option, ProgressCallback callback) {
        if (supports(option)) {
            String title = cultures.get(option.genre).replaceAll("\\s+", "_");
            TreeMap<String, String> values = getCategoryOption(title);

            if (containsGenderKey(values)) { // WE HAVE A GENDER KEY!
                for (String potentialGenderKey : values.values()) {
                    if (option.gender == GenderOption.EITHER && (containeAnyKey(potentialGenderKey))) {
                        processAll(values.keySet(), option, callback);
                    } else if (option.gender == GenderOption.AMBIGOUS && containsUniKey(potentialGenderKey)
                            || option.gender == GenderOption.FEMALE && containsFemaleKey(potentialGenderKey)
                            || option.gender == GenderOption.MALE && containsMaleKey(potentialGenderKey)) {
                        processSpecific(potentialGenderKey, option, callback);
                    }
                }
            } else {
                System.out.println("preforming deep search");
            }
        }
    }

    private void processAll(Set<String> s, PeopleNameOption option, ProgressCallback callback) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void processSpecific(String s, PeopleNameOption option, final ProgressCallback callback) {
        final TreeMap<String, String> values = getGenderNames(s);

        final float[] progressValues = ProgressUtil.getProgressValues(values.size());
        int counter = 1;
        
        for (final String peopleName : values.values()) {
            final int c = counter++;
            callback.onProgressUpdate(progressValues[c]);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    final File file = processName(peopleName, option);
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            callback.onProgress(processFile(peopleName, file));
                        }
                    });

                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();
        }
    }

    private File processName(String name, PeopleNameOption option) {
        final String url = "http://en.wikipedia.org/w/api.php?format=xml&action=query&titles=" + name.replaceAll("\\s+", "_") + "&prop=links&pllimit=500&plnamespace=0";
        File file = null;
        try {
            file = File.createTempFile(option.genre.toUpperCase() + "_" + name + "_", option.gender.name(), new File("/temp"));
            URL u = new URL(url);
            FileUtils.copyURLToFile(u, file);

        } catch (IOException ex) {
            Logger.getLogger(WikiNameApi2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    private TreeSet<String> processFile(String name, File file) {
        final TreeSet<String> names = new TreeSet<>();
        try {
            Document doc = documentBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("pl");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i).getAttributes().getNamedItem("title");
                if (n == null) {
                    continue;
                }
                String s = n.getNodeValue();
                if (s.contains(name) && !s.matches(".*\\d.*")) {
                    if(s.contains("(")) s = s.substring(0, s.indexOf("("));
                    names.add(s);
                }
            }
        } catch (SAXException | IOException ex) {
            Logger.getLogger(WikiNameApi2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return names;
    }

}
