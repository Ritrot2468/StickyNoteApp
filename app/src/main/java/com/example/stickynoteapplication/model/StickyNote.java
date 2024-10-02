package com.example.stickynoteapplication.model;
//
//import androidx.room.ColumnInfo;
//
//import com.example.stickynoteapplication.persistence.Writable;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.Serializable;


// Creates a new empty sticky note with:
// - default noteColor and noteName
// - default size/dimension (will implement into GUI later)
// - default FONT size/color/style
// - empty taskList to track tasks


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.stickynoteapplication.persistence.Writable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Entity(tableName = "sticky_notes")
public class StickyNote extends NoteType implements Writable, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note_name")
    private String noteName;

    @ColumnInfo(name = "note_text")
    private String noteText;

    @ColumnInfo(name = "note_color")
    private String stickyNoteColor;

    @ColumnInfo(name = "font_size")
    private String fontSize;

    @ColumnInfo(name = "font_color")
    private String fontColor;

    @ColumnInfo(name = "font_name")
    private String fontName;

    // Constructor
    public StickyNote(String noteName) {
        super(noteName);
        this.noteName = noteName;
        this.stickyNoteColor = "#EAB434"; // Default color
        this.fontSize = "11"; // Default font size
        this.fontColor = "#000000"; // Default font color
        this.fontName = "Calibri"; // Default font name
        this.noteText = ""; // Default empty note text
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getStickyNoteColor() {
        return stickyNoteColor;
    }

    public void setStickyNoteColor(String stickyNoteColor) {
        this.stickyNoteColor = stickyNoteColor;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    // JSON Conversion
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("noteName", noteName);
        json.put("noteText", noteText);
        json.put("noteColor", stickyNoteColor);
        json.put("fontSize", fontSize);
        json.put("fontColor", fontColor);
        json.put("fontName", fontName);
        return json;
    }
}



//public class StickyNote extends NoteType implements Writable {
//
//    private String noteName;
//    private String noteText;
//    private int id;
//    private Integer stickyNoteColor;
//    private String fontSize;
//    private String fontColor;
//    private String fontName;
//
//
//    // EFFECTS: instantiates new stickyNote with given name, default yellow color
//    // font size = 11 and black font color, font name = Calibri and an empty todolist
//    public StickyNote(String noteName) {
//        super(noteName);
//    }
//
//
//    // setters
//
//
//
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    // EFFECTS: returns this as JSON object
//    @Override
//    public JSONObject toJson() throws JSONException {
//        JSONObject json = new JSONObject();
//        json.put("noteName", noteName);
//        json.put("noteColor", stickyNoteColor);
//        json.put("fontSize", fontSize);
//        json.put("fontColor", fontColor);
//        json.put("fontName", fontName);
//        return json;
//    }
//}
