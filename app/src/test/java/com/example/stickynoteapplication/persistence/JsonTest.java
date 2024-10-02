package com.example.stickynoteapplication.persistence;

import com.example.stickynoteapplication.model.StickyNote;
import com.example.stickynoteapplication.model.ToDoList;
import com.example.stickynoteapplication.model.Task;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

// Checks that the attributes of our created JSON object matches
// object it was copied from for the Tasks and stickyNotes

public class JsonTest {
    protected void checkTask(String taskDescription, Boolean isCompleted, Task t) {
        assertEquals(taskDescription, t.getTaskDescription());
        assertEquals(isCompleted, t.isCompleted());
    }

    protected void checkStickyNote
            (String noteName, String fn, Integer fc,
             Integer fs, Integer stickyNoteColor, StickyNote sn) {
        assertEquals(noteName, sn.getNoteName());
        assertEquals(fn, sn.getFontName());
        assertEquals(fc, sn.getFontColor());
        assertEquals(fs, sn.getFontSize());
        assertEquals(stickyNoteColor, sn.getStickyNoteColor());
    }

    protected void checkToDoList
            (String listName, List<Task> taskList, ToDoList list) {
        assertEquals(listName, list.getName());
        int i = 0;
        for (Task t : taskList) {
            checkTask(t.getTaskDescription(), t.isCompleted(), list.getTasks().get(i));
            i++;
        }

    }
}
