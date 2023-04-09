package com.serhat.notesapp.data.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.serhat.notesapp.R;
import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.room.NoteDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteRepository {
    private final NoteDao noteDao;
    private final Context context;
    private final MutableLiveData<List<Note>> notes;
    private final MutableLiveData<Note> note;

    private final MutableLiveData<String> toastObserver;
    private final MutableLiveData<Boolean> onSuccessObserver;

    public NoteRepository(NoteDao noteDao, Context context) {
        this.noteDao = noteDao;
        this.context = context;
        notes = new MutableLiveData<>();
        note = new MutableLiveData<>();

        toastObserver = new MutableLiveData<>();
        onSuccessObserver = new MutableLiveData<>();
    }

    public MutableLiveData<List<Note>> getNotes() {
        return notes;
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
    public void loadNotes() {
        noteDao.getNotes().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<Note>>() {
                @Override
                public void onSubscribe(Disposable d) { }

                @Override
                public void onSuccess(List<Note> newNotes) {
                    notes.setValue(newNotes);
                }

                @Override
                public void onError(Throwable e) {
                    toastObserver.setValue(context.getResources().getString(R.string.msg_something_went_wrong));
                    toastObserver.setValue("");
                }
            });
    }

    public void getNoteDetails(int note_id) {
        noteDao.getNoteDetails(note_id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<Note>() {
                @Override
                public void onSubscribe(Disposable d) { }

                @Override
                public void onSuccess(Note newNote) {
                    note.setValue(newNote);
                }

                @Override
                public void onError(Throwable e) {
                    toastObserver.setValue(context.getResources().getString(R.string.msg_something_went_wrong));
                    toastObserver.setValue("");
                }
            });
    }

    public void addNote(String note_title, String note_content, int[] colors) {
        if (note_title.isEmpty() || note_content.isEmpty()) {
            toastObserver.setValue(context.getResources().getString(R.string.msg_note_title_or_content_cannot_be_empty));
            toastObserver.setValue("");
            return;
        }

        int randomIndex = new Random().nextInt(colors.length);
        int randomColor = colors[randomIndex];

        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(context.getResources().getString(R.string.date_format), new Locale(Locale.getDefault().getLanguage()));
        Note note = new Note(0, note_title, note_content, df.format(new Date()), randomColor);
        noteDao.addNote(note).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) { }

                @Override
                public void onComplete() {
                    onSuccessObserver.setValue(true);
                    onSuccessObserver.setValue(false);
                }

                @Override
                public void onError(Throwable e) {
                    toastObserver.setValue(context.getResources().getString(R.string.msg_something_went_wrong));
                    toastObserver.setValue("");
                }
            });
    }

    public void editNote(int note_id, String note_title, String note_content, String created_at, int color) {
        if (note_title.isEmpty() || note_content.isEmpty()) {
            toastObserver.setValue(context.getResources().getString(R.string.msg_note_title_or_content_cannot_be_empty));
            toastObserver.setValue("");
            return;
        }

        Note note = new Note(note_id, note_title, note_content, created_at, color);
        noteDao.editNote(note).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) { }

                @Override
                public void onComplete() {
                    onSuccessObserver.setValue(true);
                    onSuccessObserver.setValue(false);
                }

                @Override
                public void onError(Throwable e) {
                    toastObserver.setValue(context.getResources().getString(R.string.msg_something_went_wrong));
                    toastObserver.setValue("");
                }
            });
    }

    public void deleteNote(int note_id) {
        Note note = new Note(note_id, "", "", "", 0);
        noteDao.deleteNote(note).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) { }

                @Override
                public void onComplete() {
                    onSuccessObserver.setValue(true);
                    onSuccessObserver.setValue(false);
                }

                @Override
                public void onError(Throwable e) {
                    toastObserver.setValue(context.getResources().getString(R.string.msg_something_went_wrong));
                    toastObserver.setValue("");
                }
            });
    }
}
