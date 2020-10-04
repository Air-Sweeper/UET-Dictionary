package com.view;

import com.model.Dictionary;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteWordController extends Dictionary {

    @FXML
    private TextField wordToDelete;

    @FXML
    private Label warningMessage;

    @FXML
    private FontAwesomeIconView warningIcon;

    private static Stage deleteWordBoxStage;

    public static void openDeleteWordWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditBoxController.class.getResource("DeleteWordBox.fxml"));
            AnchorPane deleteWordBoxPane = loader.load();

            Scene newWordScene = new Scene(deleteWordBoxPane);
            deleteWordBoxStage = new Stage();
            deleteWordBoxStage.initModality(Modality.APPLICATION_MODAL);
            deleteWordBoxStage.setTitle("Delete word");
            deleteWordBoxStage.setScene(newWordScene);
            deleteWordBoxStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteWord() {
        if (dictionary.containsKey(wordToDelete.getText())) {
            dictionary.remove(wordToDelete.getText());
            deleteWordBoxStage.close();
        } else {
            warningMessage.setVisible(true);
            warningIcon.setVisible(true);
        }
    }

    public void closeDeleteWordBox() {
        deleteWordBoxStage.close();
    }
}
