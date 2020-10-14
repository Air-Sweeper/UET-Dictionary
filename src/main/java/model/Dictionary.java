package main.java.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

public class Dictionary {
    protected static final TreeMap<String, String> dictionary = new TreeMap<>();
    protected static final HashSet<String> favouriteWords = new HashSet<>();
    protected static final HashSet<String> searchedWords = new HashSet<>();
    protected static final List<String> virtualDictionary = new ArrayList<>();
}
