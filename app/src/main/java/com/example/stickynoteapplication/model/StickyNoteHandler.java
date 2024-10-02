package com.example.stickynoteapplication.model;

import com.example.stickynoteapplication.persistence.Writable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// handles list of sticky notes being added, removed and modified.

//public class StickyNoteHandler implements Writable {
//
//    private final List<StickyNote> stickyNotes;
//    private final List<ToDoList> toDoLists;
//
//    // EFFECTS: initialized handler with a list of empty sticky notes
//    public StickyNoteHandler() {
//        this.stickyNotes = new ArrayList<>();
//        this.toDoLists = new ArrayList<>();
//    }
//
//    // getter
//
//    public List<StickyNote> getStickyNotes() {
//        return stickyNotes;
//    }
//
//    public List<ToDoList> getToDoLists() {
//        return toDoLists;
//    }
//
//    // REQUIRES: unique note name (case-sensitive) for new StickyNote that isn't already in the list
//    // MODIFIES: this
//    // EFFECTS: adds new StickyNote to list
//    public void addNote(String noteName) {
//        StickyNote s = new StickyNote(noteName);
//        stickyNotes.add(s);
//    }
//
//
//    // MODIFIES: this
//    // EFFECTS: adds new StickyNote to list
//    public void addStickyNote(StickyNote stickyNote) {
//        stickyNotes.add(stickyNote);
//        EventLog.getInstance().logEvent(new Event(stickyNote.getNoteName() + " note has been added"));
//    }
//
//    // REQUIRES: unique note name (case-sensitive) for new ToDoList that isn't already in the list
//    // MODIFIES: this
//    // EFFECTS: adds new ToDolist to list
//    public void addToDoList(String listName) {
//        ToDoList s = new ToDoList(listName);
//        toDoLists.add(s);
//    }
//
//    // REQUIRES: unique note name (case-sensitive) for new ToDoList that isn't already in the list
//    // MODIFIES: this
//    // EFFECTS: adds new ToDolist to list
//    public void addToDoList(ToDoList newList) {
//        toDoLists.add(newList);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: removes note with given name from list of sticky notes
//    public void deleteNote(String newNoteName) {
//        StickyNote stickyNote = getNote(newNoteName);
//        stickyNotes.remove(stickyNote);
//    }
//
//
//
//    // MODIFIES: this
//    // EFFECTS: removes given StickyNote from list of sticky notes
//    public void deleteStickyNote(StickyNote stickyNote) {
//        stickyNotes.remove(stickyNote);
//        EventLog.getInstance().logEvent(new Event(stickyNote.getNoteName() + " note has been deleted"));
//    }
//
//    // REQUIRES: No duplicate sticky note names in stickyNotes
//    // EFFECTS: retrieves StickyNote with given name from stickyNotes, else returns null if not found
//    public StickyNote getNote(String noteName) {
//        for (StickyNote s : stickyNotes) {
//            if (s.getNoteName().equals(noteName)) {
//                return s;
//            }
//        }
//        return null;
//
//    }
//
//    // EFFECTS: returns true if there are any sticky note in list of sticky notes with given name
//    //          else false
//    public boolean isDuplicateName(String newNoteName) {
//        for (StickyNote s: stickyNotes) {
//            if (s.getNoteName().equals(newNoteName)) {
//                return true;
//            }
//        }
//        return false;
//
//    }
//
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    // EFFECTS: returns this as JSON object
//    @Override
//    public JSONObject toJson() throws JSONException {
//        JSONObject json = new JSONObject();
//        json.put("stickyNotes", stickiesToJson());
//        json.put("toDoLists", toDoListsToJson());
//        return json;
//    }
//
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    // EFFECTS: returns sticky notes in this StickyNoteHandler as a JSON array
//    protected JSONArray stickiesToJson() throws JSONException {
//        JSONArray jsonArray = new JSONArray();
//
//        for (StickyNote t : stickyNotes) {
//            jsonArray.put(t.toJson());
//        }
//
//        return jsonArray;
//    }
//
//    // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//    // EFFECTS: returns toDoLists in this StickyNoteHandler as a JSON array
//    protected JSONArray toDoListsToJson() throws JSONException {
//        JSONArray jsonArray = new JSONArray();
//
//        for (ToDoList t : toDoLists) {
//            jsonArray.put(t.toJson());
//        }
//
//        return jsonArray;
//    }
//
//}
