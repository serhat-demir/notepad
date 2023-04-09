package com.serhat.notesapp.di;

import android.content.Context;

import androidx.room.Room;

import com.serhat.notesapp.data.repository.NoteRepository;
import com.serhat.notesapp.room.NoteDao;
import com.serhat.notesapp.room.NotesDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public NoteRepository provideNoteRepository(@ApplicationContext Context context, NoteDao noteDao) {
        return new NoteRepository(noteDao, context);
    }

    @Provides
    @Singleton
    public NoteDao provideNoteDao(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, NotesDatabase.class, "notes.sqlite")
                .createFromAsset("notes.sqlite")
                .build().getNoteDao();
    }
}
