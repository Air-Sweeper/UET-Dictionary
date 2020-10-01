package com.view;

import com.model.Dictionary;
import com.model.Word;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewWordBoxController extends Dictionary {

    static Stage newWordWindow;
    static Scene newWordScene;
    static AnchorPane newWordPane;

    @FXML
    private TextField newWord;

    @FXML
    private TextField newWordMeaning;

    public static void openNewWordBox() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NewWordBoxController.class.getResource("NewWordBox.fxml"));
            newWordPane = loader.load();
            newWordWindow = new Stage();
            newWordScene = new Scene(newWordPane);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(newWordScene);
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
            String src = "D:\\Source\\UET-Dictionary-TeamVersion\\src\\com\\model\\dictionary.txt";
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
}
