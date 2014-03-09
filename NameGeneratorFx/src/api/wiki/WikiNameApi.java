/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.wiki;

import api.PeopleNameApi;
import api.behindTheName.BehindTheNameApi;
import api.behindTheName.PeopleNameOption;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
public class WikiNameApi implements PeopleNameApi {

    private final String NAME_BY_CULTURE = "Category:Given_names_by_culture";

    private final DocumentBuilderFactory dbFactory;
    private DocumentBuilder documentBuilder;

    private final TreeMap<String, String> genres;

    public WikiNameApi() {
        dbFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(WikiNameApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file = loadCategories(NAME_BY_CULTURE);
        genres = invalidate(getSubgenres(file));
    }

    private TreeMap<String, String> invalidate(List<String> subcategories) {
        TreeMap<String, String> map = new TreeMap<>();
        for (String s : subcategories) {
            String a = s.replaceAll("Category:", "").replaceAll("given names", "").trim();
            map.put(a, s);
        }

        return map;
    }

    private List<String> getTitleAttributes(File file) {
        List<String> titles = new ArrayList<>();
        try {
            Document doc = documentBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("cm");
            for (int i = 0; i < nodes.getLength(); i++) {

                Node n = nodes.item(i).getAttributes().getNamedItem("title");
                if (n == null) {
                    continue;
                }
                String s = n.getNodeValue();
                titles.add(s);

            }
        } catch (SAXException | IOException ex) {
            Logger.getLogger(WikiNameApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return titles;
    }

    private List<String> getSubgenres(File file) {
        List<String> genres = new ArrayList<>();
        try {
            Document doc = documentBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("cm");
            for (int i = 0; i < nodes.getLength(); i++) {

                Node n = nodes.item(i).getAttributes().getNamedItem("title");
                if (n == null) {
                    continue;
                }
                String s = n.getNodeValue();
                if (s.startsWith("Category:")) {
                    genres.add(s);
                }

            }
        } catch (SAXException | IOException ex) {
            Logger.getLogger(WikiNameApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return genres;
    }

    private File loadCategories(String title) {
        String url = "http://en.wikipedia.org/w/api.php?format=xml&action=query&list=categorymembers&cmlimit=500&cmtitle=" + title;
        System.out.println(url);
        File file;
        try {
            file = File.createTempFile("WIKI" + "_", "API", new File("/temp"));
            URL u = new URL(url);
            FileUtils.copyURLToFile(u, file);
        } catch (IOException ex) {
            Logger.getLogger(WikiNameApi.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return file;
    }

    private File getSubCategories(String title, PeopleNameOption option) {
        if (!title.startsWith("Category:")) {
            throw new IllegalArgumentException(title + " invalid");
        }
        title = title.replaceAll("\\s+", "_");
        return getSubCategories(title, 500, option);
    }

    private File getSubCategories(String title, int amount, PeopleNameOption option) {
        String url = "http://en.wikipedia.org/w/api.php?format=xml&action=query&list=categorymembers&cmlimit=" + amount + "&cmtitle=" + title;
        System.out.println(url);
        File file;
        try {
            file = File.createTempFile(option.genre.toUpperCase() + "_", option.gender.name(), new File("/temp"));
            URL u = new URL(url);
            FileUtils.copyURLToFile(u, file);
        } catch (IOException ex) {
            Logger.getLogger(WikiNameApi.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return file;
    }

    @Override
    public Set<String> getPeopleOptions() {
        return genres.keySet();
    }

    @Override
    public boolean supports(PeopleNameOption option) {
        return genres.containsKey(option.genre);
    }

    @Override
    public void Search(PeopleNameOption option, ProgressCallback callback) {
        if (!genres.containsKey(option.genre)) {
            callback.onProgressUpdate(1.0f);
        }
        Search(new HashSet<String>(), option, callback);
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

    private void Search(Set<String> sub, PeopleNameOption option, ProgressCallback callback) {
        File file = getSubCategories(genres.get(option.genre), option);
        if (getSubgenres(file).isEmpty()) {
            return;
        } else {
            for (String s : getSubgenres(file)) {
                if (option.gender == GenderOption.AMBIGOUS && containsUniKey(s)) {
                    HandleSex(s, option, callback);
                } else if (option.gender == GenderOption.FEMALE && containsFemaleKey(s)) {
                    HandleSex(s, option, callback);
                } else if (option.gender == GenderOption.MALE && containsMaleKey(s)) {
                    HandleSex(s, option, callback);
                } else if (option.gender == GenderOption.EITHER && (containeAnyKey(s))) {
                    handleAny(s, option, callback);
                }
//                } else if (s.contains("Category:") && !sub.contains(s)) {
//                    sub.add(s);
//                    handleSubCategory(s, option, callback);
//                }
            }
        }

    }


    private void handleAny(String s, PeopleNameOption option, ProgressCallback callback) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    private void HandleSex(String s, PeopleNameOption option, ProgressCallback callback) {
        File file = getSubCategories(s, option);
        List<String> names = getTitleAttributes(file);
        for(int i = 0; i < names.size(); i++) {
            handleName(names.get(i));
            if(names.get(i).contains("("))
            names.set(i, names.get(i).substring(0, names.get(i).indexOf("(")).trim());
        }
        callback.onProgress(new TreeSet<>(names));
    }

    private void handleName(String name) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
