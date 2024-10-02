package com.example.stickynoteapplication.model;

public abstract class NoteType {

    protected String noteColor;

    protected String description;

    protected String fontSize;
    protected String fontColor;
    protected String fontName;
    protected String noteName;


    public NoteType(String name) {
        this.noteName = name;
        this.noteColor = "#EAB434";
        this.fontSize = "11";
        this.fontColor = "#000000" ;
        this.fontName = "Calibri";
        this.description = "";
    }

    public String getDescription() {
        return description;
    }
    public String getNoteName() {
        return noteName;
    }

    public String getStickyNoteColor() {
        return noteColor;
    }

    public String getFontSize() {
        return fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public String getFontName() {
        return fontName;
    }

    // REQUIRES: no duplicate names
    // MODIFIES: this
    // EFFECTS: change noteName to given name
    public void changeNoteName(String newName) {
        this.noteName = newName;
        EventLog.getInstance().logEvent(new Event("Note Name Changed to " + newName));
    }

    // MODIFIES: this
    // EFFECTS: change stickyNoteColor to given Color
    public void changeNoteColor(String newColor) {
        this.noteColor = newColor;
        EventLog.getInstance().logEvent(new Event(noteName + " note color changed to " + newColor.toString()));
    }


    // MODIFIES: this
    // EFFECTS: change fontSize to given size
    public void changeFontSize(String newFontSize) {
        this.fontSize = newFontSize;
        EventLog.getInstance().logEvent(new Event(noteName + " font Size Changed to " + newFontSize));
    }


    // MODIFIES: this
    // EFFECTS: change fontColor to given color
    public void changeFontColor(String newFontColor) {
        this.fontColor = newFontColor;
    }

    // MODIFIES: this
    // EFFECTS: change fontName to given name
    public void changeFontName(String newFontName) {
        this.fontName = newFontName;
    }

    public void changeDescription(String newDescription) {this.description = newDescription; }


    // EFFECTS: takes given Color object and returns associated red, blue
    //          and yellow components as integers
    // public int colorToJson(Color c) {
    //return c.getRGB();
    // }
}
