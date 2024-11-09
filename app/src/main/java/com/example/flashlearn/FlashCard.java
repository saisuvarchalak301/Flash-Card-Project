package com.example.flashlearn;

public class FlashCard {

    private String id;  // Unique identifier for each flashcard
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

    // Getters and setters
    public String getId() {
        return id;  // Return the id
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public void setKnown(boolean known) {
        isKnown = known;
    }
}
