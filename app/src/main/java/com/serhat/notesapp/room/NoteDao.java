package com.serhat.notesapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.serhat.notesapp.data.model.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY note_id DESC")
    Single<List<Note>> getNotes();

    @Query("SELECT * FROM notes WHERE note_id = :note_id")
    Single<Note> getNoteDetails(int note_id);

    @Insert
    Completable addNote(Note note);

    @Update
    Completable editNote(Note note);

    @Delete
    Completable deleteNote(Note note);
}
