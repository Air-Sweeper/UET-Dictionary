package main.java.model;

import java.util.*;

public class Dictionary {
    protected static final Map<String, String> dictionary = new TreeMap<>();
    protected static final Set<String> bookmarkedWords = new HashSet<>();
    protected static final Set<String> searchedWords = new HashSet<>();
    protected static final List<String> virtualDictionary = new ArrayList<>();
}
