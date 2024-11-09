package com.example.flashlearn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.ViewHolder> {
    private List<Flashcard> flashcardList;
    private Context context;

    public FlashcardAdapter(List<Flashcard> flashcardList, Context context) {
        this.flashcardList = flashcardList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flashcard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flashcard flashcard = flashcardList.get(position);
        holder.questionTextView.setText(flashcard.getQuestion());

        holder.itemView.setOnClickListener(view -> {
            // Logic to view and flip the flashcard
        });

        holder.itemView.setOnLongClickListener(view -> {
            // Logic to edit or delete flashcard
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return flashcardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView questionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
        }
    }
}
