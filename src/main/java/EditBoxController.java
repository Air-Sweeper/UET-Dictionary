package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.Dictionary;

public class EditBoxController extends Dictionary {

    private static final String EDIT_BOX_FILE_PATH = "view/fxml/edit_box.fxml";
    private static final String APPLICATION_ICON_PATH = "icon.png";
    private static final String APPLICATION_NAME = "UET-Dictionary";

    private static String wordTarget;
    private static Stage editBoxStage;

    @FXML
    private TextField newDefinitionField;

    public static void openEditBox(String wordToEdit) {
        try {
            wordTarget = wordToEdit;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditBoxController.class.getResource(EDIT_BOX_FILE_PATH));
            AnchorPane newWordPane = loader.load();
            Scene newWordScene = new Scene(newWordPane);
            editBoxStage = new Stage();
            editBoxStage.setScene(newWordScene);
            editBoxStage.setTitle(APPLICATION_NAME);
            editBoxStage.initModality(Modality.APPLICATION_MODAL);
            editBoxStage.resizableProperty().setValue(Boolean.FALSE);
            editBoxStage.getIcons().add(new Image(APPLICATION_ICON_PATH));
            editBoxStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editWord() {
        if (!newDefinitionField.getText().equals("")) {
            String newDefinition = "<html><i>"
                    + wordTarget
                    + "</i><br/><ul><li><font color='#cc0000'><b>"
                    + newDefinitionField.getText()
                    + "</b></font></li></ul></html>";
            dictionary.replace(wordTarget, newDefinition);
            closeEditBox();
        }
    }

    public void closeEditBox() {
        editBoxStage.close();
    }
}
