package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.Dictionary;

public class EditBoxController extends Dictionary {

    private static final String DICTIONARY_FILE_PATH = "src/main/resources/E_V_dictionary.txt";
    private static final String EDIT_BOX_FILE_PATH = "view/fxml/EditBox.fxml";
    private static final String EDIT_BOX_TITLE = "Edit word";
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
            editBoxStage.initModality(Modality.APPLICATION_MODAL);
            editBoxStage.setTitle(EDIT_BOX_TITLE);
            editBoxStage.setScene(newWordScene);
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
            overrideDictionary();
            closeEditBox();
        }
    }

    public void closeEditBox() {
        editBoxStage.close();
    }
}
