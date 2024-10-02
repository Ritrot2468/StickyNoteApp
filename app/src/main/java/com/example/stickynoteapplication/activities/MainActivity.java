
package com.example.stickynoteapplication.activities;

import android.os.Bundle;

import com.example.stickynoteapplication.R;
import com.example.stickynoteapplication.fragments.MyNotesFragment;
import com.example.stickynoteapplication.fragments.ToDoListFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemSelected(R.id.navigation_notes, true);

        // Load the default fragment
        loadFragment(new MyNotesFragment(), "NOTES_FRAGMENT");

        bottomMenu();
    }

    private void bottomMenu() {
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                String tag = null;

                if (i == R.id.navigation_notes) {
                    fragment = fragmentManager.findFragmentByTag("NOTES_FRAGMENT");
                    if (fragment == null) fragment = new MyNotesFragment();
                    tag = "NOTES_FRAGMENT";
                } else if (i == R.id.navigation_todolist) {
                    fragment = fragmentManager.findFragmentByTag("TODO_FRAGMENT");
                    if (fragment == null) fragment = new ToDoListFragment();
                    tag = "TODO_FRAGMENT";
                }

                loadFragment(fragment, tag);
            }
        });
    }

    private void loadFragment(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }
}

