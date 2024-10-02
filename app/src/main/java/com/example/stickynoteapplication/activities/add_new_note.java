package com.example.stickynoteapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.stickynoteapplication.DAO.MyNotesDatabase;
import com.example.stickynoteapplication.R;
import com.example.stickynoteapplication.entities.MyNoteEntities;
import com.example.stickynoteapplication.fragments.MyNotesFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class add_new_note extends AppCompatActivity {

    private ImageView back_to_home, saveNote;
    private EditText inputNoteTitle, inputNoteText;
    private TextView textDateTime;
    private View indicator1, indicator2;
    private String selectedColor;
    private int noteId = -1;

    private String getColorHexString(int colorId) {
        int color = ContextCompat.getColor(this, colorId);
        return String.format("#%06X", (0xFFFFFF & color));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_note);

        indicator1 = findViewById(R.id.viewIndicator);
        indicator2 = findViewById(R.id.viewIndicator2);
        back_to_home = findViewById(R.id.back_button);
        inputNoteText = findViewById(R.id.note_text_input);
        inputNoteTitle = findViewById(R.id.note_name_input);
        textDateTime = findViewById(R.id.textDateTime);
        selectedColor = getColorHexString(R.color.palette8);
        saveNote = findViewById(R.id.save_button);  // Assuming there's a TextView or Button with this id

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the new activity
                Intent intent = new Intent(add_new_note.this, MyNotesFragment.class);

                // Start the new activity and finish this one
                startActivity(intent);
                finish();
            }
        });

        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );

        // Check if we are editing an existing note
        Intent intent = getIntent();
        if (intent.hasExtra("noteId")) {
            // Retrieve the passed note ID, title, and content
            noteId = intent.getIntExtra("noteId", -1);

            if (noteId != -1) {
                String noteTitle = intent.getStringExtra("noteTitle");
                String noteContent = intent.getStringExtra("noteContent");

                // Populate the fields with existing note data
                inputNoteTitle.setText(noteTitle);
                inputNoteText.setText(noteContent);
            }
        }


        bottomSheet();
        setViewColor(indicator1, selectedColor);
        setViewColor(indicator2, selectedColor);
    }

    private void setViewColor(View view, String selectedColor) {
        Drawable background = view.getBackground();

        if (background instanceof GradientDrawable) {
            // If the background is a GradientDrawable, set the color directly
            GradientDrawable gradientDrawable = (GradientDrawable) background;
            gradientDrawable.setColor(Color.parseColor(selectedColor));
        } else if (background instanceof ColorDrawable) {
            // If it's a ColorDrawable, replace it with a new GradientDrawable
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(Color.parseColor(selectedColor));
            view.setBackground(gradientDrawable);
        } else {
            // If the background is some other type, you can handle it or set a plain color
            view.setBackgroundColor(Color.parseColor(selectedColor));
        }
    }
    private void saveNote() {
        if (inputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Invalid Note Title", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (inputNoteText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Invalid Note Details", Toast.LENGTH_SHORT).show();
            return;
        }
        final MyNoteEntities myNoteEntities = new MyNoteEntities();
        myNoteEntities.setTitle(inputNoteTitle.getText().toString());
        myNoteEntities.setNoteText(inputNoteText.getText().toString());
        myNoteEntities.setDateTime(textDateTime.getText().toString());
        myNoteEntities.setColor(selectedColor);
        myNoteEntities.setNoteType("STICKY_NOTE");

        //Log.d("AddNewNote", "Title: " + myNoteEntities.getTitle() + ", Content: " + myNoteEntities.getNoteText());
        if (noteId != -1) {
            myNoteEntities.setId(noteId);  // Set the noteId for updating
        }

        class SaveNotes extends AsyncTask<Void,Void,Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                MyNotesDatabase db = MyNotesDatabase.getDatabase(getApplicationContext());
                if (noteId != -1) {
                    // If noteId is present, update the existing note
                    db.notesDao().update(myNoteEntities);
                    Log.d("AddNewNote", "Note UPDATED" );
                } else {
                    // Otherwise, insert a new note
                    long newId = db.notesDao().insert(myNoteEntities);
                    Log.d("AddNewNote", "Note inserted with ID: " + newId);
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        //new SaveNoteAsyncTask(StickyNoteDatabase.getDatabase(this)).execute(newNote);

        new SaveNotes().execute();
    }

    private void bottomSheet() {
        final LinearLayout linearLayout = findViewById(R.id.bottom_colorsheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);

        linearLayout.findViewById(R.id.bottom_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        final ImageView imgColor1 = linearLayout.findViewById(R.id.imageColor1);
        final ImageView imgColor2 = linearLayout.findViewById(R.id.imageColor2);
        final ImageView imgColor3 = linearLayout.findViewById(R.id.imageColor3);
        final ImageView imgColor4 = linearLayout.findViewById(R.id.imageColor4);
        final ImageView imgColor5 = linearLayout.findViewById(R.id.imageColor5);
        final ImageView imgColor6 = linearLayout.findViewById(R.id.imageColor6);

        linearLayout.findViewById(R.id.viewColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor =getColorHexString(R.color.palette6);
                imgColor1.setImageResource(R.drawable.baseline_playlist_add_check_24);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(0);

                setViewColor(indicator1, selectedColor);
                setViewColor(indicator2, selectedColor);
            }
        });

        linearLayout.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHexString(R.color.palette4);
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(R.drawable.baseline_playlist_add_check_24);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(0);

                setViewColor(indicator1, selectedColor);
                setViewColor(indicator2, selectedColor);
            }
        });

        linearLayout.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHexString(R.color.palette3);
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(R.drawable.baseline_playlist_add_check_24);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(0);

                setViewColor(indicator1, selectedColor);
                setViewColor(indicator2, selectedColor);
            }
        });

        linearLayout.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHexString(R.color.palette7);
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(R.drawable.baseline_playlist_add_check_24);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(0);

                setViewColor(indicator1, selectedColor);
                setViewColor(indicator2, selectedColor);
            }
        });

        linearLayout.findViewById(R.id.viewColor5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHexString(R.color.palette8);
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(R.drawable.baseline_playlist_add_check_24);
                imgColor6.setImageResource(0);

                setViewColor(indicator1, selectedColor);
                setViewColor(indicator2, selectedColor);
            }
        });

        linearLayout.findViewById(R.id.viewColor6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = getColorHexString(R.color.palette2);
                imgColor1.setImageResource(0);
                imgColor2.setImageResource(0);
                imgColor3.setImageResource(0);
                imgColor4.setImageResource(0);
                imgColor5.setImageResource(0);
                imgColor6.setImageResource(R.drawable.baseline_playlist_add_check_24);

                setViewColor(indicator1, selectedColor);
                setViewColor(indicator2, selectedColor);
            }
        });
    }
}
