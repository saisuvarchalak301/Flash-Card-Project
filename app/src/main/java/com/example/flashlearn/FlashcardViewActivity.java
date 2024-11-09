package com.example.flashlearn;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class FlashcardViewActivity extends AppCompatActivity {

    private TextView questionTextView, answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_view);

        // Initialize the TextViews
        questionTextView = findViewById(R.id.questionTextView);
        answerTextView = findViewById(R.id.answerTextView);

        // Get the flashcard ID passed through the intent
        String flashcardId = getIntent().getStringExtra("flashcard_id");

        // Fetch the flashcard from Firebase using the flashcardId
        fetchFlashcardFromFirestore(flashcardId);
    }

    private void fetchFlashcardFromFirestore(String flashcardId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Fetch flashcard from Firestore
        db.collection("flashcards").document(flashcardId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Get the flashcard data from Firestore
                        FlashCard flashCard = documentSnapshot.toObject(FlashCard.class);

                        // Set the question and answer on the TextViews
                        if (flashCard != null) {
                            questionTextView.setText(flashCard.getQuestion());
                            answerTextView.setText(flashCard.getAnswer());
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }
}
