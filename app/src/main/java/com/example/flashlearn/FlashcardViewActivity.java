package com.example.flashlearn;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class FlashcardViewActivity extends AppCompatActivity {

    private TextView questionTextView, answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_view);

        // Initialize the TextViews
        questionTextView = findViewById(R.id.questionTextView);
        answerTextView = findViewById(R.id.answerTextView);

        // Get the flashcard ID from the Intent
        String flashcardId = getIntent().getStringExtra("flashcardId");

        if (flashcardId == null) {
            // Show an error if no flashcardId is passed
            Toast.makeText(this, "No flashcard ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch the flashcard data from Firestore
        FirebaseFirestore.getInstance().collection("flashcards")
                .document(flashcardId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        FlashCard flashCard = documentSnapshot.toObject(FlashCard.class);
                        if (flashCard != null) {
                            // Set the question and answer text
                            questionTextView.setText(flashCard.getQuestion());
                            answerTextView.setText(flashCard.getAnswer());
                        } else {
                            Toast.makeText(FlashcardViewActivity.this, "Flashcard not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FlashcardViewActivity.this, "Flashcard does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle error
                    Toast.makeText(FlashcardViewActivity.this, "Error fetching flashcard", Toast.LENGTH_SHORT).show();
                });

        // Toggle the visibility of the answer when the question is clicked
        questionTextView.setOnClickListener(v -> {
            if (answerTextView.getVisibility() == View.GONE) {
                answerTextView.setVisibility(View.VISIBLE);  // Show the answer
            } else {
                answerTextView.setVisibility(View.GONE);     // Hide the answer
            }
        });
    }
}
