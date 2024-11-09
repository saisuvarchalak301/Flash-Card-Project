package com.example.flashlearn;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class FlashcardCreationActivity extends AppCompatActivity {

    private EditText questionEditText, answerEditText, categoryEditText;
    private FirebaseFirestore db;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_creation);

        // Initialize the UI elements
        questionEditText = findViewById(R.id.questionEditText);
        answerEditText = findViewById(R.id.answerEditText);
        categoryEditText = findViewById(R.id.categoryEditText); // Added category EditText
        saveButton = findViewById(R.id.saveButton);

        // Initialize Firestore instance
        db = FirebaseFirestore.getInstance();

        // Save button click listener
        saveButton.setOnClickListener(view -> saveFlashcard());
    }

    // Method to save the flashcard to Firestore
    private void saveFlashcard() {
        String question = questionEditText.getText().toString().trim();
        String answer = answerEditText.getText().toString().trim();
        String category = categoryEditText.getText().toString().trim(); // Get category

        if (!question.isEmpty() && !answer.isEmpty() && !category.isEmpty()) {
            // Create a new FlashCard object
            FlashCard flashcard = new FlashCard(question, answer, category, false);

            // Save to Firestore
            db.collection("flashcards")
                    .add(flashcard)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(FlashcardCreationActivity.this, "Flashcard saved!", Toast.LENGTH_SHORT).show();
                        finish();  // Finish the activity and return to the home screen
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(FlashcardCreationActivity.this, "Error saving flashcard", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Please enter question, answer, and category", Toast.LENGTH_SHORT).show();
        }
    }
}
