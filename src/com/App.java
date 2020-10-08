package com;

import com.model.DictionaryCommand;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static final String SEARCH_ENGINE_FILE_PATH = "view/SearchEngine.fxml";
    private static final String APPLICATION_NAME = "UET-Dictionary";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        loadDatabase();
        launchApplication();
        showDictionary();
    }

    private void loadDatabase() {
        DictionaryCommand.importFromFile();
        DictionaryCommand.importFromFavourite();
        DictionaryCommand.importFromHistory();
    }

    public void launchApplication() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(SEARCH_ENGINE_FILE_PATH));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            Stage mainWindow = new Stage();

            mainWindow.setTitle(APPLICATION_NAME);
            mainWindow.setScene(scene);
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDictionary() {
        DictionaryCommand.showAllWords();
    }
}
