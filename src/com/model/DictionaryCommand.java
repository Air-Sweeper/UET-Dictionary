package com.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class DictionaryCommand extends Dictionary {

    private static final Scanner input = new Scanner(System.in);

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
            Scanner inputFile = new Scanner(new File("D:\\Source\\UET-Dictionary\\src\\com\\model\\dictionary.txt"));

            while (inputFile.hasNext()) {
                Word word = new Word();

                String curLine = inputFile.nextLine();
                String[] splitWords = curLine.split("\t");

                word.setWord_target(splitWords[0].trim());
                word.setWord_explain(splitWords[1].trim());

                dictionary.put(word.getWord_target(), word.getWord_explain());
            }
            inputFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void dictionaryDelete() {
        System.out.print("Search for deleting: ");
        String neededWord = input.next();

        if (dictionary.containsKey(neededWord)) {
            dictionary.remove(neededWord);
        } else {
            System.out.println("No matched word found!");
        }
    }

    public static void dictionaryExportToFile() {

        try {
            System.out.println("Enter file name: ");
            String fileName = input.next();
            File exportedDict = new File(fileName);
            if (exportedDict.createNewFile()) {
                FileWriter fileWriter = new FileWriter(exportedDict);
                for(Map.Entry<String, String> word : dictionary.entrySet()) {
                    fileWriter.write(word.getKey() + "\t" + word.getValue() + "\n");
                }
                fileWriter.close();
            } else {
                System.out.println("Can not create new file! File already exists!");
            }
        } catch(IOException exception) {
            System.out.println("An error occurred!");
            exception.printStackTrace();
        }
    }

    public static void importFromFavourite() {

        try {
            File favouriteFile = new File("D:\\Source\\UET-Dictionary\\src\\com\\model\\favourite.txt");
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
}
