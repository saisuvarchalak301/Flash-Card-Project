package com.example.flashlearn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FlashCardAdapter extends RecyclerView.Adapter<FlashCardAdapter.ViewHolder> {

    private List<FlashCard> flashcardList;
    private Context context;
    private DatabaseReference databaseReference;

    // Constructor to initialize the list of flashcards and context
    public FlashCardAdapter(List<FlashCard> flashcardList, Context context) {
        this.flashcardList = flashcardList;
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("flashcards");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each flashcard item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the flashcard at the current position
        FlashCard flashcard = flashcardList.get(position);

        // Set the question text to the TextView
        holder.questionTextView.setText(flashcard.getQuestion());

        // Set an onClickListener to open the FlashcardViewActivity when the item is clicked
        holder.itemView.setOnClickListener(view -> {
            // Intent to open FlashcardViewActivity
            Intent intent = new Intent(context, FlashcardViewActivity.class);
            intent.putExtra("flashcardId", flashcard.getId()); // Get the flashcard ID
            context.startActivity(intent);
        });

        // Set an onClickListener to open the EditFlashcardActivity when the "edit" button is clicked
        holder.editButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditFlashcardActivity.class);
            intent.putExtra("flashcardId", flashcard.getId()); // Pass the flashcard ID for editing
            context.startActivity(intent);
        });

        // Set an onClickListener to delete the flashcard when the "delete" button is clicked
        holder.deleteButton.setOnClickListener(view -> {
            // Remove the flashcard from Firebase Database
            databaseReference.child(flashcard.getId()).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "Flashcard deleted", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to delete flashcard", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return flashcardList.size();
    }

    // ViewHolder class to hold views for each flashcard item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
