//package com.example.stickynoteapplication.persistence;
//
//import com.example.stickynoteapplication.model.StickyNoteHandler;
//import com.example.stickynoteapplication.model.StickyNote;
//import com.example.stickynoteapplication.model.ToDoList;
//import com.example.stickynoteapplication.model.Task;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.stream.Stream;
//
//// Represents a reader that reads a StickyNoteHandler from JSON data stored in file
//
//public class JsonReader {
//    private final String source;
//
//    // EFFECTS: constructs reader to read from source file
//    public JsonReader(String source) {
//        this.source = source;
//    }
//
//    // EFFECTS: reads StickyNoteHandler from file and returns it;
//    // throws IOException if an error occurs reading data from file
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    public StickyNoteHandler read() throws IOException, JSONException {
//        String jsonData = readFile(source);
//        JSONObject jsonObject = new JSONObject(jsonData);
//        return parseStickyNoteHandler(jsonObject);
//    }
//
//    // EFFECTS: reads source file as string and returns it
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    private String readFile(String source) throws IOException {
//        StringBuilder contentBuilder = new StringBuilder();
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
//                stream.forEach(s -> contentBuilder.append(s));
//            }
//        }
//
//        return contentBuilder.toString();
//    }
//
//
//    // EFFECTS: parses StickyNoteHandler from JSON object and returns it
//    private StickyNoteHandler parseStickyNoteHandler(JSONObject jsonObject) throws JSONException {
//        StickyNoteHandler sn = new StickyNoteHandler();
//        JSONArray jsonArray = jsonObject.getJSONArray("stickyNotes");
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject nextSticky = (JSONObject) jsonArray.get(i);
//            addStickyNote(sn, nextSticky);
//        }
//
//        JSONArray jsonArray1 = jsonObject.getJSONArray("todoLists");
//        for (int i = 0; i < jsonArray1.length(); i++) {
//            JSONObject nextToDoList = (JSONObject) jsonArray1.get(i);
//            addTasksToToDoList(sn, nextToDoList);
//        }
//        return sn;
//    }
//
//
//
//    // MODIFIES: s
//    // EFFECTS: creates a new sticky note with old info parsed into it and
//    // converts taskList field into an object.
//    private void addStickyNote(StickyNoteHandler s, JSONObject jsonObject) throws JSONException {
//        String stickyNoteColor = jsonObject.getString("noteColor");
//        int fontSize = jsonObject.getInt("fontSize");
//        String fontColor = jsonObject.getString("fontColor");
//        String fontName = jsonObject.getString("fontName");
//        String noteName = jsonObject.getString("noteName");
//
//
//        StickyNote sn = new StickyNote(noteName);
//        sn.changeNoteColor(stickyNoteColor);
//        sn.changeFontSize(fontSize);
//        sn.changeFontColor(fontColor);
//        sn.changeFontName(fontName);
//
//        s.addStickyNote(sn);
//    }
//
//
//
//
//
//    // MODIFIES: toDoList
//    // EFFECTS: parses taskList object from JSON object and adds tasks to todolist
//    private void addTasksToToDoList(StickyNoteHandler sn, JSONObject taskListJson) throws JSONException {
//        JSONArray tasksArray = taskListJson.getJSONArray("tasks");
//        String name = taskListJson.getString("name");
//        ToDoList toDoList = new ToDoList(name);
//
//        for (int i = 0; i<tasksArray.length(); i++) {
//            JSONObject taskJson = (JSONObject) tasksArray.get(i);
//            String taskDescription = taskJson.getString("taskDescription");
//            boolean isCompleted = taskJson.getBoolean("isCompleted");
//
//
//            Task task = new Task(taskDescription);
//            task.setCompletedStatus(isCompleted);
//            toDoList.addTask(task);
//        }
//
//        sn.addToDoList(toDoList);
//
//    }
//
//
//}
