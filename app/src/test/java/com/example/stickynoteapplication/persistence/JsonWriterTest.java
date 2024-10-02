package com.example.stickynoteapplication.persistence;

import com.example.stickynoteapplication.model.StickyNoteHandler;
import com.example.stickynoteapplication.model.StickyNote;
import com.example.stickynoteapplication.model.ToDoList;
import com.example.stickynoteapplication.model.Task;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import android.graphics.Color;

// checks that we can write data to a file and then use the reader to read it back in and check that we
// read in a copy of what was written out.

public class JsonWriterTest extends JsonTest {

    String path;

    @BeforeEach
    void runBefore() {
        path = "./src/test/resources/";
    }

    @Test
    void testWriterInvalidFile() {
        try {
            StickyNoteHandler snh = new StickyNoteHandler();
            JsonWriter writer = new JsonWriter(path + "data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStickyNoteHandler() {
        try {
            StickyNoteHandler snh = new StickyNoteHandler();
            JsonWriter writer = new JsonWriter("./src/test/resources/data/testWriterEmptyStickyNoteHandler.json");
            writer.open();
            writer.write(snh);
            writer.close();

            JsonReader reader = new JsonReader("./src/test/resources/data/testWriterEmptyStickyNoteHandler.json");
            snh = reader.read();
            assertEquals(0, snh.getStickyNotes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testWriterStickyNoteNoTask() {
        try {
            StickyNoteHandler snh = new StickyNoteHandler();
            snh.addStickyNote(new StickyNote("Ice"));
            snh.getNote("Ice").changeFontName("Arial");
            snh.getNote("Ice").changeFontSize(20);
            JsonWriter writer = new JsonWriter(path + "data/testWriterStickyNoteNoTask.json");
            writer.open();
            writer.write(snh);
            writer.close();

            JsonReader reader = new JsonReader(path + "data/testWriterStickyNoteNoTask.json");
            snh = reader.read();
            List<StickyNote> stickyNotes = snh.getStickyNotes();
            assertEquals(1, stickyNotes.size());
            ToDoList taskList = new ToDoList();
            checkStickyNote("Ice", "Arial", Color.BLACK, 20, Color.YELLOW, taskList,
                    snh.getStickyNotes().get(0));
            assertEquals(0, snh.getToDoLists().get(0).getTasks().size());
            assertEquals(0, snh.getStickyNotes().get(0).getTaskList().getCompletedTasks().size());
            assertEquals(0, snh.getStickyNotes().get(0).getTaskList().getIncompleteTasks().size());

        } catch (IOException | JSONException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterStickyNoteWithMultipleTasks() {
        writeStickyNoteWithMultipleTasks();
    }

    void writeStickyNoteWithMultipleTasks() {
        try {
            StickyNoteHandler snh = new StickyNoteHandler();
            snh.addStickyNote(new StickyNote("Light"));
            snh.addStickyNote(new StickyNote("Tray"));

            Task firstNoteTask1 = new Task("Run a mile");
            firstNoteTask1.changeCompletedStatus();
            Task task2 = new Task("Get new shoes");
            snh.getNote("Light").changeFontName("Arial");
            snh.getNote("Light").changeFontSize(11);
            snh.getNote("Light").getTaskList().getTasks().add(firstNoteTask1);
            snh.getNote("Light").getTaskList().getTasks().add(task2);

            snh.getNote("Tray").changeFontSize(16);
            snh.getNote("Tray").changeFontName("Times New Roman");
            snh.getNote("Tray").changeFontColor("#101010");
            snh.getNote("Tray").changeNoteColor("#100000");
            snh.getNote("Tray").getTaskList().getTasks().add(task2);
            JsonWriter writer = new JsonWriter(path + "data/testWriterStickyNoteWithMultipleTasks.json");
            writer.open();
            writer.write(snh);
            writer.close();

            JsonReader reader = new JsonReader(path + "data/testWriterStickyNoteWithMultipleTasks.json");
            snh = reader.read();
            ToDoList taskList1 = new ToDoList();
            taskList1.addTask(firstNoteTask1);
            Task secondNoteTask1 = new Task("Get new shoes");
            taskList1.addTask(secondNoteTask1);

            assertEquals(2, snh.getStickyNotes().size());
            checkStickyNote("Light", "Arial", Color.BLACK, 11, Color.YELLOW, taskList1,
                    snh.getStickyNotes().get(0));
            compareTasksFirstNote(snh.getStickyNotes().get(0));
            assertEquals(2, snh.getStickyNotes().get(0).getTaskList().getTasks().size());

            ToDoList taskList2 = new ToDoList();
            taskList2.addTask(secondNoteTask1);
            checkStickyNote("Tray", "Times New Roman", Color.RED, 16, Color.MAGENTA, taskList2,
                    snh.getStickyNotes().get(1));
            assertEquals(1, snh.getStickyNotes().get(1).getTaskList().getTasks().size());
            compareTasksSecondNote(snh.getStickyNotes().get(1));
        } catch (IOException | JSONException e) {
            fail("Exception should not have been thrown");
        }
    }

    void compareTasksFirstNote(StickyNote s) {
        List<Task> taskList = s.getTaskList().getTasks();
        checkTask("Run a mile", true, taskList.get(0));
        checkTask("Get new shoes", false, taskList.get(1));
    }

    void compareTasksSecondNote(StickyNote s) {
        List<Task> taskList = s.getTaskList().getTasks();
        checkTask("Get new shoes", false, taskList.get(0));
    }
}
