/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.wiki;

import api.PeopleNameApi;
import api.behindTheName.PeopleNameOption;
import de.tudarmstadt.ukp.wikipedia.api.Category;
import de.tudarmstadt.ukp.wikipedia.api.DatabaseConfiguration;
import de.tudarmstadt.ukp.wikipedia.api.Page;
import de.tudarmstadt.ukp.wikipedia.api.WikiConstants;
import de.tudarmstadt.ukp.wikipedia.api.WikiConstants.Language;
import de.tudarmstadt.ukp.wikipedia.api.Wikipedia;
import de.tudarmstadt.ukp.wikipedia.api.exception.WikiApiException;
import de.tudarmstadt.ukp.wikipedia.api.exception.WikiInitializationException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chingo
 */
public class WikiNameApi implements PeopleNameApi {

    private final static String baseUrl = "";

    private Wikipedia wiki;

    public WikiNameApi() {
        DatabaseConfiguration dbConfig = new DatabaseConfiguration();
        dbConfig.setHost("SERVER_URL");
        dbConfig.setDatabase("DATABASE");
        dbConfig.setUser("USER");
        dbConfig.setPassword("PASSWORD");
        dbConfig.setLanguage(Language.english);
        try {
            this.wiki = new Wikipedia(dbConfig);
        } catch (WikiInitializationException ex) {
            Logger.getLogger(WikiNameApi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Search() {
        System.out.println("lol");
        try {
            Page page = wiki.getPage("Category:Names");
            
            for(Category c : page.getCategories()) {
//                System.out.println(c.getTitle());
            }
        } catch (WikiApiException ex) {
            Logger.getLogger(WikiNameApi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Set<String> getPeopleOptions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean supports(PeopleNameOption option) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String... args) {
        WikiNameApi wiki = new WikiNameApi();
        wiki.Search();
    }

}
