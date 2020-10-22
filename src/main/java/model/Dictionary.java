package main.java.model;

import java.io.*;
import java.util.*;

public class Dictionary {

    private static final String HISTORY_FILE_PATH = "src/main/resources/history.txt";
    private static final String DICTIONARY_FILE_PATH = "src/main/resources/E_V_dictionary.txt";
    private static final String FAVOURITE_FILE_PATH =  "src/main/resources/bookmark.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";

    protected static final Map<String, String> dictionary = new TreeMap<>();
    protected static final Set<String> bookmarkedWords = new HashSet<>();
    protected static final Set<String> searchedWords = new HashSet<>();
    protected static final List<String> virtualDictionary = new ArrayList<>();

    public static void importFromDictionary() throws IOException {
        FileReader fis = new FileReader(DICTIONARY_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String wordTarget = parts[0];
            String wordDefinition = SPLITTING_CHARACTERS + parts[1];
            if (dictionary.containsKey(wordTarget)) {
                dictionary.replace(wordTarget, wordDefinition);
            } else {
                dictionary.put(wordTarget, wordDefinition);
                virtualDictionary.add(wordTarget);
            }
        }
    }

    public static void importFromHistory() {
        try {
            File historyFile = new File(HISTORY_FILE_PATH);
            Scanner inputFile = new Scanner(historyFile);
            while (inputFile.hasNext()) {
                String word = inputFile.nextLine();
                searchedWords.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void importFromBookmark() {
        try {
            File favouriteFile = new File(FAVOURITE_FILE_PATH);
            Scanner inputFile = new Scanner(favouriteFile);
            while (inputFile.hasNext()) {
                String favouriteWord = inputFile.nextLine();
                bookmarkedWords.add(favouriteWord);
            }
            inputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void updateBookmark() {
        try {
            File exportedDict = new File(FAVOURITE_FILE_PATH);
            FileWriter fileWriter = new FileWriter(exportedDict);
            for(String favouriteWord : bookmarkedWords) {
                fileWriter.write(favouriteWord + "\n");
            }
            fileWriter.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public void overrideDictionary() {
        try {
            File exportedDict = new File(DICTIONARY_FILE_PATH);
            FileWriter fileWriter = new FileWriter(exportedDict);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Map.Entry<String, String> word : dictionary.entrySet()){
                bufferedWriter.write(word.getKey() + word.getValue() + "\n");
            }
            bufferedWriter.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
