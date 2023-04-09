package com.serhat.notesapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.serhat.notesapp.data.repository.NoteRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EditNoteViewModel extends ViewModel {
    private final NoteRepository nRepo;
    private final MutableLiveData<String> toastObserver;
    public MutableLiveData<Boolean> onSuccessObserver;

    @Inject
    public EditNoteViewModel(NoteRepository nRepo) {
        this.nRepo = nRepo;

        toastObserver = nRepo.getToastObserver();
        onSuccessObserver = nRepo.getOnSuccessObserver();
    }

    public MutableLiveData<String> getToastObserver() {
        return toastObserver;
    }

    public MutableLiveData<Boolean> getOnSuccessObserver() {
        return onSuccessObserver;
    }

    public void editNote(int note_id, String note_title, String note_content, String created_at, int color) {
        nRepo.editNote(note_id, note_title, note_content, created_at, color);
    }
}
