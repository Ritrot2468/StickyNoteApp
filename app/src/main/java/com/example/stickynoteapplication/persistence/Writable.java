package com.example.stickynoteapplication.persistence;

import org.json.JSONException;
import org.json.JSONObject;

// EFFECTS: returns this as JSON object

// Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson() throws JSONException;
}
