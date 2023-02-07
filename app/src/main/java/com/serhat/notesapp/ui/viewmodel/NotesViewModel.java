package com.serhat.notesapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.data.repository.NoteRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NotesViewModel extends ViewModel {
    private final NoteRepository nRepo;
    private final MutableLiveData<List<Note>> notes;
    private final MutableLiveData<String> toastObserver;

    @Inject
    public NotesViewModel(NoteRepository nRepo) {
        this.nRepo = nRepo;

        notes = nRepo.getNotes();
        toastObserver = nRepo.getToastObserver();
    }

    public MutableLiveData<List<Note>> getNotes() {
        return notes;
    }

    public MutableLiveData<String> getToastObserver() {
        return toastObserver;
    }

    public void loadNotes() {
        nRepo.loadNotes();
    }
}
