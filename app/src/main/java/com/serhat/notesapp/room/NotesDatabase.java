package com.serhat.notesapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.serhat.notesapp.data.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {
    public abstract NoteDao getNoteDao();
}
