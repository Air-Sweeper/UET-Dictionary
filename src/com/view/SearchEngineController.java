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
    private TextArea wordDefinition;

    @FXML
    private ListView<String> searchedWordList = new ListView<>();

    @FXML
    public void addNewWord() {
        UtilityController.openNewWordBox();
    }

    @FXML
    public void searchForWord() {

        getDefinition();
        getWordList();
    }

    private void getDefinition() {

        String word = wordToSearch.getText();

        if (dictionary.containsKey(word)) {
            wordDefinition.setText(word + "\n" + dictionary.get(word));
        } else {
            wordDefinition.setText("No matched word found!");
        }
    }

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

}
