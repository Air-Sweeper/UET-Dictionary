package main.java;

import com.voicerss.tts.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import main.java.model.Dictionary;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SearchEngineController extends Dictionary {

    private static final double BORDER_CONSTRAINT = 0.0;

    private static final String API_KEY = "cc4d6af20685439b9b77544383b558fc";
    private static final String APPLICATION_ICON_PATH = "icon.png";
    private static final String APPLICATION_NAME = "UET-Dictionary";
    private static final String AUDIO_OUTPUT_FILE_LOCATION = "src/main/resources/word_pronunciation.wav";
    private static final String BOOKMARKED_COLOR = "-fx-fill: #fce877";
    private static final String CAMBRIDGE_DICTIONARY_URL = "https://dictionary.cambridge.org/vi/dictionary/english/";
    private static final String CAMBRIDGE_TAB_TITLE = "Cambridge dictionary";
    private static final String EMPTY_STRING = "";
    private static final String GOOGLE_TEST_URL = "http://www.google.com";
    private static final String GOOGLE_TRANSLATE_URL = "https://translate.google.com/?hl=vi#view=home&op=translate&sl=auto&tl=vi&text=";
    private static final String GOOGLE_TRANSLATE_TAB_TITLE = "Google Translate";
    private static final String NON_BOOKMARKED_COLOR = "-fx-fill: #9e9e9e";
    private static final String SEARCH_ENGINE_FILE_PATH = "view/fxml/main_engine.fxml";
    private static final String SEARCH_ON_CAMBRIDGE = "Search on Cambridge dictionary";
    private static final String SEARCH_ON_GOOGLE_TRANSLATE = "Search on Google Translate";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static Stage mainWindow;

    ObservableList<String> relatedWordsObservableList = FXCollections.observableArrayList();
    ObservableList <String> bookmarkedWordObservableList = FXCollections.observableArrayList();
    ObservableList<String> searchedWordObservableList = FXCollections.observableArrayList();

    @FXML
    private FontAwesomeIconView warningIcon;
    @FXML
    private FontAwesomeIconView pronunciationIcon;
    @FXML
    private FontAwesomeIconView bookmarkIcon;
    @FXML
    private Label warningMessageLabel;
    @FXML
    private ListView<String> relatedWordList;
    @FXML
    private ListView<String> dictionaryList;
    @FXML
    private ListView<String> historyList;
    @FXML
    private ListView<String> bookmarkedWordList;
    @FXML
    private TabPane webTabPane;
    @FXML
    private TextField wordToSearchField;
    @FXML
    private TextField wordTargetField;
    @FXML
    private TextField todayField;
    @FXML
    private WebView wordDefinitionView;
    @FXML
    private WebView amazingWordView;

    public static void launchMainInterface() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(SEARCH_ENGINE_FILE_PATH));
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            mainWindow = new Stage();
            mainWindow.setScene(scene);
            mainWindow.getIcons().add(new Image(APPLICATION_ICON_PATH));
            mainWindow.setTitle(APPLICATION_NAME);
            mainWindow.setOnCloseRequest(e -> {
                e.consume();
                closeProgram();
            });
            mainWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeProgram() {
        SaveBoxController.openSaveBoxWindow();
    }

    public void exit() {
        mainWindow.close();
    }

    public void closeMainWindow() {
        closeProgram();
    }

    public void search() {
        if (dictionary.containsKey(wordToSearchField.getText())) {
            searchedWords.add(wordToSearchField.getText());
            wordTargetField.setText(wordToSearchField.getText());
            wordDefinitionView.getEngine().loadContent(dictionary.get(wordToSearchField.getText()));
            pronunciationIcon.setVisible(true);
            bookmarkIcon.setVisible(true);
            updateBookmarkIconColor();
        }
    }

    public void showRelatedWordList() {
        String pattern = wordToSearchField.getText();
        if (!pattern.equals("")) {
            relatedWordsObservableList.clear();
            boolean isExisted = false;
            for (Map.Entry<String, String> word : dictionary.entrySet()) {
                if (word.getKey().startsWith(pattern)) {
                    relatedWordsObservableList.add(word.getKey());
                    isExisted = true;
                }
            }
            warningIcon.setVisible(!isExisted);
            warningMessageLabel.setVisible(!isExisted);
            relatedWordList.getItems().clear();
            relatedWordList.getItems().addAll(relatedWordsObservableList);
            relatedWordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            relatedWordList.setCellFactory(listView -> bindContextMenuToDictionaryCell());
        }
    }

    private ListCell<String> bindContextMenuToDictionaryCell() {
        ListCell<String> cell = new ListCell<>();

        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem();
        editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()));
        editItem.setOnAction(event -> {
            String item = cell.getItem();
            EditBoxController.openEditBox(item);
        });

        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
        deleteItem.setOnAction(event -> {
            dictionary.remove(cell.getItem());
            bookmarkedWords.remove(cell.getItem());
            searchedWords.remove(cell.getItem());
            virtualDictionary.remove(cell.getItem());
            relatedWordList.getItems().remove(cell.getItem());
            historyList.getItems().remove(cell.getItem());
            dictionaryList.getItems().remove(cell.getItem());
            bookmarkedWordList.getItems().remove(cell.getItem());
            wordDefinitionView.getEngine().loadContent("");
            pronunciationIcon.setVisible(false);
            bookmarkIcon.setVisible(false);
        });

        MenuItem searchOnCambridgeItem = new MenuItem();
        searchOnCambridgeItem.textProperty().bind(Bindings.format(SEARCH_ON_CAMBRIDGE));
        searchOnCambridgeItem.setOnAction(event -> findOnCambridgeDictionary(cell.getItem()));

        MenuItem searchOnGoogleTranslateItem = new MenuItem();
        searchOnGoogleTranslateItem.textProperty().bind(Bindings.format(SEARCH_ON_GOOGLE_TRANSLATE));
        searchOnGoogleTranslateItem.setOnAction(event -> translateWithGoogleTranslate(cell.getItem()));

        contextMenu.getItems().addAll(editItem, deleteItem, searchOnCambridgeItem, searchOnGoogleTranslateItem);

        cell.textProperty().bind(cell.itemProperty());

        cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                cell.setContextMenu(null);
            } else {
                cell.setContextMenu(contextMenu);
            }
        });
        return cell ;
    }

    private ListCell<String> bindContextMenuToSearchedWordCell() {
        ListCell<String> cell = new ListCell<>();

        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem();
        editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()));
        editItem.setOnAction(event -> {
            String item = cell.getItem();
            EditBoxController.openEditBox(item);
        });

        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Remove \"%s\" from history", cell.itemProperty()));
        deleteItem.setOnAction(event -> {
            searchedWords.remove(cell.getItem());
            historyList.getItems().remove(cell.getItem());
        });

        MenuItem searchOnCambridgeItem = new MenuItem();
        searchOnCambridgeItem.textProperty().bind(Bindings.format(SEARCH_ON_CAMBRIDGE));
        searchOnCambridgeItem.setOnAction(event -> findOnCambridgeDictionary(cell.getItem()));

        MenuItem searchOnGoogleTranslateItem = new MenuItem();
        searchOnGoogleTranslateItem.textProperty().bind(Bindings.format(SEARCH_ON_GOOGLE_TRANSLATE));
        searchOnGoogleTranslateItem.setOnAction(event -> translateWithGoogleTranslate(cell.getItem()));

        contextMenu.getItems().addAll(editItem, deleteItem, searchOnCambridgeItem, searchOnGoogleTranslateItem);

        cell.textProperty().bind(cell.itemProperty());

        cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                cell.setContextMenu(null);
            } else {
                cell.setContextMenu(contextMenu);
            }
        });
        return cell ;
    }

    private ListCell<String> bindContextMenuToBookmarkWordCell() {
        ListCell<String> cell = new ListCell<>();

        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem();
        editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()));
        editItem.setOnAction(event -> {
            String item = cell.getItem();
            EditBoxController.openEditBox(item);
        });

        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Remove \"%s\" from bookmark", cell.itemProperty()));
        deleteItem.setOnAction(event -> {
            bookmarkedWords.remove(cell.getItem());
            bookmarkedWordList.getItems().remove(cell.getItem());
        });

        MenuItem searchOnCambridgeItem = new MenuItem();
        searchOnCambridgeItem.textProperty().bind(Bindings.format(SEARCH_ON_CAMBRIDGE));
        searchOnCambridgeItem.setOnAction(event -> findOnCambridgeDictionary(cell.getItem()));

        MenuItem searchOnGoogleTranslateItem = new MenuItem();
        searchOnGoogleTranslateItem.textProperty().bind(Bindings.format(SEARCH_ON_GOOGLE_TRANSLATE));
        searchOnGoogleTranslateItem.setOnAction(event -> translateWithGoogleTranslate(cell.getItem()));

        contextMenu.getItems().addAll(editItem, deleteItem, searchOnCambridgeItem, searchOnGoogleTranslateItem);

        cell.textProperty().bind(cell.itemProperty());

        cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                cell.setContextMenu(null);
            } else {
                cell.setContextMenu(contextMenu);
            }
        });
        return cell ;
    }

    public void getDefinitionFromRelatedWordList() {
        String selectedWord = relatedWordList.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            searchedWords.add(selectedWord);
            wordTargetField.setText(selectedWord);
            wordDefinitionView.getEngine().loadContent(dictionary.get(selectedWord));
            pronunciationIcon.setVisible(true);
            bookmarkIcon.setVisible(true);
            updateBookmarkIconColor();
        }
    }

    public void getDefinitionFromHistoryList() {
        String selectedWord = historyList.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            wordTargetField.setText(selectedWord);
            wordDefinitionView.getEngine().loadContent(dictionary.get(selectedWord));
            pronunciationIcon.setVisible(true);
            bookmarkIcon.setVisible(true);
            updateBookmarkIconColor();
        }
    }

    public void getDefinitionFromBookmarkList() {
        String selectedWord = bookmarkedWordList.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            searchedWords.add(selectedWord);
            wordTargetField.setText(selectedWord);
            wordDefinitionView.getEngine().loadContent(dictionary.get(selectedWord));
            pronunciationIcon.setVisible(true);
            bookmarkIcon.setVisible(true);
            updateBookmarkIconColor();
        }
    }

    public void getDefinitionFromDictionaryList() {
        String selectedWord = dictionaryList.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            searchedWords.add(selectedWord);
            wordTargetField.setText(selectedWord);
            wordDefinitionView.getEngine().loadContent(dictionary.get(selectedWord));
            pronunciationIcon.setVisible(true);
            bookmarkIcon.setVisible(true);
            updateBookmarkIconColor();
        }
    }

    public void cambridgeHelp() {
        String wordToFind = wordToSearchField.getText();
        findOnCambridgeDictionary(wordToFind);
    }

    private void findOnCambridgeDictionary(String wordToFind) {
        try {
            URL url = new URL(GOOGLE_TEST_URL);
            URLConnection connection = url.openConnection();
            connection.connect();

            WebView cambridgeView = new WebView();
            AnchorPane cambridgePane = new AnchorPane(cambridgeView);
            AnchorPane.setTopAnchor(cambridgeView, BORDER_CONSTRAINT);
            AnchorPane.setBottomAnchor(cambridgeView, BORDER_CONSTRAINT);
            AnchorPane.setLeftAnchor(cambridgeView, BORDER_CONSTRAINT);
            AnchorPane.setRightAnchor(cambridgeView, BORDER_CONSTRAINT);

            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem();
            menuItem.setText("Close");
            menuItem.setOnAction(e -> closeTab());
            contextMenu.getItems().add(menuItem);

            Tab cambridgeTab = new Tab(CAMBRIDGE_TAB_TITLE);
            cambridgeTab.setContent(cambridgePane);
            cambridgeTab.setContextMenu(contextMenu);

            webTabPane.getTabs().add(cambridgeTab);
            cambridgeView.getEngine().load( CAMBRIDGE_DICTIONARY_URL + wordToFind );
        } catch (IOException e) {
            CheckInternetController.openNoInternetWindow();
        }
    }

    public void googleHelp() {
        String wordToFind = wordToSearchField.getText();
        translateWithGoogleTranslate(wordToFind);
    }

    private void translateWithGoogleTranslate(String wordToFind) {
        try {
            URL url = new URL(GOOGLE_TEST_URL);
            URLConnection connection = url.openConnection();
            connection.connect();

            WebView googleTranslateView = new WebView();
            AnchorPane googleTranslationPane = new AnchorPane(googleTranslateView);
            AnchorPane.setTopAnchor(googleTranslateView, BORDER_CONSTRAINT);
            AnchorPane.setBottomAnchor(googleTranslateView, BORDER_CONSTRAINT);
            AnchorPane.setLeftAnchor(googleTranslateView, BORDER_CONSTRAINT);
            AnchorPane.setRightAnchor(googleTranslateView, BORDER_CONSTRAINT);

            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem();
            menuItem.setText("Close");
            menuItem.setOnAction(e -> closeTab());
            contextMenu.getItems().add(menuItem);

            Tab googleTranslationTab = new Tab(GOOGLE_TRANSLATE_TAB_TITLE);
            googleTranslationTab.setContent(googleTranslationPane);
            googleTranslationTab.setContextMenu(contextMenu);

            webTabPane.getTabs().add(googleTranslationTab);
            googleTranslateView.getEngine().load( GOOGLE_TRANSLATE_URL + wordToFind );
        } catch (IOException e) {
            CheckInternetController.openNoInternetWindow();
        }
    }

    private void closeTab() {
        Tab selectedTab = webTabPane.getSelectionModel().getSelectedItem();
        Tab definitionTab = webTabPane.getTabs().get(0);
        if (selectedTab.hashCode() != definitionTab.hashCode()) {
            webTabPane.getTabs().remove(selectedTab);
        }
    }

    public void clearTextField() {
        if (!wordToSearchField.getText().equals(EMPTY_STRING)) {
            wordToSearchField.clear();
        }
    }

    public void textToSpeech() throws Exception {
        try {
            String wordTarget = wordTargetField.getText();
            VoiceProvider tts = new VoiceProvider(API_KEY);

            VoiceParameters params = new VoiceParameters(wordTarget, Languages.English_UnitedStates);
            params.setCodec(AudioCodec.WAV);
            params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
            params.setBase64(false);
            params.setSSML(false);
            params.setRate(0);

            byte[] voice = tts.speech(params);

            FileOutputStream fos = new FileOutputStream(AUDIO_OUTPUT_FILE_LOCATION);
            fos.write(voice, 0, voice.length);
            fos.flush();
            fos.close();

            InputStream in = new FileInputStream(AUDIO_OUTPUT_FILE_LOCATION);
            AudioStream sound = new AudioStream(in);
            AudioPlayer.player.start(sound);
        } catch (IOException e) {
            e.printStackTrace();
            CheckInternetController.openNoInternetWindow();
        }
    }

    public void showHistorySearch() {
        searchedWordObservableList.clear();
        searchedWordObservableList.addAll(searchedWords);
        historyList.getItems().clear();
        historyList.getItems().addAll(searchedWordObservableList);
        historyList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        historyList.setCellFactory(listView -> bindContextMenuToSearchedWordCell());
    }

    public void clearHistory() {
        searchedWords.clear();
        searchedWordObservableList.clear();
        historyList.getItems().clear();
        historyList.getItems().addAll(searchedWordObservableList);
        historyList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void showDailyWord() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime now = LocalDateTime.now();
        String today = dtf.format(now);
        amazingWordView.getEngine().loadContent(dictionary.get(dailyWords.get(today)));
        todayField.setText(today);
    }

    public void showDictionary() {
        ObservableList<String> dictionaryObservableList = FXCollections.observableArrayList();
        dictionaryObservableList.addAll(virtualDictionary);
        dictionaryList.getItems().clear();
        dictionaryList.getItems().addAll(dictionaryObservableList);
        dictionaryList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        dictionaryList.setCellFactory(listView -> bindContextMenuToDictionaryCell());
    }

    private void updateBookmarkIconColor() {
        if (bookmarkedWords.contains(wordTargetField.getText())) {
            bookmarkIcon.setStyle(BOOKMARKED_COLOR);
        } else {
            bookmarkIcon.setStyle(NON_BOOKMARKED_COLOR);
        }
    }

    public void updateBookmarkedWords() {
        if (bookmarkedWords.contains(wordTargetField.getText())) {
            bookmarkedWords.remove(wordTargetField.getText());
        } else {
            bookmarkedWords.add(wordTargetField.getText());
        }
        updateBookmarkIconColor();
    }

    public void showBookmark() {
        bookmarkedWordObservableList.clear();
        bookmarkedWordObservableList.addAll(bookmarkedWords);
        bookmarkedWordList.getItems().clear();
        bookmarkedWordList.getItems().addAll(bookmarkedWordObservableList);
        bookmarkedWordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        bookmarkedWordList.setCellFactory(listView -> bindContextMenuToBookmarkWordCell());
    }

    public void clearBookmark() {
        bookmarkedWords.clear();
        bookmarkedWordObservableList.clear();
        bookmarkedWordList.getItems().clear();
        bookmarkedWordList.getItems().addAll(bookmarkedWordObservableList);
        bookmarkedWordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void exportDictionary() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(mainWindow);
        exportDictionaryFile(selectedDirectory.getAbsolutePath());
    }

    public void exportBookmark() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(mainWindow);
        exportBookmarkFile(selectedDirectory.getAbsolutePath());
    }

    public void openAddNewWordWindow() {
        NewWordBoxController.openNewWordBox();
    }

    public void openEditWordWindow() {
        if (!wordToSearchField.getText().equals("")){
            EditBoxController.openEditBox(wordToSearchField.getText());
        }
    }

    public void openDeleteWordWindow() {
        DeleteWordController.openDeleteWordWindow();
    }

    public void openAboutUsWindow() {
        AboutUsController.openAboutUsWindow();
    }
}