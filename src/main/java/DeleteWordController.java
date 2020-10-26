package main.java;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.Dictionary;

public class DeleteWordController extends Dictionary {

    private static final String APPLICATION_ICON_PATH = "icon.png";
    private static final String APPLICATION_NAME = "UET-Dictionary";
    private static final String DELETE_BOX_FILE_PATH = "view/fxml/delete_box.fxml";

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
            deleteWordBoxStage.setScene(newWordScene);
            deleteWordBoxStage.setTitle(APPLICATION_NAME);
            deleteWordBoxStage.initModality(Modality.APPLICATION_MODAL);
            deleteWordBoxStage.resizableProperty().setValue(Boolean.FALSE);
            deleteWordBoxStage.getIcons().add(new Image(APPLICATION_ICON_PATH));
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
