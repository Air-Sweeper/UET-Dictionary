package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SaveBoxController extends SearchEngineController {

    private static final String SAVE_BOX_FILE_PATH = "view/fxml/save_box.fxml";
    private static final String APPLICATION_ICON_PATH = "icon.png";
    private static final String APPLICATION_NAME = "UET Dictionary";

    private static Stage saveBoxWindow;

    public static void openSaveBoxWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(SAVE_BOX_FILE_PATH));
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            saveBoxWindow = new Stage();
            saveBoxWindow.setScene(scene);
            saveBoxWindow.setTitle(APPLICATION_NAME);
            saveBoxWindow.resizableProperty().setValue(Boolean.FALSE);
            saveBoxWindow.getIcons().add(new Image(APPLICATION_ICON_PATH));
            saveBoxWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllWork() {
        updateDictionary();
        updateHistory();
        updateBookmark();
        closeSaveBoxWindow();
        exit();
    }

    public void ignoreEverything() {
        closeSaveBoxWindow();
        exit();
    }

    public void closeSaveBoxWindow() {
        saveBoxWindow.close();
    }
}
