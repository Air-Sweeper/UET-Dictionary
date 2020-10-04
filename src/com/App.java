package com;

import com.model.DictionaryCommand;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        loadDatabase();
        launchApplication();
        showDictionary();
    }

    public void launchApplication() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/SearchEngine.fxml"));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            Stage mainWindow = new Stage();
            mainWindow.setTitle("UET-Dictionary");
            mainWindow.setScene(scene);
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDatabase() {
        DictionaryCommand.importFromFile();
        DictionaryCommand.importFromFavourite();
        DictionaryCommand.importFromHistory();
    }

    public void showDictionary() {
        DictionaryCommand.showAllWords();
    }
}
