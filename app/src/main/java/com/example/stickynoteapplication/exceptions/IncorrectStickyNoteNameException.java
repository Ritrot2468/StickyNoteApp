package com.example.stickynoteapplication.exceptions;

// Exception used for cases where the sticky note name entered
// can't be found in the list of sticky notes because it is incorrectly typed or does not exist
// sticky note names are case-sensitive

public class IncorrectStickyNoteNameException extends Exception {
    public IncorrectStickyNoteNameException() {

    }
}
