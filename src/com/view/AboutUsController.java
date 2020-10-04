package com.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutUsController {

    public static void openAboutUsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditBoxController.class.getResource("AboutUsBox.fxml"));
            AnchorPane aboutUsBoxPane = loader.load();

            Scene aboutUsScene = new Scene(aboutUsBoxPane);
            Stage aboutUsStage = new Stage();
            aboutUsStage.initModality(Modality.APPLICATION_MODAL);
            aboutUsStage.setTitle("About us");
            aboutUsStage.setScene(aboutUsScene);
            aboutUsStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
