/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import api.behindTheName.PeopleNameOption;
import java.util.Set;
import javafx.scene.control.ProgressBar;
import model.ProgressCallback;

/**
 *
 * @author Chingo
 */
public interface PeopleNameApi {
    
    public Set<String> getPeopleOptions();
    public boolean supports(PeopleNameOption option);
    public void Search(final PeopleNameOption option, ProgressCallback callback);
}
