package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.model.Dictionary;

import java.io.IOException;

/**
 * UET Dictionary is an open source Java application.
 * It is very simple and easy to use, created on 26/10/2020.
 * Enjoy!
 * @author Nguyen Minh Thai - sophomore at University of Engineering and Technology
 */

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws IOException {
        loadDatabase();
        launchApplication();
    }

    private void loadDatabase() throws IOException {
        Dictionary.importFromEnglishDictionary();
        Dictionary.importFromBookmark();
        Dictionary.importFromHistory();
        Dictionary.importFromDailyWord();
        Dictionary.updateDailyWords();
    }

    public void launchApplication() {
        SearchEngineController.launchMainInterface();
    }
}
