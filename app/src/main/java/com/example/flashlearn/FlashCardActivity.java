package com.example.flashlearn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        setContentView(R.layout.activity_flashcard);  // Correct layout reference

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        questionView = findViewById(R.id.flashcard_question);
        answerView = findViewById(R.id.flashcard_answer);
        btnMarkKnown = findViewById(R.id.btn_mark_known);
        btnShuffle = findViewById(R.id.btn_shuffle);

        flashCards = new ArrayList<>();

        // Load flashcards from Firestore
        loadFlashCardsFromFirestore();

        // Set up initial flashcard
        displayFlashCard();

        // Flip the card when clicked
        findViewById(R.id.flashcard_container).setOnClickListener(v -> flipFlashCard());

        // Mark as known
        btnMarkKnown.setOnClickListener(v -> markCardAsKnown());

        // Shuffle flashcards
        btnShuffle.setOnClickListener(v -> shuffleCards());
    }

    private void loadFlashCardsFromFirestore() {
        db.collection("flashcards")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    flashCards.clear();  // Clear any previously loaded cards
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        FlashCard flashCard = document.toObject(FlashCard.class);
                        if (flashCard != null) {
                            flashCard.setId(document.getId()); // Set Firestore document ID
                            flashCards.add(flashCard);
                        }
                    }
                    if (flashCards.size() > 0) {
                        displayFlashCard(); // Show the first card
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(FlashCardActivity.this, "Error loading flashcards", Toast.LENGTH_SHORT).show();
                });
    }

    private void displayFlashCard() {
        if (flashCards.size() > 0) {
            FlashCard currentCard = flashCards.get(currentCardIndex);
            questionView.setText(currentCard.getQuestion());
            answerView.setText(currentCard.getAnswer());
            answerView.setVisibility(View.GONE); // Initially hide the answer
        }
    }

    private void flipFlashCard() {
        // Flip animation to reveal answer
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

    private void markCardAsKnown() {
        if (flashCards.size() > 0) {
            FlashCard currentCard = flashCards.get(currentCardIndex);
            currentCard.setKnown(true); // Mark the card as known
            db.collection("flashcards")
                    .document(currentCard.getId()) // Use the card ID for the document ID
                    .update("isKnown", true)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(FlashCardActivity.this, "Marked as known!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(FlashCardActivity.this, "Error updating", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void shuffleCards() {
        Collections.shuffle(flashCards);
        currentCardIndex = 0; // Reset to the first card
        displayFlashCard();  // Display shuffled cards
    }
}
