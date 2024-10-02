package com.example.stickynoteapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stickynoteapplication.DAO.MyNotesDatabase;
import com.example.stickynoteapplication.R;
import com.example.stickynoteapplication.entities.MyNoteEntities;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.noteViewHolder> {

    public interface OnNoteClickListener {
        void onNoteClick(int position);
    }
    private List<MyNoteEntities> noteEntitiesList;
    private Context context;
    private OnNoteClickListener onNoteClickListener;
    public MyNoteAdapter(Context context, OnNoteClickListener onNoteClickListener) {
        this.noteEntitiesList = new ArrayList<>();
        this.context = context;
        this.onNoteClickListener = onNoteClickListener;
    }

    // Method to update the notes list and notify the adapter
    public void setNotes(List<MyNoteEntities> noteEntitiesList) {
        this.noteEntitiesList = noteEntitiesList;
        notifyDataSetChanged();
    }

    public List<MyNoteEntities> getNoteEntitiesList() {
        return noteEntitiesList;
    }

    @NonNull
    @Override
    public noteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new noteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false), onNoteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull noteViewHolder holder, int position) {
        position = holder.getAdapterPosition();
        MyNoteEntities currentNote = noteEntitiesList.get(position);
        holder.setNote(currentNote);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(currentNote, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteEntitiesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class noteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, textNote, dateTime;
        private LinearLayout linearLayout;
        private RoundedImageView roundedImageView;
        OnNoteClickListener onNoteClickListener;
        ImageButton deleteButton;

        public noteViewHolder(@NonNull View itemView, OnNoteClickListener onNoteClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            textNote = itemView.findViewById(R.id.textNote);
            dateTime = itemView.findViewById(R.id.textDateTime);
            linearLayout = itemView.findViewById(R.id.layoutNote);
            roundedImageView = itemView.findViewById(R.id.imageNote_item);
            roundedImageView.setVisibility(View.VISIBLE);
            this.onNoteClickListener = onNoteClickListener;
            itemView.setOnClickListener(this);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        public void setNote(MyNoteEntities myNoteEntities) {
            title.setText(myNoteEntities.getTitle());
            textNote.setText(myNoteEntities.getNoteText());
            dateTime.setText(myNoteEntities.getDateTime());

            // Check if the background is a GradientDrawable before casting
            if (linearLayout.getBackground() instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) linearLayout.getBackground();
                if (myNoteEntities.getColor() != null) {
                    gradientDrawable.setColor(Color.parseColor(myNoteEntities.getColor()));
                } else {
                    gradientDrawable.setColor(Color.parseColor("#FF937B")); // Default color
                }
            } else if (linearLayout.getBackground() instanceof ColorDrawable) {
                // If it's a ColorDrawable, set a new GradientDrawable
                GradientDrawable gradientDrawable = new GradientDrawable();
                if (myNoteEntities.getColor() != null) {
                    gradientDrawable.setColor(Color.parseColor(myNoteEntities.getColor()));
                } else {
                    gradientDrawable.setColor(Color.parseColor("#FF937B")); // Default color
                }
                linearLayout.setBackground(gradientDrawable);
            }
        }

        @Override
        public void onClick(View v) {
            onNoteClickListener.onNoteClick(getAdapterPosition());  // Trigger the callback with the note position
        }

    }

    private void deleteNote(MyNoteEntities note, int position) {
        // Delete the note from the database
        class DeleteNoteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                MyNotesDatabase.getDatabase(context).notesDao().delete(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // Remove the note from the list and notify the adapter
                noteEntitiesList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, noteEntitiesList.size());  // Update the rest of the items
                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }

        // Execute the delete operation
        new DeleteNoteTask().execute();
    }
}

//package com.example.stickynoteapplication.adapters;
//
//import android.graphics.Color;
//import android.graphics.drawable.GradientDrawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.stickynoteapplication.R;
//import com.example.stickynoteapplication.entities.MyNoteEntities;
//import com.makeramen.roundedimageview.RoundedImageView;
//
//import java.util.List;
//
//public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.ViewHolder> {
//    List<MyNoteEntities> noteEntitiesList;
//
//    public MyNoteAdapter(List<MyNoteEntities> noteEntitiesList) {
//        this.noteEntitiesList = noteEntitiesList;
//    }
//    @NonNull
//    @Override
//    public MyNoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.setNote(noteEntitiesList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return noteEntitiesList.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView title, textNote,dateTime;
//        private LinearLayout linearLayout;
//        RoundedImageView roundedImageView;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            title = itemView.findViewById(R.id.textTitle);
//            textNote = itemView.findViewById(R.id.textNote);
//            dateTime = itemView.findViewById(R.id.textDateTime);
//            linearLayout = itemView.findViewById(R.id.layoutNote);
//            roundedImageView = itemView.findViewById(R.id.imageNote_item);
//
//        }
//
//        public void setNote(MyNoteEntities myNoteEntities) {
//            title.setText(myNoteEntities.getTitle());
//            textNote.setText(myNoteEntities.getNoteText());
//            dateTime.setText(myNoteEntities.getDateTime());
//
//            GradientDrawable gradientDrawable = (GradientDrawable) linearLayout.getBackground();
//
//            if (myNoteEntities.getColor() != null) {
//                gradientDrawable.setColor(Color.parseColor(myNoteEntities.getColor()));
//            } else {
//                gradientDrawable.setColor(Color.parseColor("FF937B"));
//            }
//
//        }
//    }
//}
