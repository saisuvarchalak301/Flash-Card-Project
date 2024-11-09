package com.example.flashlearn;

import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class FlashcardCreationActivity extends AppCompatActivity {
    private EditText questionEditText, answerEditText;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_creation);

        questionEditText = findViewById(R.id.questionEditText);
        answerEditText = findViewById(R.id.answerEditText);
        db = FirebaseFirestore.getInstance();

        findViewById(R.id.saveButton).setOnClickListener(view -> saveFlashcard());
    }

    private void saveFlashcard() {
        String question = questionEditText.getText().toString().trim();
        String answer = answerEditText.getText().toString().trim();

        if (!question.isEmpty() && !answer.isEmpty()) {
            Flashcard flashcard = new Flashcard(question, answer, false);
            db.collection("flashcards").add(flashcard)
                    .addOnSuccessListener(documentReference -> finish())
                    .addOnFailureListener(e -> {
                        // Handle error
                    });
        }
    }
}
