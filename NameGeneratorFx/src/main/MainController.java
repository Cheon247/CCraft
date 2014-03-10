package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import api.behindTheName.BehindTheNameApi;
import api.behindTheName.PeopleNameOption;
import api.wiki.WikiNameApi2;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import model.GenderOption;
import model.ProgressCallback;

/**
 *
 * @author Chingo
 */
public class MainController implements Initializable {

    @FXML
    private ListView<String> nameOpts;

    @FXML
    private Label resultsLabel, givenNameLabel, surnameLabel;

    @FXML
    private ListView givenNameResult, surnameResult;

    @FXML
    private ChoiceBox<GenderOption> genderBox;

    @FXML
    private ProgressBar behindTheNameProgress, wikiProgressBar;

    private BehindTheNameApi btnApi;
    private WikiNameApi2 wikiApi;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnApi = new BehindTheNameApi();
        wikiApi = new WikiNameApi2();

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
        wikiProgressBar.setProgress(0);
        behindTheNameProgress.setProgress(0);

        final Set<String> set = Collections.synchronizedSortedSet(new TreeSet<String>());
        final ObservableList<String> uniqueGivenNames = FXCollections.observableArrayList();
        final ObservableList<String> uniqueSurnames = FXCollections.observableArrayList();
        final PeopleNameOption option = new PeopleNameOption(nameOpts.getSelectionModel().getSelectedItem(), genderBox.getSelectionModel().getSelectedItem());

        final Set<String> names = Collections.synchronizedSortedSet(new TreeSet<String>());
        final Set<String> surnames = Collections.synchronizedSortedSet(new TreeSet<String>());
        final Set<String> total = Collections.synchronizedSortedSet(new TreeSet<String>());

        givenNameResult.setItems(uniqueGivenNames);
        surnameResult.setItems(uniqueSurnames);

        btnApi.Search(option, new ProgressCallback() {

            @Override
            public void onProgress(TreeSet<String> result) {
                names.addAll(result);
                uniqueGivenNames.setAll(names);

                surnameLabel.setText("Surnames (" + surnames.size() + ") ");
                givenNameLabel.setText("Given Names (" + names.size() + ") ");
                resultsLabel.setText("Possibilities (" + names.size() * surnames.size() + ") ");
            }

            @Override
            public void onProgressUpdate(double progress) {
                behindTheNameProgress.setProgress(progress);

            }
        });
        wikiApi.Search(option, new ProgressCallback() {

            @Override
            public void onProgressUpdate(double progress) {
                wikiProgressBar.setProgress(progress);

            }

            @Override
            public void onProgress(TreeSet<String> result) {
                names.addAll(getNames(result));
                surnames.addAll(getSurNames(result));

                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        uniqueGivenNames.setAll(names);
                        uniqueSurnames.setAll(surnames);

                        surnameLabel.setText("Surnames (" + surnames.size() + ") ");
                        givenNameLabel.setText("Given Names (" + names.size() + ") ");
                        resultsLabel.setText("Possibilities (" + names.size() * surnames.size() + ") ");
                    }
                });

            }
        });

    }

    private TreeSet<String> getNames(Set<String> data) {
        TreeSet<String> names = new TreeSet<>();
        for (String s : data) {
            names.add(s.split("\\s+")[0]);
        }
        return names;
    }

    private TreeSet<String> getSurNames(Set<String> data) {
        TreeSet<String> names = new TreeSet<>();
        for (String s : data) {
            if (s.split("\\s+").length >= 2) {
                names.add(s.substring(s.split("\\s+")[0].length()));
            }
        }
        return names;
    }

}
