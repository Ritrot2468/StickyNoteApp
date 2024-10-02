package com.example.stickynoteapplication.model_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.stickynoteapplication.model.StickyNote;
import com.example.stickynoteapplication.model.StickyNoteHandler;

public class StickyNoteHandlerTest {

    public StickyNoteHandler stickyNotes;


    @BeforeEach
    public void runBefore() {
        assertEquals(5,4);
        stickyNotes = new StickyNoteHandler();
    }

    @Test
    public void testAddNoteOnce() {
       assertTrue(stickyNotes.getStickyNotes().isEmpty());
       stickyNotes.addNote("Octopus");
       assertEquals(stickyNotes.getStickyNotes().size(), 1);
    }

    @Test
    public void testAddNoteMultiple() {
        assertTrue(stickyNotes.getStickyNotes().isEmpty());
        stickyNotes.addNote("Octopus");
        stickyNotes.addNote("Animal");
        assertEquals(stickyNotes.getStickyNotes().size(), 2);
    }

    @Test
    public void testDeleteNoteOnce() {
        assertTrue(stickyNotes.getStickyNotes().isEmpty());
        stickyNotes.addNote("Octopus");
        assertEquals(stickyNotes.getStickyNotes().size(), 1);
        stickyNotes.deleteNote("Octopus");
        assertTrue(stickyNotes.getStickyNotes().isEmpty());
    }

    @Test
    public void testDeleteNoteMultiple() {
        assertTrue(stickyNotes.getStickyNotes().isEmpty());
        stickyNotes.addNote("Octopus");
        stickyNotes.addNote("Animal");


        assertEquals(stickyNotes.getStickyNotes().size(), 2);
        stickyNotes.deleteNote("Octopus");
        assertEquals(stickyNotes.getStickyNotes().size(), 1);
        stickyNotes.deleteNote("Animal");
        assertTrue(stickyNotes.getStickyNotes().isEmpty());
    }

    @Test
    public void testGetNote() {
        assertTrue(stickyNotes.getStickyNotes().isEmpty());
        stickyNotes.addNote("Octopus");
        stickyNotes.addNote("Animal");
        StickyNote st1 = stickyNotes.getNote("Octopus");
        assertEquals(st1.getNoteName(), "Octopus");
        assertNull(stickyNotes.getNote("Tira"));

    }


    @Test
    public void testDeleteStickyNote() {
        assertTrue(stickyNotes.getStickyNotes().isEmpty());
        stickyNotes.addNote("Octopus");
        stickyNotes.addNote("Animal");
        StickyNote st1 = stickyNotes.getNote("Octopus");
        stickyNotes.deleteStickyNote(st1);
        assertNull(stickyNotes.getNote("Octopus"));
        assertEquals(stickyNotes.getStickyNotes().size(), 1);

    }

    @Test
    public void testAddStickyNote() {
        assertTrue(stickyNotes.getStickyNotes().isEmpty());
        stickyNotes.addNote("Octopus");
        stickyNotes.addNote("Animal");
        StickyNote st1 = new StickyNote("Runner");
        stickyNotes.addStickyNote(st1);
        assertEquals(stickyNotes.getNote("Runner"), st1);
        assertEquals(stickyNotes.getStickyNotes().size(), 3);

    }

    // Also checks for case-sensitivity
    @Test
    public void testIsDuplicateName() {
        stickyNotes.addNote("Grizzly");
        assertTrue(stickyNotes.isDuplicateName("Grizzly"));
        stickyNotes.getNote("Grizzly").changeNoteName("grizzly");
        assertFalse(stickyNotes.isDuplicateName("Grizzly"));
    }
}
