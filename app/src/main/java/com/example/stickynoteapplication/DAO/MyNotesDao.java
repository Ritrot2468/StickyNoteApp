package com.example.stickynoteapplication.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.stickynoteapplication.entities.MyNoteEntities;

import java.util.List;

@Dao
public interface MyNotesDao {
    @Insert
    long insert(MyNoteEntities noteEntities);

    @Update
    void update(MyNoteEntities noteEntities);
    @Delete
    void delete(MyNoteEntities noteEntities);

    @Query("SELECT * FROM note WHERE noteType = :type")
    LiveData<List<MyNoteEntities>> getNotesByType(String type);

    @Query("SELECT * FROM note WHERE id = :noteId LIMIT 1")
    MyNoteEntities getNoteById(int noteId);

    @Query("SELECT * FROM note ORDER BY id DESC")
    LiveData<List<MyNoteEntities>> getAllNotes();  // Use LiveData for automatic UI updates
}


//package com.example.stickynoteapplication.DAO;
////package com.example.stickynoteapplication.model;
//
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//
//import com.example.stickynoteapplication.entities.MyNoteEntities;
//
//import java.util.List;
//
//@Dao
//public interface MyNotesDao {
//    @Insert
//    void insert(MyNoteEntities noteEntities);
//    @Delete
//    void delete(MyNoteEntities noteEntities);
//
//    @Query("SELECT * FROM note ORDER BY id DESC")
//    List<MyNoteEntities> getAllNotes();
//
//
//}

