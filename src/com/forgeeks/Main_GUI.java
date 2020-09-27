package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_GUI {

    // Call Main_GUI.fxml.
    public static void launchApplication() {

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main_GUI.class.getResource("Main_GUI.fxml"));
            BorderPane rootLayout = loader.load();

            // Show the scene containing the root layout.
            Stage mainWindow = new Stage();
            Scene scene = new Scene(rootLayout);
            mainWindow.setScene(scene);
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Search input word and show related words.
    

    // Show input word's meaning.
}
