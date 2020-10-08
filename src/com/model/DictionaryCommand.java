package com.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class DictionaryCommand extends Dictionary {

    private static final Scanner input = new Scanner(System.in);
    private static final String HISTORY_FILE_PATH = "D:\\Source\\UET-Dictionary-TeamVersion\\src\\com\\model\\history.txt";
    private static final String DICTIONARY_FILE_PATH = "D:\\Source\\UET-Dictionary-TeamVersion\\src\\com\\model\\dictionary.txt";
    private static final String FAVOURITE_FILE_PATH =  "D:\\Source\\UET-Dictionary-TeamVersion\\src\\com\\model\\favourite.txt";
    private static final String SPLIT_CHARACTER = "\t";
    private static final int FIRST_INDEX = 1;

    public static void showAllWords() {
        int wordOrder = FIRST_INDEX;
        System.out.printf("%-4s| %-15s| %-15s%n", "No", "English", "Vietnamese");
        for (Map.Entry<String, String> word : dictionary.entrySet()) {
            System.out.printf("%-4d| %-15s| %-15s%n", wordOrder++, word.getKey(), word.getValue());
        }
    }

    public static void importFromFile() {
        try {
            Scanner inputFile = new Scanner(new File(DICTIONARY_FILE_PATH));
            while (inputFile.hasNext()) {
                Word word = new Word();
                String curLine = inputFile.nextLine();
                String[] splitWords = curLine.split(SPLIT_CHARACTER);
                word.setWord_target(splitWords[0].trim());
                word.setWord_explain(splitWords[1].trim());
                dictionary.put(word.getWord_target(), word.getWord_explain());
            }
            inputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
                String favouriteWord = input.nextLine();
                favouriteWords.add(favouriteWord);
            }
            inputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
