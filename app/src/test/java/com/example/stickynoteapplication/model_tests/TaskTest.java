package com.example.stickynoteapplication.model_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.stickynoteapplication.model.Task;

public class TaskTest {
    Task t1;

    @BeforeEach
    public void runBefore() {
        t1 = new Task("Workout at Gym");
    }

    @Test
    public void testIsCompleted() {
        assertFalse(t1.isCompleted());
        t1.changeCompletedStatus();
        assertTrue(t1.isCompleted());
        t1.changeCompletedStatus();
        assertFalse(t1.isCompleted());
    }

    @Test
    public void testChangeTaskDescription() {
        assertEquals(t1.getTaskDescription(), "Workout at Gym");
        assertNotEquals(t1.getTaskDescription(), "workout at Gym");
        t1.setTaskDescription("Be Filthy Rich");
        assertEquals(t1.getTaskDescription(), "Be Filthy Rich");
    }
}
