package com.view;

import com.model.Dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchEngineController extends Dictionary {

    @FXML
    private TextField wordToSearch;

    @FXML
    private TextField wordTarget;

    @FXML
    private TextArea wordDefinition;

    @FXML
    private ListView<String> searchedWordList = new ListView<>();

    @FXML
    private void getDefinition() {

        wordTarget.setText(wordToSearch.getText());
        wordDefinition.setText(dictionary.getOrDefault(wordTarget.getText(),"No matched word found!"));
    }

    @FXML
    public void getWordList() {

        String pattern = wordToSearch.getText();

        if (pattern.length() != 0) {
            List<String> wordList = new ArrayList<>();

            boolean isExisted = false;
            for (Map.Entry<String, String> word : dictionary.entrySet()) {

                String mainString = word.getKey();
                if (mainString.startsWith(pattern)) {
                    wordList.add(mainString);
                    isExisted = true;
                }
            }

            if (!isExisted) {

                System.out.println("No word found!");
            } else {

                ObservableList<String> observableWordList = FXCollections.observableList(wordList);
                searchedWordList.getItems().addAll(observableWordList);
                searchedWordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            }
        }
    }

    @FXML
    public void searchForWord() {

        getDefinition();
        getWordList();
    }

    @FXML
    public void addNewWord() {
        UtilityController.openNewWordBox();
    }

}
