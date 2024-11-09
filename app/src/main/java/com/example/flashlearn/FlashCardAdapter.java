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

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FlashCardAdapter extends RecyclerView.Adapter<FlashCardAdapter.ViewHolder> {

    private List<FlashCard> flashcardList;
    private Context context;
    private FirebaseFirestore db;

    public FlashCardAdapter(List<FlashCard> flashcardList, Context context) {
        this.flashcardList = flashcardList;
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FlashCard flashcard = flashcardList.get(position);

        // Set the question text
        holder.questionTextView.setText(flashcard.getQuestion());

        // On click listener to open FlashcardViewActivity
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, FlashcardViewActivity.class);
            intent.putExtra("flashcardId", flashcard.getId());
            context.startActivity(intent);
        });

        // Edit button to navigate to EditFlashcardActivity
        holder.editButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditFlashcardActivity.class);
            intent.putExtra("flashcardId", flashcard.getId());
            context.startActivity(intent);
        });

        // Delete button to delete flashcard from Firestore
        holder.deleteButton.setOnClickListener(view -> {
            db.collection("flashcards").document(flashcard.getId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "Flashcard deleted", Toast.LENGTH_SHORT).show();
                        flashcardList.remove(position);  // Remove from the list
                        notifyItemRemoved(position);
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
