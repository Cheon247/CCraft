package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import api.behindTheName.BehindTheNameApi;
import api.behindTheName.PeopleNameOption;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import model.GenderOption;

/**
 *
 * @author Chingo
 */
public class MainController implements Initializable {

    @FXML
    private ListView<String> nameOpts;

    @FXML
    private ListView result;

    @FXML
    private ChoiceBox<GenderOption> genderBox;

    @FXML
    private ProgressBar behindTheNameProgress;

    private BehindTheNameApi btnApi;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnApi = new BehindTheNameApi();

        HashSet<String> options = new HashSet<>();
        options.addAll(btnApi.getPeopleOptions());

        List<String> uniqueOpts = new ArrayList<>();
        uniqueOpts.addAll(options);
        Collections.sort(uniqueOpts);
        nameOpts.setItems(FXCollections.observableList(uniqueOpts));
        nameOpts.getSelectionModel().select(0);

        genderBox.setItems(FXCollections.observableList(Arrays.asList(GenderOption.values())));
        genderBox.getSelectionModel().select(0);
    }

    @FXML
    public void onSearch() {
        final TreeSet<String> set = new TreeSet<>();
        final ObservableList<String> uniqueR = FXCollections.observableArrayList();
        final PeopleNameOption option = new PeopleNameOption(nameOpts.getSelectionModel().getSelectedItem(), genderBox.getSelectionModel().getSelectedItem());
        result.setItems(uniqueR);
        
        /**
        btnApi.Search(option, new ProgressCallback() {

            @Override
            public void onProgress(TreeSet<String> result) {
                set.addAll(result);
                uniqueR.setAll(set);
            }

            @Override
            public void onProgressUpdate(float progress) {
                behindTheNameProgress.setProgress(progress);
            }
        });
        **/
        
        

    }

}
