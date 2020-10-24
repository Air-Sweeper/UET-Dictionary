package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutUsController {

    private static final String ABOUT_US_BOX_FILE_PATH = "view/fxml/about_us_box.fxml";
    private static final String ABOUT_US_BOX_TITLE = "About us";
    private static final String APPLICATION_ICON_PATH = "icon.png";

    public static void openAboutUsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditBoxController.class.getResource(ABOUT_US_BOX_FILE_PATH));
            AnchorPane aboutUsBoxPane = loader.load();
            Scene aboutUsScene = new Scene(aboutUsBoxPane);
            Stage aboutUsStage = new Stage();
            aboutUsStage.setScene(aboutUsScene);
            aboutUsStage.setTitle(ABOUT_US_BOX_TITLE);
            aboutUsStage.initModality(Modality.APPLICATION_MODAL);
            aboutUsStage.resizableProperty().setValue(Boolean.FALSE);
            aboutUsStage.getIcons().add(new Image(APPLICATION_ICON_PATH));
            aboutUsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
