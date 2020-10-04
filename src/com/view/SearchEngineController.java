package com.view;

import com.model.Dictionary;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SearchEngineController extends Dictionary {

    @FXML
    private Label warningMessage;

    @FXML
    private FontAwesomeIconView warningIcon;

    @FXML
    private TextField wordToSearch;

    @FXML
    private TextArea wordDefinition;

    @FXML
    private TextArea wordHistory;

    @FXML
    private ListView<String> searchedWordList = new ListView<>();

    public void searchForWord() {
        searchedWordList.getItems().clear();
        getDefinition();
        getWordList();
    }

    public void getWordList() {
        String pattern = wordToSearch.getText();
        if (pattern.length() != 0) {
            boolean isExisted = false;
            for (Map.Entry<String, String> word : dictionary.entrySet()) {
                String mainString = word.getKey();
                if (mainString.startsWith(pattern)) {
                    searchedWordList.getItems().add(mainString);
                    isExisted = true;
                }
            }
            warningMessage.setVisible(!isExisted);
            warningIcon.setVisible(!isExisted);
            searchedWordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            String selectedWord = searchedWordList.getSelectionModel().getSelectedItem();
            System.out.println(selectedWord);
        }
    }

    public void getDefinition() {
        if (dictionary.containsKey(wordToSearch.getText())) {
            wordDefinition.setText(wordToSearch.getText() + "\n\n" + dictionary.get(wordToSearch.getText()));
            if (!searchedWords.contains(wordToSearch.getText())){
                addToHistory();
            }
        }
    }

    public void addToHistory() {
        try {
            searchedWords.add(wordToSearch.getText());
            String src = "D:\\Source\\UET-Dictionary-TeamVersion\\src\\com\\model\\history.txt";
            FileWriter fileWriter = new FileWriter(src, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(wordToSearch.getText()+"\n");
            bufferedWriter.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public void showHistorySearch() {
        StringBuilder result = new StringBuilder();
        for (String word : searchedWords) {
            result.append(word).append("\n");
        }
        System.out.println(result);
        wordHistory.setText(result.toString());
    }

    public void addNewWord() {
        NewWordBoxController.openNewWordBox();
    }

    public void editWord() {
        if (!wordToSearch.getText().equals("")){
            EditBoxController.openEditBox(wordToSearch.getText());
        }
    }

    public void deleteWord() {
        DeleteWordController.openDeleteWordWindow();
    }

    public void aboutUs() {
        AboutUsController.openAboutUsWindow();
    }
}
