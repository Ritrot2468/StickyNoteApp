package com.example.stickynoteapplication.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Index;

import java.io.Serializable;

@Entity(
        tableName = "task",
        foreignKeys = @ForeignKey(
                entity = MyNoteEntities.class,
                parentColumns = "id",
                childColumns = "note_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class MyTaskEntities implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note_id", index = true)
    private int noteId;

    @ColumnInfo(name = "task_description")
    private String taskDescription;

    @ColumnInfo(name = "is_completed")
    private boolean isCompleted;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}


//package com.example.stickynoteapplication.entities;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//import androidx.room.PrimaryKey;
//
//import java.io.Serializable;
//
//@Entity(
//        tableName = "task",
//        foreignKeys = @ForeignKey(
//                entity = MyNoteEntities.class,
//                parentColumns = "id",
//                childColumns = "note_id",
//                onDelete = ForeignKey.CASCADE
//        )
//)
//public class MyTaskEntities implements Serializable {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//
//    @ColumnInfo(name = "note_id", index = true)
//    private int noteId;
//
//    @ColumnInfo(name = "task_description")
//    private String taskDescription;
//
//    @ColumnInfo(name = "is_completed")
//    private boolean isCompleted;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getNoteId() {
//        return noteId;
//    }
//
//    public void setNoteId(int noteId) {
//        this.noteId = noteId;
//    }
//
//    public String getTaskDescription() {
//        return taskDescription;
//    }
//
//    public void setTaskDescription(String taskDescription) {
//        this.taskDescription = taskDescription;
//    }
//
//    public boolean isCompleted() {
//        return isCompleted;
//    }
//
//    public void setCompleted(boolean completed) {
//        isCompleted = completed;
//    }
//}
