package com.example.stickynoteapplication.model;

// individual task that can be added to ToDo List
//  with associated name and completion status for tracking and modification.

import org.json.JSONException;
import org.json.JSONObject;
import com.example.stickynoteapplication.persistence.Writable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.ColumnInfo;
//
//@Entity(tableName = "tasks",
//        foreignKeys = @ForeignKey(entity = ToDoList.class,
//                parentColumns = "id",
//                childColumns = "todo_list_id",
//                onDelete = ForeignKey.CASCADE))
//public class Task {
//
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//
//    @ColumnInfo(name = "task_description")
//    private String taskDescription;
//
//    @ColumnInfo(name = "is_completed")
//    private boolean isCompleted;
//
//    @ColumnInfo(name = "todo_list_id")
//    private int todoListId;
//
//    // Constructors, Getters, and Setters
//
//    public Task(String taskDescription, boolean isCompleted, int todoListId) {
//        this.taskDescription = taskDescription;
//        this.isCompleted = isCompleted;
//        this.todoListId = todoListId;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
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
//
//    public int getTodoListId() {
//        return todoListId;
//    }
//
//    public void setTodoListId(int todoListId) {
//        this.todoListId = todoListId;
//    }
//
//    // Add any additional methods or override toJson() if needed
//}

public class Task implements Writable {
    private boolean isCompleted;
    private String taskDescription;


    // EFFECTS: create a new task with given taskDescription
    // and set status to taskCompleted to false -> task is not complete
    public Task(String taskDescription) {
        this.isCompleted = false;
        this.taskDescription = taskDescription;
    }

    // getters
    public boolean isCompleted() {
        return isCompleted;
    }

    public String getTaskDescription() {
        return taskDescription;
    }


    // MODIFIES: this
    // EFFECTS: updates status of task's completion from true -> false or false -> true & add event
    public void changeCompletedStatus() {
        this.isCompleted = !isCompleted;
        String status = "";
        if (isCompleted) {
            status = "completed";
        } else {
            status = "incomplete";
        }
        EventLog.getInstance().logEvent(new Event(taskDescription + " task is " + status));
    }

    // MODIFIES: this
    // EFFECTS: updates task status to given status
    public void setCompletedStatus(Boolean status) {
        this.isCompleted = status;

        String stat = "";
        if (this.isCompleted) {
            stat = "completed";
        } else {
            stat = "incomplete";
        }
        EventLog.getInstance().logEvent(new Event(taskDescription + " task is " + stat));
    }

    // MODIFIES: this
    // EFFECTS: updates task description to given description
    public void setTaskDescription(String newDescription) {

        this.taskDescription = newDescription;
    }

    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("taskDescription", taskDescription);
        json.put("isCompleted", isCompleted);
        return json;
    }

}





