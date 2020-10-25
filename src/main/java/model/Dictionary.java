package main.java.model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Dictionary {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DICTIONARY_FILE_PATH = "src/main/resources/E_V_dictionary.txt";
    private static final String DAILY_WORD_FILE_PATH = "src/main/resources/daily-word.txt";
    private static final String FAVOURITE_FILE_PATH =  "src/main/resources/bookmark.txt";
    private static final String HISTORY_FILE_PATH = "src/main/resources/history.txt";
    private static final String SPLITTING_CHARACTER_01 = "<html>";
    private static final String SPLITTING_CHARACTER_02 = "\t";

    protected static final Map<String, String> dictionary = new TreeMap<>();
    protected static final Map<String, String> dailyWords = new LinkedHashMap<>();
    protected static final Set<String> bookmarkedWords = new HashSet<>();
    protected static final Set<String> searchedWords = new HashSet<>();
    protected static final List<String> virtualDictionary = new ArrayList<>();

    private static long dictionary_old_size;
    private static long searchedWords_old_size;
    private static long bookmark_old_size;

    public static void importFromDictionary() throws IOException {
        FileReader fis = new FileReader(DICTIONARY_FILE_PATH);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(SPLITTING_CHARACTER_01);
            String wordTarget = parts[0];
            String wordDefinition = SPLITTING_CHARACTER_01 + parts[1];
            if (dictionary.containsKey(wordTarget)) {
                dictionary.replace(wordTarget, wordDefinition);
            } else {
                dictionary.put(wordTarget, wordDefinition);
                virtualDictionary.add(wordTarget);
            }
        }
        fis.close();
        dictionary_old_size = dictionary.size();
    }

    public static void importFromHistory() {
        try {
            File historyFile = new File(HISTORY_FILE_PATH);
            Scanner inputFile = new Scanner(historyFile);
            while (inputFile.hasNext()) {
                String word = inputFile.nextLine();
                searchedWords.add(word);
            }
            inputFile.close();
            searchedWords_old_size = searchedWords.size();
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
            bookmark_old_size = bookmarkedWords.size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void importFromDailyWord() throws IOException {
        Scanner scan = new Scanner(new File(DAILY_WORD_FILE_PATH));
        while(scan.hasNext()){
            String curLine = scan.nextLine();
            String[] split = curLine.split(SPLITTING_CHARACTER_02);
            String date = split[0].trim();
            String wordTarget = split[1].trim();
            dailyWords.put(date, wordTarget);
        }
        scan.close();
        System.out.println(dailyWords);
    }

    public void updateBookmark() {
        if (bookmarkedWords.size() != bookmark_old_size) {
            try {
                File favouriteFile = new File(FAVOURITE_FILE_PATH);
                FileWriter fileWriter = new FileWriter(favouriteFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for(String favouriteWord : bookmarkedWords) {
                    bufferedWriter.write(favouriteWord + "\n");
                }
                bufferedWriter.close();
            } catch(IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void updateDictionary() {
        if (dictionary.size() != dictionary_old_size) {
            try {
                File dictionaryFile = new File(DICTIONARY_FILE_PATH);
                FileWriter fileWriter = new FileWriter(dictionaryFile);
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

    public void updateHistory() {
        if (searchedWords.size() != searchedWords_old_size) {
            try {
                File historyFile = new File(HISTORY_FILE_PATH);
                FileWriter fileWriter = new FileWriter(historyFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                for(String searchedWord : searchedWords) {
                    bufferedWriter.write(searchedWord + "\n");
                }
                bufferedWriter.close();
            } catch(IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static void updateDailyWords() {
        String today = getDate();
        String todayWord = getTodayWord();
        if (!dailyWords.containsKey(today)) {
            File log = new File(DAILY_WORD_FILE_PATH);
            try{
                FileWriter fileWriter = new FileWriter(log, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(today + SPLITTING_CHARACTER_02 + todayWord + "\n");
                bufferedWriter.close();
                dailyWords.put(today, todayWord);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected static String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private static String getTodayWord() {
        Random randomIndex = new Random();
        int upperbound = virtualDictionary.size();
        int randomWordIndex = randomIndex.nextInt(upperbound);
        return virtualDictionary.get(randomWordIndex);
    }

    public static void exportDictionaryFile(String DIRECTORY_PATH) {
        try {
            File exportedFile = new File(DIRECTORY_PATH, "exported-dictionary.txt");
            FileWriter fileWriter = new FileWriter(exportedFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Map.Entry<String, String> word : dictionary.entrySet()){
                bufferedWriter.write(word.getKey() + word.getValue() + "\n");
            }
            bufferedWriter.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void exportBookmarkFile(String DIRECTORY_PATH) {
        try {
            File exportedFile = new File(DIRECTORY_PATH, "exported-bookmark.txt");
            FileWriter fileWriter = new FileWriter(exportedFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String bookmark : bookmarkedWords){
                bufferedWriter.write(bookmark + dictionary.get(bookmark) + "\n");
            }
            bufferedWriter.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
