package com.serhat.notesapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.data.repository.NoteRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NoteDetailsViewModel extends ViewModel {
    private final NoteRepository nRepo;
    private final MutableLiveData<Note> note;
    private final MutableLiveData<String> toastObserver;
    public MutableLiveData<Boolean> onSuccessObserver;

    @Inject
    public NoteDetailsViewModel(NoteRepository nRepo) {
        this.nRepo = nRepo;

        note = nRepo.getNote();
        toastObserver = nRepo.getToastObserver();
        onSuccessObserver = nRepo.getOnSuccessObserver();
    }

    public MutableLiveData<Note> getNote() {
        return note;
    }

    public MutableLiveData<String> getToastObserver() {
        return toastObserver;
    }

    public MutableLiveData<Boolean> getOnSuccessObserver() {
        return onSuccessObserver;
    }

    public void getNoteDetails(int note_id) {
        nRepo.getNoteDetails(note_id);
    }

    public void deleteNote(int note_id) {
        nRepo.deleteNote(note_id);
    }
}
