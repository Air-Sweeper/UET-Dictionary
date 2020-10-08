package com.model;

public class Word {
    private String word_target;
    private String word_explain;

    public void setWord_target(String newWord) {
        this.word_target = newWord;
    }

    public void setWord_explain(String newWord_explain) {
        this.word_explain = newWord_explain;
    }

    public String getWord_target() {
        return this.word_target;
    }

    public String getWord_explain() {
        return this.word_explain;
    }
}
