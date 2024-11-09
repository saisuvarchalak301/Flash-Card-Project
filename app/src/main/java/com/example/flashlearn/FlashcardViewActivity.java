package com.example.flashlearn;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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
        String flashcardId = getIntent().getStringExtra("flashcardId");

        // Fetch the flashcard from Firebase using the flashcardId (you can implement this logic)
        // For now, assume we have a dummy flashcard object
        FlashCard flashCard = getFlashCardById(flashcardId);

        // Set the question and answer
        questionTextView.setText(flashCard.getQuestion());
        answerTextView.setText(flashCard.getAnswer());
    }

    private FlashCard getFlashCardById(String flashcardId) {
        // You can implement Firebase fetching logic here
        // For now, returning a dummy flashcard
        return new FlashCard("Dummy Question", "Dummy Answer");
    }
}
