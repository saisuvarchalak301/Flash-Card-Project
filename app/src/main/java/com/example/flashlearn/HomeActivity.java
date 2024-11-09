package com.example.flashlearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlashCardAdapter flashCardAdapter;
    private ArrayList<FlashCard> flashcards;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycler_view);  // Updated to match XML ID
        flashcards = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        flashCardAdapter = new FlashCardAdapter(flashcards, this);
        recyclerView.setAdapter(flashCardAdapter);

        // Load flashcards from Firestore
        loadFlashcards();

        // Floating Action Button (FAB) to create a new flashcard
        findViewById(R.id.fab_add).setOnClickListener(view -> {  // Updated to match XML ID
            Intent intent = new Intent(HomeActivity.this, FlashcardCreationActivity.class);
            startActivity(intent);
        });
    }

    private void loadFlashcards() {
        db.collection("flashcards")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    flashcards.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        FlashCard flashCard = document.toObject(FlashCard.class);
                        if (flashCard != null) {
                            flashCard.setId(document.getId());
                            flashcards.add(flashCard);
                        }
                    }
                    flashCardAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(HomeActivity.this, "Error loading flashcards", Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); // Log the error for debugging purposes
                });
    }
}
