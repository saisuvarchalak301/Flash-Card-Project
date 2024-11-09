package com.example.flashlearn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Collections;

public class FlashCardActivity extends AppCompatActivity {

    private TextView questionView, answerView;
    private Button btnMarkKnown, btnShuffle;
    private ArrayList<FlashCard> flashCards;
    private int currentCardIndex = 0;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Correct layout for FlashCardActivity
        setContentView(R.layout.activity_flashcard);  // Use the correct layout file

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        questionView = findViewById(R.id.flashcard_question);
        answerView = findViewById(R.id.flashcard_answer);
        btnMarkKnown = findViewById(R.id.btn_mark_known);
        btnShuffle = findViewById(R.id.btn_shuffle);

        flashCards = new ArrayList<>();

        // Example: Adding Flashcards (You can fetch them from Firebase)
        flashCards.add(new FlashCard("What is Android?", "Android is an OS for mobile devices."));
        flashCards.add(new FlashCard("What is Java?", "Java is a programming language."));

        // Set up initial flashcard
        displayFlashCard();

        // Flip the card when clicked
        findViewById(R.id.flashcard_container).setOnClickListener(v -> flipFlashCard());

        // Mark as known
        btnMarkKnown.setOnClickListener(v -> markCardAsKnown());

        // Shuffle flashcards
        btnShuffle.setOnClickListener(v -> shuffleCards());
    }

    // Display current flashcard question and answer
    private void displayFlashCard() {
        if (flashCards.size() > 0) {
            FlashCard currentCard = flashCards.get(currentCardIndex);
            questionView.setText(currentCard.getQuestion());
            answerView.setText(currentCard.getAnswer());
        }
    }

    // Flip the flashcard to show answer or question
    private void flipFlashCard() {
        if (answerView.getVisibility() == View.VISIBLE) {
            // Flip back to question
            questionView.setVisibility(View.VISIBLE);
            answerView.setVisibility(View.GONE);
        } else {
            // Flip to answer
            questionView.setVisibility(View.GONE);
            answerView.setVisibility(View.VISIBLE);
        }
    }

    // Mark the current flashcard as known
    private void markCardAsKnown() {
        FlashCard currentCard = flashCards.get(currentCardIndex);
        currentCard.setKnown(true);  // Mark the card as known

        // Update Firestore
        db.collection("flashcards")
                .document(String.valueOf(currentCardIndex))  // Using the card index as the document ID
                .update("isKnown", true)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(FlashCardActivity.this, "Marked as known!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(FlashCardActivity.this, "Error updating", Toast.LENGTH_SHORT).show();
                });
    }

    // Shuffle flashcards and reset to the first card
    private void shuffleCards() {
        Collections.shuffle(flashCards);
        currentCardIndex = 0;  // Reset to the first card
        displayFlashCard();  // Display shuffled cards
    }
}
