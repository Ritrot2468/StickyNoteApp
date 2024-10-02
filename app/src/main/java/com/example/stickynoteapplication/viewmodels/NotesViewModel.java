package com.example.stickynoteapplication.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.stickynoteapplication.DAO.MyNotesDao;
import com.example.stickynoteapplication.DAO.MyNotesDatabase;
import com.example.stickynoteapplication.entities.MyNoteEntities;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private LiveData<List<MyNoteEntities>> allTodos;
    private LiveData<List<MyNoteEntities>> allNotes;
    //private MyNotesDao allNotes;

    public NotesViewModel(Application application) {
        super(application);
        MyNotesDatabase database = MyNotesDatabase.getDatabase(application);
        this.allTodos = database.notesDao().getNotesByType("TODO_LIST");
        this.allNotes = database.notesDao().getNotesByType("STICKY_NOTE");
    }

    public LiveData<List<MyNoteEntities>> getAllTodos(String type) {
        return allTodos;
    }

    public LiveData<List<MyNoteEntities>> getAllNotes(String type) {
        return allNotes;
    }
//    public LiveData<List<MyNoteEntities>> getAllNotes() {
//        return allNotes;
//    }
}
