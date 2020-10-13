package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutUsController {

    private static final String ABOUT_US_BOX_FILE_PATH = "view/AboutUsBox.fxml";
    private static final String ABOUT_US_BOX_TITLE = "About us";

    public static void openAboutUsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditBoxController.class.getResource(ABOUT_US_BOX_FILE_PATH));
            AnchorPane aboutUsBoxPane = loader.load();
            Scene aboutUsScene = new Scene(aboutUsBoxPane);
            Stage aboutUsStage = new Stage();
            aboutUsStage.initModality(Modality.APPLICATION_MODAL);
            aboutUsStage.setTitle(ABOUT_US_BOX_TITLE);
            aboutUsStage.setScene(aboutUsScene);
            aboutUsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
