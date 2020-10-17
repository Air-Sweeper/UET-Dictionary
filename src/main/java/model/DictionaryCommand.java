package main.java.model;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class DictionaryCommand extends Dictionary {

    private static final String HISTORY_FILE_PATH = "src/main/resources/history.txt";
    private static final String DICTIONARY_FILE_PATH = "src/main/resources/E_V_dictionary.txt";
    private static final String FAVOURITE_FILE_PATH =  "src/main/resources/favourite.txt";
    private static final String SPLITTING_CHARACTERS = "<html>";

    /*public static void importFromFile() {
        try {
            Scanner inputFile = new Scanner(new File(DICTIONARY_FILE_PATH));
            while (inputFile.hasNext()) {
                Word word = new Word();
                String curLine = inputFile.nextLine();
                String[] splitWords = curLine.split(SPLITTING_CHARACTERS);
                word.setWord_target(splitWords[0].trim());
                word.setWord_explain(splitWords[1].trim());
                dictionary.put(word.getWord_target(), word.getWord_explain());
                virtualDictionary.add(word.getWord_target());
            }
            inputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    public static void importFromDictionary() throws IOException {
        FileReader fis = new FileReader(DICTIONARY_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTERS);
            String wordTarget = parts[0];
            String wordDefinition = SPLITTING_CHARACTERS + parts[1];
            Word word = new Word(wordTarget, wordDefinition);
            dictionary.put(wordTarget, wordDefinition);
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

    public static void importFromFavourite() {
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

    public static void updateFavourite() {
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

    public static void dictionaryExportToFile() {
        try {
            File exportedDict = new File(DICTIONARY_FILE_PATH);
            FileWriter fileWriter = new FileWriter(exportedDict);
            for(Map.Entry<String, String> word : dictionary.entrySet()) {
                fileWriter.write(word.getKey() + "\t" + word.getValue() + "\n");
            }
            fileWriter.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

}
