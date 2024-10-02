//package com.example.stickynoteapplication.DAO;
package com.example.stickynoteapplication.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.stickynoteapplication.entities.MyTaskEntities;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    long insert(MyTaskEntities task);

    @Query("SELECT * FROM task WHERE note_id = :noteId")
    LiveData<List<MyTaskEntities>> getTasksForNote(int noteId);  // Use LiveData for task retrieval

    @Delete
    void delete(MyTaskEntities task);

    @Update
    void update(MyTaskEntities task);

    @Query("SELECT * FROM task ORDER BY id DESC")
    LiveData<List<MyTaskEntities>> getAllTasks();

}


//package com.example.stickynoteapplication.model;

//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import com.example.stickynoteapplication.entities.MyNoteEntities;
//import com.example.stickynoteapplication.entities.MyTaskEntities;
//import com.example.stickynoteapplication.model.StickyNote;
//
//import java.util.List;
//
//@Dao
//public interface TaskDao {
//    @Insert
//    long insert(MyTaskEntities task);
//
//    @Query("SELECT * FROM task WHERE note_id = :noteId")
//    List<MyTaskEntities> getTasksForNoteId(int noteId);
//
//    @Delete
//    void delete(MyTaskEntities task);
//
//    @Update
//    void update(MyTaskEntities task);
//}

