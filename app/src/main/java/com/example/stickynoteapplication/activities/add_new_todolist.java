package com.example.stickynoteapplication.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.stickynoteapplication.DAO.MyNotesDatabase;
import com.example.stickynoteapplication.R;
import com.example.stickynoteapplication.entities.MyNoteEntities;
import com.example.stickynoteapplication.entities.MyTaskEntities;
import com.example.stickynoteapplication.viewmodels.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class add_new_todolist extends AppCompatActivity {

    private ImageView back_to_home, saveNote, selectedImageView;
    private EditText inputNoteTitle;
    private TextView textDateTime;
    private LinearLayout todoItemsContainer;
    private Button addTaskButton;
    private String selectedColor;
    private View indicator1, indicator2;
    private int noteId = -1;  // Default to -1 for new to-do lists

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todolist);

        initializeUI();

        setupClickListeners();

        updateDateTime();

        bottomSheet();
        setViewColor(indicator1, selectedColor);
        setViewColor(indicator2, selectedColor);

        // Check if we're editing an existing to-do list
        Intent intent = getIntent();
        if (intent.hasExtra("noteId")) {
            noteId = intent.getIntExtra("noteId", -1);

            if (noteId != -1) {
                String noteTitle = intent.getStringExtra("noteTitle");
                // Populate the fields with existing to-do list data
                inputNoteTitle.setText(noteTitle);

                // You can also retrieve the tasks for this to-do list from the database
                loadTasksForNote();
            }
        }
    }

    private void initializeUI() {
        back_to_home = findViewById(R.id.todo_back_button);
        inputNoteTitle = findViewById(R.id.todo_list_name_input);
        textDateTime = findViewById(R.id.todo_textDateTime);
        saveNote = findViewById(R.id.todo_save_button);
        todoItemsContainer = findViewById(R.id.todo_items_container);
        addTaskButton = findViewById(R.id.add_task_button);
        indicator1 = findViewById(R.id.viewIndicator);
        indicator2 = findViewById(R.id.viewIndicator2);
        selectedColor = getColorHexString(R.color.palette8);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTask();
            }
        });
    }

    private void setupClickListeners() {
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToToDoListFragment();  // Return to ToDoListFragment
            }
        });
    }

    private void updateDateTime() {
        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );
    }
    private void addNewTask() {
        // Inflate a new task item layout and add it to the container
        View newTaskView = getLayoutInflater().inflate(R.layout.item_task, null);

        CheckBox checkBox = newTaskView.findViewById(R.id.todo_item_checkbox);
        ImageView deleteButton = newTaskView.findViewById(R.id.delete_item_button);

        // Set up the delete button to remove the task item
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoItemsContainer.removeView(newTaskView);
            }
        });

        // Add the new task view to the container
        todoItemsContainer.addView(newTaskView);
    }


    private void saveNote() {
        if (inputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Invalid To-Do List Title", Toast.LENGTH_SHORT).show();
            return;
        }

        if (todoItemsContainer.getChildCount() == 0) {
            Toast.makeText(this, "Please add at least one task.", Toast.LENGTH_SHORT).show();
            return;
        }

        final MyNoteEntities myNoteEntities = new MyNoteEntities();
        myNoteEntities.setTitle(inputNoteTitle.getText().toString());
        myNoteEntities.setDateTime(textDateTime.getText().toString());
        myNoteEntities.setColor(selectedColor);
        myNoteEntities.setNoteType("TODO_LIST");

        // Save the to-do list and its associated tasks
        new SaveNoteWithTasksTask().execute(myNoteEntities);
    }



    private void loadTasksForNote() {
        class LoadTasksForNote extends AsyncTask<Integer, Void, List<MyTaskEntities>> {
            @Override
            protected List<MyTaskEntities> doInBackground(Integer... noteIds) {
                MyNotesDatabase db = MyNotesDatabase.getDatabase(getApplicationContext());
                return (List<MyTaskEntities>) db.taskDao().getTasksForNote(noteIds[0]);
            }

            @Override
            protected void onPostExecute(List<MyTaskEntities> tasks) {
                // Display the tasks in the UI
                for (MyTaskEntities task : tasks) {
                    View taskView = getLayoutInflater().inflate(R.layout.item_task, null);
                    EditText taskDescription = taskView.findViewById(R.id.task_content);
                    taskDescription.setText(task.getTaskDescription());
                    CheckBox checkBox = taskView.findViewById(R.id.todo_item_checkbox);
                    checkBox.setText(task.getTaskDescription());
                    checkBox.setChecked(task.isCompleted());

                    // Add the task view to the container
                    todoItemsContainer.addView(taskView);
                }
            }
        }

        new LoadTasksForNote().execute(noteId);  // Load tasks for the given noteId
    }

    private void navigateToToDoListFragment() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private class SaveNoteWithTasksTask extends AsyncTask<MyNoteEntities, Void, Void> {
        @Override
        protected Void doInBackground(MyNoteEntities... myNoteEntities) {
            MyNotesDatabase db = MyNotesDatabase.getDatabase(getApplicationContext());

            long noteId = db.notesDao().insert(myNoteEntities[0]);

            // Save each task associated with this to-do list
            for (int i = 0; i < todoItemsContainer.getChildCount(); i++) {
                View taskView = todoItemsContainer.getChildAt(i);
                CheckBox checkBox = taskView.findViewById(R.id.todo_item_checkbox);

                MyTaskEntities task = new MyTaskEntities();
                task.setNoteId((int) noteId);
                task.setTaskDescription(checkBox.getText().toString());
                task.setCompleted(checkBox.isChecked());

                db.taskDao().insert(task);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();  // Close the activity
        }
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
    private String getColorHexString(int colorId) {
        int color = ContextCompat.getColor(this, colorId);
        return String.format("#%06X", (0xFFFFFF & color));
    }
}


//package com.example.stickynoteapplication.activities;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import com.example.stickynoteapplication.DAO.MyNotesDatabase;
//import com.example.stickynoteapplication.R;
//import com.example.stickynoteapplication.entities.MyNoteEntities;
//import com.example.stickynoteapplication.entities.MyTaskEntities;
//import com.example.stickynoteapplication.fragments.ToDoListFragment;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class add_new_todolist extends AppCompatActivity {
//
//    private ImageView back_to_home, saveNote, selectedImageView;
//    private EditText inputNoteTitle;
//    private TextView textDateTime;
//    private LinearLayout todoItemsContainer;
//    private String selectedColor;
//    private int noteId = -1;  // Default to -1 for new to-do lists
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_new_todolist);
//
//        initializeUI();
//
//        setupClickListeners();
//
//        updateDateTime();
//
//        setupColorSelection();
//
//        // Check if we're editing an existing to-do list
//        Intent intent = getIntent();
//        if (intent.hasExtra("noteId")) {
//            noteId = intent.getIntExtra("noteId", -1);
//
//            if (noteId != -1) {
//                String noteTitle = intent.getStringExtra("noteTitle");
//                // Populate the fields with existing to-do list data
//                inputNoteTitle.setText(noteTitle);
//
//                // You can also retrieve the tasks for this to-do list from the database
//                loadTasksForNote();
//            }
//        }
//    }
//
//    private void initializeUI() {
//        back_to_home = findViewById(R.id.todo_back_button);
//        inputNoteTitle = findViewById(R.id.todo_list_name_input);
//        textDateTime = findViewById(R.id.todo_textDateTime);
//        saveNote = findViewById(R.id.todo_save_button);
//        todoItemsContainer = findViewById(R.id.todo_items_container);
//        Button addTaskButton = findViewById(R.id.add_task_button);
//
//        addTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addNewTask();
//            }
//        });
//    }
//
//    private void setupClickListeners() {
//        saveNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveNote();
//            }
//        });
//
//        back_to_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToMainActivity();
//                //finish();
//            }
//        });
//    }
//
//    private void updateDateTime() {
//        textDateTime.setText(
//                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
//                        .format(new Date())
//        );
//    }
//
//    private void setupColorSelection() {
//        selectedColor = getColorHexString(R.color.palette8);
//        selectedImageView = new ImageView(this);
//        selectedImageView.setImageResource(R.drawable.baseline_playlist_add_check_24);
//        selectedImageView.setColorFilter(ContextCompat.getColor(this, R.color.white));
//
//        FrameLayout initialFrame = findViewById(R.id.frameColor1);
//        initialFrame.addView(selectedImageView);
//
//        setClickListener(R.id.frameColor1, R.color.palette2);
//        setClickListener(R.id.frameColor2, R.color.palette1);
//        setClickListener(R.id.frameColor3, R.color.palette3);
//        setClickListener(R.id.frameColor4, R.color.palette4);
//        setClickListener(R.id.frameColor5, R.color.palette7);
//        setClickListener(R.id.frameColor6, R.color.palette6);
//    }
//
//    private void addNewTask() {
//        // Inflate a new task item layout and add it to the container
//        View newTaskView = getLayoutInflater().inflate(R.layout.item_task, null);
//
//        CheckBox checkBox = newTaskView.findViewById(R.id.todo_item_checkbox);
//        ImageView deleteButton = newTaskView.findViewById(R.id.delete_item_button);
//
//        // Set up the delete button to remove the task item
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                todoItemsContainer.removeView(newTaskView);
//            }
//        });
//
//        // Add the new task view to the container
//        todoItemsContainer.addView(newTaskView);
//    }
//
//    private void setClickListener(int frameId, int colorId) {
//        FrameLayout frameLayout = findViewById(frameId);
//        frameLayout.setOnClickListener(v -> {
//            FrameLayout parent = (FrameLayout) selectedImageView.getParent();
//            if (parent != null) {
//                parent.removeView(selectedImageView);
//            }
//            frameLayout.addView(selectedImageView);
//
//            selectedColor = getColorHexString(colorId);
//        });
//    }
//
//    private void saveNote() {
//        if (inputNoteTitle.getText().toString().trim().isEmpty()) {
//            Toast.makeText(this, "Invalid To-Do List Title", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (todoItemsContainer.getChildCount() == 0) {
//            Toast.makeText(this, "Please add at least one task.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        final MyNoteEntities myNoteEntities = new MyNoteEntities();
//        myNoteEntities.setTitle(inputNoteTitle.getText().toString());
//        myNoteEntities.setDateTime(textDateTime.getText().toString());
//        myNoteEntities.setColor(selectedColor);
//
//        // Save the to-do list and its associated tasks
//        new SaveNoteWithTasksTask().execute(myNoteEntities);
//    }
//
//    private void loadTasksForNote() {
//        class LoadTasksForNote extends AsyncTask<Integer, Void, List<MyTaskEntities>> {
//            @Override
//            protected List<MyTaskEntities>
//            doInBackground(Integer... noteIds) {
//                MyNotesDatabase db = MyNotesDatabase.getDatabase(getApplicationContext());
//                return (List<MyTaskEntities>) db.taskDao().getTasksForNote(noteIds[0]);
//            }
//
//            @Override
//            protected void onPostExecute(List<MyTaskEntities> tasks) {
//                // Display the tasks in the UI
//                for (MyTaskEntities task : tasks) {
//                    View taskView = getLayoutInflater().inflate(R.layout.item_task, null);
//                    CheckBox checkBox = taskView.findViewById(R.id.todo_item_checkbox);
//                    checkBox.setText(task.getTaskDescription());
//                    checkBox.setChecked(task.isCompleted());
//
//                    // Add the task view to the container
//                    todoItemsContainer.addView(taskView);
//                }
//            }
//        }
//
//        new LoadTasksForNote().execute(noteId);  // Load tasks for the given noteId
//    }
//
//    private void navigateToMainActivity() {
//        Intent intent = new Intent();
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//
//    private class SaveNoteWithTasksTask extends AsyncTask<MyNoteEntities, Void, Void> {
//        @Override
//        protected Void doInBackground(MyNoteEntities... myNoteEntities) {
//            MyNotesDatabase db = MyNotesDatabase.getDatabase(getApplicationContext());
//
//            long noteId = db.notesDao().insert(myNoteEntities[0]);
//
//            // Save each task associated with this to-do list
//            for (int i = 0; i < todoItemsContainer.getChildCount(); i++) {
//                View taskView = todoItemsContainer.getChildAt(i);
//                CheckBox checkBox = taskView.findViewById(R.id.todo_item_checkbox);
//
//                MyTaskEntities task = new MyTaskEntities();
//                task.setNoteId((int) noteId);
//                task.setTaskDescription(checkBox.getText().toString());
//                task.setCompleted(checkBox.isChecked());
//
//                db.taskDao().insert(task);
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Intent intent = new Intent();
//            setResult(RESULT_OK, intent);
//            finish();  // Close the activity
//        }
//    }
//
//    private String getColorHexString(int colorId) {
//        int color = ContextCompat.getColor(this, colorId);
//        return String.format("#%06X", (0xFFFFFF & color));
//    }
//}




//package com.example.stickynoteapplication.activities;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import com.example.stickynoteapplication.DAO.MyNotesDatabase;
//import com.example.stickynoteapplication.R;
//import com.example.stickynoteapplication.entities.MyNoteEntities;
//import com.example.stickynoteapplication.entities.MyTaskEntities;
//import com.example.stickynoteapplication.fragments.ToDoListFragment;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class add_new_todolist extends AppCompatActivity {
//    private ImageView back_to_home, saveNote, selectedImageView;
//    private EditText inputNoteTitle, inputNoteText;
//    private TextView textDateTime;
//    private LinearLayout todoItemsContainer;
//    private String selectedColor;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_new_todolist);
//
//        initializeUI();
//
//        setupClickListeners();
//
//        updateDateTime();
//
//        setupColorSelection();
//    }
//
//    private void initializeUI() {
//        back_to_home = findViewById(R.id.back_button);
//        inputNoteText = findViewById(R.id.note_text_input);
//        inputNoteTitle = findViewById(R.id.note_name_input);
//        textDateTime = findViewById(R.id.textDateTime);
//        saveNote = findViewById(R.id.save_button);
//        todoItemsContainer = findViewById(R.id.todo_items_container);
//        Button addTaskButton = findViewById(R.id.add_task_button);
//
//        addTaskButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addNewTask();
//            }
//        });
//    }
//
//    private void setupClickListeners() {
//        saveNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveNote();
//            }
//        });
//
//        back_to_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToMainActivity();
//            }
//        });
//    }
//
//    private void updateDateTime() {
//        textDateTime.setText(
//                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
//                        .format(new Date())
//        );
//    }
//
//    private void setupColorSelection() {
//        selectedColor = getColorHexString(R.color.palette8);
//        selectedImageView = new ImageView(this);
//        selectedImageView.setImageResource(R.drawable.baseline_playlist_add_check_24);
//        selectedImageView.setColorFilter(ContextCompat.getColor(this, R.color.white));
//
//        FrameLayout initialFrame = findViewById(R.id.frameColor1);
//        initialFrame.addView(selectedImageView);
//
//        setClickListener(R.id.frameColor1, R.color.palette2);
//        setClickListener(R.id.frameColor2, R.color.palette1);
//        setClickListener(R.id.frameColor3, R.color.palette3);
//        setClickListener(R.id.frameColor4, R.color.palette4);
//        setClickListener(R.id.frameColor5, R.color.palette7);
//        setClickListener(R.id.frameColor6, R.color.palette6);
//    }
//
//    private void addNewTask() {
//        // Inflate a new task item layout and add it to the container
//        View newTaskView = getLayoutInflater().inflate(R.layout.item_task, null);
//
//        CheckBox checkBox = newTaskView.findViewById(R.id.todo_item_checkbox);
//        ImageView deleteButton = newTaskView.findViewById(R.id.delete_item_button);
//
//        // Set up the delete button to remove the item
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                todoItemsContainer.removeView(newTaskView);
//            }
//        });
//
//        // Add the new task view to the container
//        todoItemsContainer.addView(newTaskView);
//    }
//
//    private void setClickListener(int frameId, int colorId) {
//        FrameLayout frameLayout = findViewById(frameId);
//        frameLayout.setOnClickListener(v -> {
//            FrameLayout parent = (FrameLayout) selectedImageView.getParent();
//            if (parent != null) {
//                parent.removeView(selectedImageView);
//            }
//            frameLayout.addView(selectedImageView);
//
//            selectedColor = getColorHexString(colorId);
//        });
//    }
//
//    private void saveNote() {
//        if (inputNoteTitle.getText().toString().trim().isEmpty()) {
//            Toast.makeText(this, "Invalid Note Title", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (inputNoteText.getText().toString().trim().isEmpty()) {
//            Toast.makeText(this, "Invalid Note Details", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        final MyNoteEntities myNoteEntities = new MyNoteEntities();
//        myNoteEntities.setTitle(inputNoteTitle.getText().toString());
//        myNoteEntities.setNoteText(inputNoteText.getText().toString());
//        myNoteEntities.setDateTime(textDateTime.getText().toString());
//        myNoteEntities.setColor(selectedColor);
//
//        new SaveNotesTask().execute(myNoteEntities);
//    }
//
//    private String getColorHexString(int colorId) {
//        int color = ContextCompat.getColor(this, colorId);
//        return String.format("#%06X", (0xFFFFFF & color));
//    }
//
//    private void navigateToMainActivity() {
//        Intent intent = new Intent(add_new_todolist.this, ToDoListFragment.class);
//        startActivity(intent);
//        finish();
//    }
//
//    private class SaveNotesTask extends AsyncTask<MyNoteEntities, Void, Void> {
//        @Override
//        protected Void doInBackground(MyNoteEntities... myNoteEntities) {
//            MyNotesDatabase.getDatabase(getApplicationContext())
//                    .notesDao()
//                    .insert(myNoteEntities[0]);
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            setResult(RESULT_OK);
//            finish();
//        }
//    }
//
//    private class SaveNoteWithTasksTask extends AsyncTask<MyNoteEntities, Void, Void> {
//        @Override
//        protected Void doInBackground(MyNoteEntities... myNoteEntities) {
//            MyNotesDatabase db = MyNotesDatabase.getDatabase(getApplicationContext());
//
//            // Insert the note and get its ID
//            long noteId = db.notesDao().insert(myNoteEntities[0]);
//
//            // Save each task associated with this note
//            for (int i = 0; i < todoItemsContainer.getChildCount(); i++) {
//                View taskView = todoItemsContainer.getChildAt(i);
//                CheckBox checkBox = taskView.findViewById(R.id.todo_item_checkbox);
//
//                MyTaskEntities task = new MyTaskEntities();
//                task.setNoteId((int) noteId);
//                task.setTaskDescription(checkBox.getText().toString());
//                task.setCompleted(checkBox.isChecked());
//
//                db.taskDao().insert(task);
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            setResult(RESULT_OK);
//            finish();
//        }
//    }
//}