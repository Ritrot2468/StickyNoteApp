package com.example.stickynoteapplication.model_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import android.graphics.Color;

import com.example.stickynoteapplication.model.StickyNote;
import com.example.stickynoteapplication.model.Task;
import com.example.stickynoteapplication.model.ToDoList;

public class StickyNoteTest {

    public StickyNote st1;
    public StickyNote st2;

    public ToDoList td1;

    public ToDoList td2;

    @BeforeEach
    public void runBefore() {
        st1 = new StickyNote("Runner");
        st2 = new StickyNote("Butter");
        td1 = new ToDoList("Runner");
        td2 = new ToDoList("Butter");
    }

    @Test
    public void testToDoList() {
        assertEquals(td1.totalNumOfTasks(), 0);
        Task t1 = new Task("Eat French Fries.");
        Task t2 = new Task("Workout for 10 mins");
        td1.addTask(t1);
        td1.addTask(t2);
        assertEquals(td1.totalNumOfTasks(), 2);
    }

    @Test
    public void testGetters() {
        assertEquals(st1.getNoteName(), "Runner");
        st1.changeFontName("Times New Roman");
        assertEquals(st1.getFontName(), "Times New Roman");

        assertEquals(st1.getStickyNoteColor(), "#EAB434");
        st1.changeNoteColor("#000000");
        assertEquals(st1.getStickyNoteColor(), "#000000");

        st1.changeFontSize(33);
        assertEquals(st1.getFontSize(), 33);
        String c = st1.getStickyNoteColor();
    }

    @Test
    public void testFontAttributes() {
        assertEquals(st1.getFontName(), "Calibri");
        assertEquals(st1.getFontSize(), 11);
        assertEquals(st1.getFontColor(), Color.BLACK);

        st1.changeFontColor("#000000");
        st1.changeFontName("Times New Roman");
        st1.changeFontSize(12);

        assertEquals(st1.getFontName(), "Times New Roman");
        assertEquals(st1.getFontSize(), 12);
        assertEquals(st1.getFontColor(), "#000000");

    }
}
