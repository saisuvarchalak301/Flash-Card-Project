package com.example.flashlearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlashCardAdapter flashcardAdapter;
    private List<FlashCard> flashcardList;
    private FloatingActionButton addFlashcardButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addFlashcardButton = findViewById(R.id.addFlashcardButton);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("flashcards");

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        flashcardList = new ArrayList<>();
        flashcardAdapter = new FlashcardAdapter(flashcardList, this);
        recyclerView.setAdapter(flashcardAdapter);

        // Load flashcards from Firebase
        loadFlashcardsFromFirebase();

        // Add new flashcard
        addFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddFlashcardActivity.class));
            }
        });
    }

    private void loadFlashcardsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                flashcardList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Flashcard flashcard = dataSnapshot.getValue(Flashcard.class);
                    if (flashcard != null) {
                        flashcardList.add(flashcard);
                    }
                }
                flashcardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load flashcards", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
