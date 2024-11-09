package com.example.flashlearn;

public class FlashCard {

    private String id;
    private String question;
    private String answer;
    private boolean isKnown;

    // Constructor
    public FlashCard(String id, String question, String answer, boolean isKnown) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.isKnown = isKnown;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public void setKnown(boolean known) {
        isKnown = known;
    }
}
