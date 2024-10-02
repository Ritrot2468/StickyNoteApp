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
import android.widget.LinearLayout;

import com.example.stickynoteapplication.DAO.MyNotesDatabase;
import com.example.stickynoteapplication.R;
import com.example.stickynoteapplication.activities.add_new_note;
import com.example.stickynoteapplication.adapters.MyNoteAdapter;
import com.example.stickynoteapplication.entities.MyNoteEntities;
import com.example.stickynoteapplication.viewmodels.NotesViewModel;

import java.util.List;

public class MyNotesFragment extends Fragment implements MyNoteAdapter.OnNoteClickListener {

    private ImageView addNotes;
    public static final int REQUEST_CODE_ADD_NOTE = 2;
    private RecyclerView noteRec;
    private MyNoteAdapter myNoteAdapter;
    private NotesViewModel notesViewModel;

    public MyNotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_notes, container, false);
        addNotes = view.findViewById(R.id.add_note);
        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), add_new_note.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
            }
        });

        noteRec = view.findViewById(R.id.note_rec);
        noteRec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myNoteAdapter = new MyNoteAdapter(getContext(), this);
        noteRec.setAdapter(myNoteAdapter);

        // Initialize ViewModel and observe data
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes("STICKY_NOTE").observe(getViewLifecycleOwner(), new Observer<List<MyNoteEntities>>() {
            @Override
            public void onChanged(List<MyNoteEntities> myNoteEntities) {
                myNoteAdapter.setNotes(myNoteEntities);

                if (myNoteEntities != null && !myNoteEntities.isEmpty()) {
                    noteRec.smoothScrollToPosition(0);
                }
            }
        });

        return view;
    }

    // Handle click events
    @Override
    public void onNoteClick(int position) {
        // Get the clicked note
        MyNoteEntities clickedNote = myNoteAdapter.getNoteEntitiesList().get(position);

        // Open the add_new_note activity for editing and pass the note data
        Intent intent = new Intent(getContext(), add_new_note.class);
        intent.putExtra("noteId", clickedNote.getId());
        intent.putExtra("noteTitle", clickedNote.getTitle());
        intent.putExtra("noteContent", clickedNote.getNoteText());
        startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);  // Start the activity for editing
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            // No need to manually refresh notes, LiveData will automatically update
          //  loadNotes();
        }
    }

//    private void loadNotes() {
//        // Load notes manually (if needed for debugging)
//        MyNotesDatabase db = MyNotesDatabase.getDatabase(getContext());
//        db.notesDao().getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<MyNoteEntities>>() {
//            @Override
//            public void onChanged(List<MyNoteEntities> myNoteEntities) {
//                myNoteAdapter.setNotes(myNoteEntities);
//                if (myNoteEntities != null && !myNoteEntities.isEmpty()) {
//                    noteRec.smoothScrollToPosition(0);  // Optional: scroll to top
//                }
//            }
//        });
//    }
}

//package com.example.stickynoteapplication.fragments;
//
//import static android.app.Activity.RESULT_OK;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.example.stickynoteapplication.DAO.MyNotesDatabase;
//import com.example.stickynoteapplication.R;
//import com.example.stickynoteapplication.activities.add_new_note;
//import com.example.stickynoteapplication.adapters.MyNoteAdapter;
//import com.example.stickynoteapplication.entities.MyNoteEntities;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class MyNotesFragment extends Fragment {
//
//    ImageView addNotes;
//    public static final int REQUEST_CODE_ADD_NOTE = 1;
//    private RecyclerView noteRec;
//    private List<MyNoteEntities> noteEntitiesList;
//    private MyNoteAdapter myNoteAdapter;
//
//    public MyNotesFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_my_notes, container, false);
//        addNotes = view.findViewById(R.id.add_note);
//        addNotes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(getContext(), add_new_note.class), REQUEST_CODE_ADD_NOTE);
//            }
//        });
//
//        noteRec = view.findViewById(R.id.note_rec);
//        noteRec.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        noteEntitiesList = new ArrayList<>();
//        myNoteAdapter = new MyNoteAdapter(noteEntitiesList);
//        noteRec.setAdapter(myNoteAdapter);
//        getAllNotes();
//        return view;
//    }
//
//    private void getAllNotes() {
//
//        @SuppressLint("StaticFieldLeak")
//        class GetNoteTask extends AsyncTask<Void,Void,List<MyNoteEntities>>{
//
//            @Override
//            protected List<MyNoteEntities> doInBackground(Void... voids) {
//                return MyNotesDatabase
//                        .getDatabase(getActivity().getApplicationContext())
//                        .notesDao()
//                        .getAllNotes();
//            }
//
//            @Override
//            protected void onPostExecute(List<MyNoteEntities> myNoteEntities) {
//                super.onPostExecute(myNoteEntities);
//                if(noteEntitiesList.size() == 0) {
//                    noteEntitiesList.addAll(myNoteEntities);
//                    myNoteAdapter.notifyDataSetChanged();
//
//                } else {
//                    noteEntitiesList.add(0,noteEntitiesList.get(0));
//                    myNoteAdapter.notifyItemInserted(0);
//                }
//
//                noteRec.smoothScrollToPosition(0);
//            }
//
//        }
//        new GetNoteTask().execute();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
//            getAllNotes();
//        }
//    }
//}