package com.view;

import com.App;
import com.model.Dictionary;
import com.model.DictionaryCommand;
import com.model.Word;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class UtilityController extends Dictionary {

    static Stage newWordWindow;
    static Scene newWordScene;
    static AnchorPane newWordPane;

    @FXML
    private TextField newWord;

    @FXML
    private TextField newWordMeaning;

    /**
     * Add new words to file functions.
     */
    public static void openNewWordBox() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UtilityController.class.getResource("NewWordBox.fxml"));
            newWordPane = loader.load();

            newWordWindow = new Stage();
            newWordScene = new Scene(newWordPane);
            newWordWindow.setScene(newWordScene);
            newWordWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewWord() {

        Word word = new Word();
        word.setWord_target(newWord.getText());
        word.setWord_explain(newWordMeaning.getText());
        updateDictionary(word);
        closeNewWordBox();
    }

    public void updateDictionary(Word newWord) {

        dictionary.put(newWord.getWord_target(), newWord.getWord_explain());
        try {

            String src = "D:\\Source\\UET-Dictionary\\src\\com\\model\\dictionary.txt";
            FileWriter fileWriter = new FileWriter(src, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newWord.getWord_target() + "\t" + newWord.getWord_explain() + "\n");
            bufferedWriter.close();
        } catch(IOException exception) {

            exception.printStackTrace();
        }
    }

    public void closeNewWordBox() {
        newWordWindow.close();
    }


    /**
     * Add favourite words.
     */



}
