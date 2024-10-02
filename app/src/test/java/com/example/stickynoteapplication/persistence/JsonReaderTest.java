package com.example.stickynoteapplication.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import android.graphics.Color;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import com.example.stickynoteapplication.model.StickyNoteHandler;
import com.example.stickynoteapplication.model.StickyNote;
import com.example.stickynoteapplication.model.ToDoList;
import com.example.stickynoteapplication.model.Task;


// checks that we can use the reader to read it back in and check that we
// read in a copy of what was written out.

public class JsonReaderTest extends JsonTest {

    String path;

    @BeforeEach
    void runBefore() {
        path = "./java/com/example/stickynoteapplication/";
    }


    @Test
    void testReaderFileNotInExistence() {
        JsonReader reader = new JsonReader(path + "data/noSuchFile.json");
        try {
            reader.read();

        } catch (IOException | JSONException e) {
            fail("IOException expected");
        }
    }

    @Test
    void testReaderEmptyStickyNoteHandler() {
        JsonReader reader = new JsonReader(path + "data/testReaderEmptyStickyNoteHandler.json");
        try {
            StickyNoteHandler snh = reader.read();
            assertEquals(0, snh.getStickyNotes().size());
        } catch (IOException | JSONException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderStickyNoteNoTask() {
        JsonReader reader = new JsonReader(path + "data/testReaderStickyNoteNoTask.json");
        try {
            StickyNoteHandler snh = reader.read();
            ToDoList taskList = new ToDoList("Runner");
            assertEquals(1, snh.getStickyNotes().size());
            checkStickyNote("Light", "Calibri", Color.BLACK, 11, Color.YELLOW,
                    snh.getStickyNotes().get(0));
            assertEquals(0, snh.getToDoLists().get(0).getTasks().size());
            assertEquals(0, snh.getToDoLists().get(0).getCompletedTasks().size());
            assertEquals(0, snh.getToDoLists().get(0).getIncompleteTasks().size());
        } catch (IOException | JSONException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderStickyNoteWithMultipleTasks() {
    readFirstStickyNoteWithTask();
    }

    void readFirstStickyNoteWithTask() {
        JsonReader reader = new JsonReader(path + "data/testReaderStickyNoteWithMultipleTasks.json");
        try {
            StickyNoteHandler snh = reader.read();
            ToDoList taskList1 = new ToDoList("Gun");
            Task firstNoteTask1 = new Task("Run a mile");
            firstNoteTask1.changeCompletedStatus();
            taskList1.addTask(firstNoteTask1);
            Task secondNoteTask1 = new Task("Get new shoes");
            taskList1.addTask(secondNoteTask1);

            assertEquals(2, snh.getStickyNotes().size());
            checkStickyNote("Light", "Calibri", Color.BLACK, 11, Color.YELLOW,
                    snh.getStickyNotes().get(0));
            compareTasksFirstNote(snh.getStickyNotes().get(0));
            assertEquals(2, snh.getStickyNotes().get(0).getTaskList().getTasks().size());

            ToDoList taskList2 = new ToDoList("Run");
            taskList2.addTask(secondNoteTask1);
            checkStickyNote("Tray", "Times New Roman", Color.RED, 16, Color.MAGENTA, taskList2,
                    snh.getStickyNotes().get(1));
            assertEquals(1, snh.getStickyNotes().get(1).getTaskList().getTasks().size());
            compareTasksSecondNote(snh.getStickyNotes().get(1));
        } catch (IOException | JSONException e) {
            fail("Couldn't read from file");
        }
    }

    void compareTasksFirstNote(ToDoList s) {
        List <Task> taskList = s.getTasks();
        checkTask("Run a mile", true, taskList.get(0));
        checkTask("Get new shoes", false, taskList.get(1));
    }

    void compareTasksSecondNote(ToDoList s) {
        List <Task> taskList = s.getTasks();
        checkTask("Get new shoes", false, taskList.get(0));
    }
}
