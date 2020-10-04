package com.view;

import com.model.Dictionary;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.model.DictionaryCommand.dictionaryExportToFile;

public class EditBoxController extends Dictionary {

    @FXML
    private TextField newDefinition;

    private static String wordTarget;

    private static Stage editBoxStage;

    public void editWord() {
        if (!newDefinition.getText().equals("")) {
            dictionary.replace(wordTarget, newDefinition.getText());
            dictionaryExportToFile();
            closeEditBox();
        }
    }

    public static void openEditBox(String wordToEdit) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditBoxController.class.getResource("EditBox.fxml"));
            AnchorPane newWordPane = loader.load();

            Scene newWordScene = new Scene(newWordPane);
            editBoxStage = new Stage();
            editBoxStage.initModality(Modality.APPLICATION_MODAL);
            editBoxStage.setTitle("Edit word");
            editBoxStage.setScene(newWordScene);
            editBoxStage.show();
            wordTarget = wordToEdit;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeEditBox() {
        editBoxStage.close();
    }
}
