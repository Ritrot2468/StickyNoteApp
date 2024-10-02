//package com.example.stickynoteapplication.persistence;
//
//import com.example.stickynoteapplication.model.StickyNoteHandler;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//
//// Represents a writer that writes JSON representation of StickyNoteHandler to file
//// Entire class modeled after JsonWriter Class in citation provided below:
//// Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//public class JsonWriter {
//
//    private final String destination;
//    private PrintWriter writer;
//    private static final int TAB = 4;
//
//    // EFFECTS: constructs writer to write to destination file
//    public JsonWriter(String destination) {
//        this.destination = destination;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
//    // be opened for writing
//    public void open() throws FileNotFoundException {
//        writer = new PrintWriter(destination);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: writes JSON representation of StickyNoteHandler to file
//    public void write(StickyNoteHandler sh) throws JSONException {
//        JSONObject json = sh.toJson();
//        saveToFile(json.toString(TAB));
//    }
//
//    // MODIFIES: this
//    // EFFECTS: closes writer
//    public void close() {
//        writer.close();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: writes string to file
//    private void saveToFile(String json) {
//        writer.print(json);
//    }
//
//}
