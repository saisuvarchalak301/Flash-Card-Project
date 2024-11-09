package com.example.flashlearn;

public class FlashCard {

    private String question;
    private String answer;
    private boolean isKnown;

    // Constructor
    public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.isKnown = false;  // Default to false when the card is created
    }

    // Getters and Setters
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

    // Setter for isKnown
    public void setKnown(boolean isKnown) {
        this.isKnown = isKnown;
    }
}
