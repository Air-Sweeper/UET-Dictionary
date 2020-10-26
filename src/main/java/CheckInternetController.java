package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CheckInternetController {

    private static final String APPLICATION_ICON_PATH = "icon.png";
    private static final String APPLICATION_NAME = "UET-Dictionary";
    private static final String NO_INTERNET_FILE_PATH = "view/fxml/check_internet_box.fxml";

    private static Stage noInternetStage;

    public static void openNoInternetWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CheckInternetController.class.getResource(NO_INTERNET_FILE_PATH));
            AnchorPane noInternetPane = loader.load();
            Scene newWordScene = new Scene(noInternetPane);

            noInternetStage = new Stage();
            noInternetStage.setScene(newWordScene);
            noInternetStage.setTitle(APPLICATION_NAME);
            noInternetStage.initModality(Modality.APPLICATION_MODAL);
            noInternetStage.resizableProperty().setValue(Boolean.FALSE);
            noInternetStage.getIcons().add(new Image(APPLICATION_ICON_PATH));
            noInternetStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeNoInternetBox() {
        noInternetStage.close();
    }
}
