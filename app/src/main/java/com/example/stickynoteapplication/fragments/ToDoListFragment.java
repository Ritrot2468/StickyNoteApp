package com.example.stickynoteapplication.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stickynoteapplication.R;
import com.example.stickynoteapplication.activities.add_new_todolist;
import com.example.stickynoteapplication.adapters.MyNoteAdapter;
import com.example.stickynoteapplication.adapters.MyTaskAdapter;
import com.example.stickynoteapplication.adapters.ToDoListAdapter;
import com.example.stickynoteapplication.entities.MyNoteEntities;
import com.example.stickynoteapplication.entities.MyTaskEntities;
import com.example.stickynoteapplication.viewmodels.NotesViewModel;
import com.example.stickynoteapplication.viewmodels.TaskViewModel;

import java.util.List;

public class ToDoListFragment extends Fragment implements ToDoListAdapter.OnNoteClickListener  {

    private ImageView addtodoList;
    public static final int REQUEST_CODE_ADD_TODO = 2;
    private RecyclerView todoRec;
    private ToDoListAdapter myTodolistAdapter;


    private NotesViewModel notesViewModel;

    public ToDoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);
        addtodoList = view.findViewById(R.id.add_todo);
        todoRec = view.findViewById(R.id.todo_rec);  // Assuming you have a RecyclerView with this ID in your layout
        myTodolistAdapter = new ToDoListAdapter(getContext(), this);

        // Set up ViewModel and observe changes in the ToDo list
        //taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        todoRec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        todoRec.setAdapter(myTodolistAdapter);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);


        notesViewModel.getAllTodos("TODO_LIST").observe(getViewLifecycleOwner(), new Observer<List<MyNoteEntities>>() {
            @Override
            public void onChanged(List<MyNoteEntities> notes) {
                if (notes != null) myTodolistAdapter.setNotes(notes);
            }
        });

        addtodoList.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), add_new_todolist.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_TODO);
        });

//        addtodoList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), add_new_todolist.class);
//                intent.putExtra("noteId", noteId);  // Pass the noteId to the add_new_todolist activity
//                startActivityForResult(intent, REQUEST_CODE_ADD_TODO);
//            }
//        });


//        // Set up RecyclerView
//        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
//        notesViewModel.getNotesByType("TODO_LIST").observe(getViewLifecycleOwner(), new Observer<List<MyNoteEntities>>() {
//            @Override
//            public void onChanged(List<MyNoteEntities> myNoteEntities) {
//                myNoteAdapter.setNotes(myNoteEntities);
//
//            }
//        });
//
//        //noteId = getArguments().getInt("noteId", -1);
//        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<MyTaskEntities>>() {
//            @Override
//            public void onChanged(List<MyTaskEntities> myTaskEntities) {
//                if (myTaskEntities != null) {  // Ensure the data is not null
//                    myTaskAdapter.setTasks(myTaskEntities);
//                        todoRec.smoothScrollToPosition(0);
//
//                }
//            }
//        });

        return view;
    }


//    @Override
//    public void onTaskClick(int position) {
//        // Get the clicked note
//        MyTaskEntities clickedTask = myTodolistAdapter.getTaskEntitiesList().get(position);
//
//        // Open the add_new_todolist activity for editing and pass the task data
//        Intent intent = new Intent(getContext(), add_new_todolist.class);
//        intent.putExtra("noteId", clickedTask.getNoteId());  // Pass the noteId
//        startActivityForResult(intent, REQUEST_CODE_ADD_TODO);  // Start the activity for editing
//
//        // Use an AsyncTask to fetch the note by noteId
//        //new FetchNoteTitleTask(noteId).execute();
//        //startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);  // Start the activity for editing
//    }


//    @Override
//    public void onDeleteTask(MyTaskEntities task) {
//        // Handle task deletion (e.g., delete from the database)
//        taskViewModel.deleteTask(task);
//    }
//
//    @Override
//    public void onTaskUpdated(MyTaskEntities task) {
//        // Handle task update (e.g., update task in the database)
//        taskViewModel.updateTask(task);
//    }

    @Override
    public void onTodoClick(int position) {
        // Get the clicked to-do item
        MyNoteEntities clickedTodo = myTodolistAdapter.getNotes().get(position);

        // Open the edit_todo activity for editing and pass the to-do data
        Intent intent = new Intent(getActivity(), add_new_todolist.class);
        intent.putExtra("noteId", clickedTodo.getId());
        intent.putExtra("noteTitle", clickedTodo.getTitle());
        intent.putExtra("noteContent", clickedTodo.getNoteText());
        startActivityForResult(intent, REQUEST_CODE_ADD_TODO);  // Start the activity for editing
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_TODO && resultCode == RESULT_OK) {
            // No need to manually refresh the list, LiveData will automatically update the UI

            notesViewModel.getAllTodos("TODO_LIST").observe(getViewLifecycleOwner(), new Observer<List<MyNoteEntities>>() {
                @Override
                public void onChanged(List<MyNoteEntities> notes) {
                    if (notes != null) {
                        myTodolistAdapter.setNotes(notes);
                    }
                }
            });

        }
    }


}


//package com.example.stickynoteapplication.fragments;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.example.stickynoteapplication.R;
//import com.example.stickynoteapplication.activities.add_new_todolist;
//
//
//public class ToDoListFragment extends Fragment {
//
//    ImageView addtodoList;
//    public static final int REQUEST_CODE_ADD_NOTE = 2;
//
//    public ToDoListFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);
//        addtodoList = view.findViewById(R.id.add_todo);
//        addtodoList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(getContext(), add_new_todolist.class), REQUEST_CODE_ADD_NOTE);
//            }
//        });
//
//        return view;
//    }
//}