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

    private static final String DELETE_BOX_FILE_PATH = "DeleteWordBox.fxml";
    private static final String DELETE_BOX_TITLE = "Delete word";
    private static Stage deleteWordBoxStage;

    @FXML
    private TextField wordToDeleteField;
    @FXML
    private Label warningMessageLabel;
    @FXML
    private FontAwesomeIconView warningIcon;

    public static void openDeleteWordWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditBoxController.class.getResource(DELETE_BOX_FILE_PATH));
            AnchorPane deleteWordBoxPane = loader.load();
            Scene newWordScene = new Scene(deleteWordBoxPane);
            deleteWordBoxStage = new Stage();
            deleteWordBoxStage.initModality(Modality.APPLICATION_MODAL);
            deleteWordBoxStage.setTitle(DELETE_BOX_TITLE);
            deleteWordBoxStage.setScene(newWordScene);
            deleteWordBoxStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteWord() {
        if (dictionary.containsKey(wordToDeleteField.getText())) {
            dictionary.remove(wordToDeleteField.getText());
            deleteWordBoxStage.close();
        } else {
            warningMessageLabel.setVisible(true);
            warningIcon.setVisible(true);
        }
    }

    public void closeDeleteWordBox() {
        deleteWordBoxStage.close();
    }
}
