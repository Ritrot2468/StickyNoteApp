package com.example.stickynoteapplication.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.stickynoteapplication.persistence.Writable;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

//@Entity(tableName = "todo_lists")
//public abstract class ToDoList extends NoteType implements Writable {
//
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//
//    @Ignore
//    private List<Task> tasks = new ArrayList<>();
//
//    // Constructor
//    public ToDoList(String name) {
//        super(name);
//        this.tasks = new ArrayList<>();
//    }
//
//    // Getters and Setters
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
////    //public List<Task> getTasks() {
////        return tasks;
////    }
//
//    public void setTasks(List<Task> tasks) {
//        this.tasks = tasks;
//    }
//
//    // Methods to manage tasks (add, remove, find, etc.) can remain unchanged
//
//    // Add methods to load tasks from the database if needed
//
//    // toJson method and other JSON handling remain the same
//}

// A to do list associated with every sticky note.
// Tracks list of tasks added and any additions, deletions or modification to those tasks.
//public class ToDoList extends NoteType implements Writable {
//
//    private final List<Task> tasks;
//
//    // EFFECTS: instantiates a new todo List with:
//    //          no tasks in task list.
//    public ToDoList(String name) {
//        super(name);
//        tasks = new ArrayList<>();
//
//    }
//
//    // getter
//    public List<Task> getTasks() {
//        return tasks;
//    }
//
//
//    // MODIFIES: this
//    // EFFECTS: adds given task to todoList
//    public void addTask(Task t) {
//        this.tasks.add(t);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: removes all completed tasks from todoList
//    public void removeCompletedTasks() {
//        tasks.removeIf(Task::isCompleted);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: removes given task from todolist.
//    public void removeTask(Task t) {
//        tasks.remove(t);
//    }
//
//    // REQUIRES: task description is unique
//    // MODIFIES: this
//    // EFFECTS: returns task in todoList with given description, else return null
//    public Task findTask(String description) {
//        for (Task t : tasks) {
//            if ((t.getTaskDescription()).equals(description)) {
//                return t;
//            }
//        }
//        return null;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: changes taskDescription to given description of a given task
//    public void modifyTask(Task t, String description) {
//        t.setTaskDescription(description);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: tracks all incomplete tasks in todoList and returns it
//    public List<Task> getIncompleteTasks() {
//        List<Task> incompleteTasks = new ArrayList<>();
//        for (Task t : tasks) {
//            if (!t.isCompleted()) {
//                incompleteTasks.add(t);
//            }
//        }
//        return incompleteTasks;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: tracks all completed tasks in todoList and returns it
//    public List<Task> getCompletedTasks() {
//        List<Task> completedTasks = new ArrayList<>();
//        for (Task t : tasks) {
//            if (t.isCompleted()) {
//                completedTasks.add(t);
//            }
//        }
//        return completedTasks;
//    }
//
//    // EFFECTS: return total number of tasks currently in todoList
//    public int totalNumOfTasks() {
//        return tasks.size();
//    }
//
//
//    // EFFECTS: returns true if task with same description as newTaskDesc is found in todoList
//    public boolean isDuplicateTask(String newTaskDesc) {
//        for (Task t : tasks) {
//            if (t.getTaskDescription().equals(newTaskDesc)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    // EFFECTS: returns this as JSON object
//    @Override
//    public JSONObject toJson() throws JSONException {
//        JSONObject json = new JSONObject();
//        json.put("incompleteTasks", incompleteTasksToJson());
//        json.put("completedTasks", completedTasksToJson());
//        json.put("tasks", tasksToJson());
//        json.put("name", super.noteName);
//        return json;
//    }
//
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    // EFFECTS: returns all tasks in this todolist as a JSON array
//    private JSONArray tasksToJson() throws JSONException {
//        JSONArray jsonArray = new JSONArray();
//
//        for (Task t : tasks) {
//            jsonArray.put(t.toJson());
//        }
//        return jsonArray;
//    }
//
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    // EFFECTS: returns all completed tasks in this todolist as a JSON array
//    private JSONArray completedTasksToJson() throws JSONException {
//        JSONArray jsonArray = new JSONArray();
//
//        for (Task t : getCompletedTasks()) {
//            jsonArray.put(t.toJson());
//        }
//
//        return jsonArray;
//    }
//
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    // EFFECTS: returns all incomplete tasks in this todolist as a JSON array
//    private JSONArray incompleteTasksToJson() throws JSONException {
//        JSONArray jsonArray = new JSONArray();
//
//        for (Task t : getIncompleteTasks()) {
//            jsonArray.put(t.toJson());
//        }
//
//        return jsonArray;
//    }
//
//}
