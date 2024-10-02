package com.example.stickynoteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.example.stickynoteapplication.activities.add_new_note;
import com.example.stickynoteapplication.model.StickyNote;
//import com.example.stickynoteapplication.model.StickyNoteHandler;
//import com.example.stickynoteapplication.persistence.JsonReader;
//import com.example.stickynoteapplication.persistence.JsonWriter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.IOException;

//public class StickyNoteHandlerUI extends Fragment {
//
//    ImageButton addStickyNote;
//    public static final int REQUEST_CODE_ADD_NOTE = 1;
//
//    private StickyNoteHandler noteHandler;
//    public StickyNoteHandlerUI(StickyNoteHandler s) {
//        instantiateNoteHandler(noteHandler);
//    }
//
//    public StickyNoteHandler getNoteHandler() {
//        return noteHandler;
//    }
//
//    public void setNoteHandler(StickyNoteHandler s) {
//        this.noteHandler = s;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: instantiates new noteHandler, unless a saved version from JSON file has been loaded.
//    private void instantiateNoteHandler(StickyNoteHandler s) {
//        if (this.noteHandler == null) {
//            this.noteHandler = new StickyNoteHandler();
//        } else {
//          this.noteHandler = s;
//        }
//    }
//
//
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        View view = inflater.inflate(R.layout.home_page, container, false);
//        addStickyNote = view.findViewById(R.id.addnotebutton);
//        addStickyNote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              startActivityForResult(new Intent(getContext(), add_new_note.class),
//                      REQUEST_CODE_ADD_NOTE);
//            }
//        });
//        return view;
//
//    }
//
//
//
//
//}
