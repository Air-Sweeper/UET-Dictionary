package com.view;

import com.model.Dictionary;
import com.model.Word;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewWordBoxController extends Dictionary {

    @FXML
    private TextField newWord;

    @FXML
    private TextField newWordMeaning;

    static Stage newWordWindow;



    public static void openNewWordBox() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NewWordBoxController.class.getResource("NewWordBox.fxml"));
            AnchorPane newWordPane = loader.load();

            Scene newWordScene = new Scene(newWordPane);
            newWordWindow = new Stage();
            newWordWindow.initModality(Modality.APPLICATION_MODAL);
            newWordWindow.setTitle("New word");
            newWordWindow.setScene(newWordScene);
            newWordWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewWord() {
        if (!newWord.getText().equals("")) {
            Word word = new Word();
            word.setWord_target(newWord.getText());
            word.setWord_explain(newWordMeaning.getText());
            updateDictionary(word);
            closeNewWordBox();
        }
    }

    public void updateDictionary(Word newWord) {
        try {
            dictionary.put(newWord.getWord_target(), newWord.getWord_explain());
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
