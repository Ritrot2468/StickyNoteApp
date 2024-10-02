package com.example.stickynoteapplication.model_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.stickynoteapplication.model.Task;
import com.example.stickynoteapplication.model.ToDoList;

public class ToDoListTest {

    Task task1;
    Task task2;
    Task task3;
    ToDoList toDoList;

    @BeforeEach
    public void runBefore() {
        task1 = new Task("jump up Once.");
        task2 = new Task("Eat your vegetables");
        task3 = new Task("Get room cleaned");
        toDoList = new ToDoList("Runner");

    }

    @Test
    public void testAddTaskOnce(){
        assertEquals(toDoList.getName(), "Runner");
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        assertEquals(toDoList.getTasks().size(), 1);
    }

    @Test
    public void testRemoveTaskOnce(){
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        assertEquals(toDoList.getTasks().size(), 1);
        toDoList.removeTask(task1);
        assertEquals(toDoList.getTasks().size(), 0);
    }

    @Test
    public void testRemoveTaskMultiple() {
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);
        task1.changeCompletedStatus();
        task3.changeCompletedStatus();
        assertEquals(toDoList.getTasks().size(), 3);
        toDoList.removeTask(task1);
        toDoList.removeTask(task2);
        toDoList.removeTask(task3);
        assertEquals(toDoList.getTasks().size(), 0);
    }

    @Test
    public void testRemoveCompletedTaskOnce() {
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        assertEquals(toDoList.getTasks().size(), 1);
        task1.changeCompletedStatus();
        toDoList.removeTask(task1);
        assertTrue(toDoList.getTasks().isEmpty());
    }

    @Test
    public void testRemoveCompletedTaskMultiple() {
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);
        assertEquals(toDoList.getTasks().size(), 3);
        task1.changeCompletedStatus();
        task3.changeCompletedStatus();
        toDoList.removeCompletedTasks();
        assertEquals(toDoList.getTasks().size(), 1);
    }

    @Test
    public void testAddTaskMultiple(){
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);
        assertEquals(toDoList.getTasks().size(), 3);
    }

    @Test
    public void testGetCompletedTask(){
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);
        assertEquals(toDoList.getTasks().size(), 3);
        task1.changeCompletedStatus();
        task3.changeCompletedStatus();
        assertEquals(toDoList.getCompletedTasks().size(), 2);
    }

    @Test
    public void testGetIncompleteTask(){
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);
        assertEquals(toDoList.getTasks().size(), 3);
        task2.changeCompletedStatus();
        assertEquals(toDoList.getIncompleteTasks().size(), 2);
    }

    @Test
    public void testFindTask() {
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);
        assertEquals(toDoList.findTask("Eat your vegetables"), task2);
        assertNull(toDoList.findTask("Eat your "));

    }
    @Test
    public void testModifyAndFindTask(){
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        toDoList.addTask(task2);
        toDoList.addTask(task3);
        Task t = toDoList.findTask("Eat your vegetables");
        assertEquals(t, task2);
        toDoList.modifyTask(t,"get food at UBC");
        assertEquals(t.getTaskDescription(), "get food at UBC");
    }

    @Test
    public void testIsDuplicateTask() {
        assertEquals(toDoList.getTasks().size(), 0);
        toDoList.addTask(task1);
        toDoList.addTask(task2);
        assertTrue(toDoList.isDuplicateTask("jump up Once."));
        assertFalse(toDoList.isDuplicateTask("Jump up Once."));

        assertEquals(toDoList.findTask("jump up Once."), task1);
        task1.setTaskDescription("Check on kids");
        assertFalse(toDoList.isDuplicateTask("jump up Once."));

    }
}
