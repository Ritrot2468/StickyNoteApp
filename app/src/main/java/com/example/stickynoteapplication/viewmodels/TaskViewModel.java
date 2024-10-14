package com.example.stickynoteapplication.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.stickynoteapplication.DAO.MyNotesDatabase;
import com.example.stickynoteapplication.entities.MyNoteEntities;
import com.example.stickynoteapplication.entities.MyTaskEntities;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private LiveData<List<MyTaskEntities>> allTasks;
    private LiveData<List<MyNoteEntities>> allTodos;
    public TaskViewModel(Application application) {
        super(application);
        MyNotesDatabase database = MyNotesDatabase.getDatabase(application);
        allTasks = database.taskDao().getAllTasks();
        this.allTodos = database.notesDao().getNotesByType("TODO_LIST");
    }

    // Method to get all tasks

    public LiveData<List<MyTaskEntities>> getTasksforNote(int noteId) {
        return MyNotesDatabase.getDatabase(getApplication()).taskDao().getTasksForNote(noteId);
    }

    public LiveData<List<MyNoteEntities>> getAllTodos(String type) {
        return allTodos;
    }

    //    public void insertTask(MyTaskEntities task) {
//        MyNotesDatabase.databaseWriteExecutor.execute(() -> {
//            MyNotesDatabase.getDatabase(getApplication()).taskDao().insert(task);
//        });
//    }
public void insertTask(MyNoteEntities note, List<MyTaskEntities> tasks) {
    MyNotesDatabase.databaseWriteExecutor.execute(() -> {
        long noteId = MyNotesDatabase.getDatabase(getApplication()).notesDao().insert(note);
        for (MyTaskEntities task : tasks) {
            task.setNoteId((int) noteId);
            MyNotesDatabase.getDatabase(getApplication()).taskDao().insert(task);
        }
    });
}
    public void updateTask(MyTaskEntities task) {
        MyNotesDatabase.databaseWriteExecutor.execute(() -> {
            MyNotesDatabase.getDatabase(getApplication()).taskDao().update(task);
        });
    }

    public void deleteTask(MyNoteEntities todo) {
        MyNotesDatabase.databaseWriteExecutor.execute(() -> {
            MyNotesDatabase.getDatabase(getApplication()).notesDao().delete(todo);
        });
    }
}
