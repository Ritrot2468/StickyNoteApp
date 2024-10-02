package com.example.stickynoteapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stickynoteapplication.model.StickyNote;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StickyNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StickyNoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private StickyNote currentNote;

    public StickyNoteFragment(StickyNote s) {
        this.currentNote = s;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment StickyNoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StickyNoteFragment newInstance(StickyNote param1, String param2) {
        StickyNoteFragment fragment = new StickyNoteFragment(param1);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout
                .todolist_item, container, false);

//        TextView noteNameTextView = view.findViewById(R.id.noteNameTextView);
//        TextView noteColorTextView = view.findViewById(R.id.noteColorTextView);
//
//        noteNameTextView.setText(currentNote.getNoteName());
//        noteColorTextView.setText(String.valueOf(currentNote.getStickyNoteColor()));

        return view;
    }
}