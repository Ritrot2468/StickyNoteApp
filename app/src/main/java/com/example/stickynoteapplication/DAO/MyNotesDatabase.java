package com.example.stickynoteapplication.DAO;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.stickynoteapplication.entities.MyNoteEntities;
import com.example.stickynoteapplication.entities.MyTaskEntities;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MyNoteEntities.class, MyTaskEntities.class}, version = 3, exportSchema = true)
public abstract class MyNotesDatabase extends RoomDatabase {

    private static volatile MyNotesDatabase INSTANCE;

    public abstract MyNotesDao notesDao();
    public abstract TaskDao taskDao();

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);
    public static synchronized MyNotesDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyNotesDatabase.class, "sticky_note_database")
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Example migration: Adding a new column to the "note" table
            //database.execSQL("ALTER TABLE note ADD COLUMN last_modified INTEGER DEFAULT 0 NOT NULL");
            //database.execSQL("ALTER TABLE note ADD COLUMN noteType INTEGER DEFAULT 0 NOT NULL");
            database.execSQL("ALTER TABLE note ADD COLUMN noteType TEXT DEFAULT 'undefined'");

        }
    };
}


//package com.example.stickynoteapplication.DAO;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.room.migration.Migration;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import com.example.stickynoteapplication.entities.MyNoteEntities;
//import com.example.stickynoteapplication.entities.MyTaskEntities;
//import com.example.stickynoteapplication.model.Task;
////import com.example.stickynoteapplication.model.ToDoList;
//
//@Database(entities = {MyNoteEntities.class, MyTaskEntities.class}, version = 1, exportSchema = true)
//public abstract class MyNotesDatabase extends RoomDatabase {
//
//    private static volatile MyNotesDatabase INSTANCE;
//
//    public abstract MyNotesDao notesDao();
//    public abstract TaskDao taskDao();
//
//    public static synchronized MyNotesDatabase getDatabase(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            MyNotesDatabase.class, "sticky_note_database")
//                    .addMigrations(MIGRATION_1_2)
//                    .build();
//        }
//        return INSTANCE;
//    }
//
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            // Perform schema changes and data transformations here
//        }
//    };
//}
