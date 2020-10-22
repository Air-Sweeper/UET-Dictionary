package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.Dictionary;
import main.java.model.Word;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewWordBoxController extends Dictionary {

    private static final String NEW_WORD_BOX_FILE_PATH = "view/fxml/NewWordBox.fxml";
    private static final String NEW_WORD_BOX_TITLE = "New word";
    private static final String DICTIONARY_FILE_PATH = "src/main/resources/E_V_dictionary.txt";
    private static Stage newWordWindow;

    @FXML
    private TextField newWordField;
    @FXML
    private TextField newWordMeaningField;

    public static void openNewWordBox() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(NewWordBoxController.class.getResource(NEW_WORD_BOX_FILE_PATH));
            AnchorPane newWordPane = loader.load();
            Scene newWordScene = new Scene(newWordPane);
            newWordWindow = new Stage();
            newWordWindow.initModality(Modality.APPLICATION_MODAL);
            newWordWindow.setTitle(NEW_WORD_BOX_TITLE);
            newWordWindow.setScene(newWordScene);
            newWordWindow.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewWord() {
        if (!newWordField.getText().equals("")
                && !newWordMeaningField.getText().equals("")) {
            String target = newWordField.getText();
            String definition = "<html><i>"
                    + target
                    + "</i><br/><ul><li><font color='#cc0000'><b>"
                    + newWordMeaningField.getText()
                    + "</b></font></li></ul></html>";
            Word word = new Word(target, definition);
            updateDictionary(word);
            closeNewWordBox();
        }
    }

    public void updateDictionary(Word newWord) {
        try {
            dictionary.put(newWord.getWord_target(), newWord.getWord_explain());
            FileWriter fileWriter = new FileWriter(DICTIONARY_FILE_PATH, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newWord.getWord_target() + newWord.getWord_explain() + "\n");
            bufferedWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void closeNewWordBox() {
        newWordWindow.close();
    }
}
