package com.view;

import com.model.Dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.*;

public class SearchEngineController extends Dictionary {

    @FXML
    private TextField wordToSearch;

    @FXML
    private TextArea wordDefinition;

    @FXML
    private TextArea wordHistory;

    @FXML
    private ListView<String> searchedWordList = new ListView<>();

    public void editWord() {
        EditBoxController.openEditBox(wordToSearch.getText());
    }

    public void addNewWord() {
        NewWordBoxController.openNewWordBox();
    }

    public void showHistorySearch() {

        try {
            File historyFile = new File("D:\\Source\\UET-Dictionary-TeamVersion\\src\\com\\model\\history.txt");
            Scanner inputFile = new Scanner(historyFile);

            HashSet<String> historySearchWord = new HashSet<>();
            while (inputFile.hasNext()) {
                String word = inputFile.nextLine();
                historySearchWord.add(word);
            }
            StringBuilder result = new StringBuilder();
            for (String word : historySearchWord) {
                result.append(word).append("\n");
            }
            wordHistory.setText(result.toString());
            inputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void searchForWord() {

        getDefinition();
        getWordList();
    }

    private void getDefinition() {

        String word = wordToSearch.getText();

        if (dictionary.containsKey(word)) {
            addToHistory();
            wordDefinition.setText(word + "\n\n" + dictionary.get(word));
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

    public void addToHistory() {

        try {
            String src = "D:\\Source\\UET-Dictionary-TeamVersion\\src\\com\\model\\history.txt";
            FileWriter fileWriter = new FileWriter(src, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(wordToSearch.getText()+"\n");
            bufferedWriter.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
