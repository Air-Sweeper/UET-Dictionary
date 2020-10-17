package main.java;

import com.voicerss.tts.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.java.model.Dictionary;
import main.java.model.DictionaryCommand;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.util.Map;
import java.util.Random;

public class SearchEngineController extends Dictionary {
    
    private static final String HISTORY_FILE_PATH = "src/main/resources/history.txt";
    private static final String EMPTY_STRING = "";
    private static final String API_KEY = "cc4d6af20685439b9b77544383b558fc";
    private static final String AUDIO_OUTPUT_FILE_LOCATION = "src/main/resources/word_pronunciation.wav";
    private static final String BOOKMARKED_COLOR = "-fx-fill: #fce877";
    private static final String NON_BOOKMARKED_COLOR = "-fx-fill: #9e9e9e";

    ObservableList<String> relatedWords = FXCollections.observableArrayList();

    @FXML
    private Label warningMessageLabel;
    @FXML
    private FontAwesomeIconView warningIcon;
    @FXML
    private FontAwesomeIconView pronunciationIcon;
    @FXML
    private FontAwesomeIconView bookmarkIcon;
    @FXML
    private TextField wordToSearchField;
    @FXML
    private TextField amazingWordField;
    @FXML
    private TextField amazingWordMeaningField;
    @FXML
    private TextField wordTargetField;
    @FXML
    private TextArea wordDefinitionArea;
    @FXML
    private TextArea wordHistoryArea;
    @FXML
    private TextArea allWordsFromDictionaryArea;
    @FXML
    private TextArea favouriteListArea;
    @FXML
    private TitledPane historyPane;
    @FXML
    private ListView<String> relatedWordList;

    public void searchForWord() {
        getDefinition();
        showRelatedWordList();
    }

    public void getDefinition() {
        if (dictionary.containsKey(wordToSearchField.getText())) {
            wordTargetField.setText(wordToSearchField.getText());
            wordDefinitionArea.setText(dictionary.get(wordToSearchField.getText()));
            pronunciationIcon.setVisible(true);
            bookmarkIcon.setVisible(true);
            updateBookmarkIconColor();
            if (!searchedWords.contains(wordToSearchField.getText())){
                addToHistory();
            }
        }
    }

    public void showRelatedWordList() {
        String pattern = wordToSearchField.getText();
        if (!pattern.equals("")) {
            relatedWords.clear();
            boolean isExisted = false;
            for (Map.Entry<String, String> word : dictionary.entrySet()) {
                if (word.getKey().startsWith(pattern)) {
                    relatedWords.add(word.getKey());
                    isExisted = true;
                }
            }
            warningIcon.setVisible(!isExisted);
            warningMessageLabel.setVisible(!isExisted);
            relatedWordList.getItems().clear();
            relatedWordList.getItems().addAll(relatedWords);
            relatedWordList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
    }

    public void getDefinitionOfSelectedWord() {
        String selectedWord = relatedWordList.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            wordTargetField.setText(selectedWord);
            wordDefinitionArea.setText(dictionary.get(selectedWord));
            pronunciationIcon.setVisible(true);
            bookmarkIcon.setVisible(true);
            updateBookmarkIconColor();
            addToHistory();
        }
    }

    public void clearTextField() {
        if (canBeDeleted()) {
            wordToSearchField.clear();
        }
    }

    public void pronounceWord() throws Exception {
        textToSpeech();
        playPronunciationFile();
    }

    public void textToSpeech() throws Exception {
        String wordTarget = relatedWordList.getSelectionModel().getSelectedItem();
        if (wordTarget == null) {
            wordTarget = wordToSearchField.getText();
        }
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
    }

    public void playPronunciationFile() throws IOException {
        InputStream in = new FileInputStream(AUDIO_OUTPUT_FILE_LOCATION);
        AudioStream sound = new AudioStream(in);
        AudioPlayer.player.start(sound);
    }

    public void addToHistory() {
        try {
            String selectedWord;
            if (relatedWordList.getSelectionModel().getSelectedItem() == null) {
                selectedWord = wordToSearchField.getText();
            } else {
                selectedWord = relatedWordList.getSelectionModel().getSelectedItem();
            }
            if (!searchedWords.contains(selectedWord)) {
                searchedWords.add(selectedWord);
                FileWriter fileWriter = new FileWriter(HISTORY_FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(selectedWord+"\n");
                bufferedWriter.close();
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public void showHistorySearch() {
        StringBuilder result = new StringBuilder();
        for (String word : searchedWords) {
            result.append(word).append("\n");
        }
        wordHistoryArea.setText(result.toString());
        historyPane.setContent(wordHistoryArea);
    }

    public void generateAmazingWord() {
        if (amazingWordField.getText().equals("")) {
            Random randomIndex = new Random();
            int upperbound = virtualDictionary.size();
            int randomWordIndex = randomIndex.nextInt(upperbound);
            String amazingWord = virtualDictionary.get(randomWordIndex);
            String amazingWordMeaning = dictionary.get(amazingWord);
            amazingWordField.setText(amazingWord);
            amazingWordMeaningField.setText(amazingWordMeaning);
        } else {
            amazingWordField.clear();
            amazingWordMeaningField.clear();
        }
    }

    public void showDictionary() {
        StringBuilder allWordsFromDictionary = new StringBuilder();
        for (Map.Entry<String, String> word : dictionary.entrySet()) {
            allWordsFromDictionary.append(word.getKey()).append("\n");
        }
        allWordsFromDictionaryArea.setText(allWordsFromDictionary.toString());
    }

    public void updateBookmarkIconColor() {
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
        DictionaryCommand.updateFavourite();
    }

    public void showFavourite() {
        StringBuilder favouriteList = new StringBuilder();
        for (String word : bookmarkedWords) {
            favouriteList.append(word).append("\n");
        }
        favouriteListArea.setText(favouriteList.toString());
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

    public boolean canBeDeleted() {
        return !wordToSearchField.getText().equals(EMPTY_STRING);
    }
}
