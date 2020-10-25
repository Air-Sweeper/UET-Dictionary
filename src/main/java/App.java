package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.model.Dictionary;

import java.io.IOException;

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
        Dictionary.importFromDictionary();
        Dictionary.importFromBookmark();
        Dictionary.importFromHistory();
        Dictionary.importFromDailyWord();
        Dictionary.updateDailyWords();
    }

    public void launchApplication() {
        SearchEngineController MainInterface = new SearchEngineController();
        MainInterface.launchMainInterface();
    }
}
