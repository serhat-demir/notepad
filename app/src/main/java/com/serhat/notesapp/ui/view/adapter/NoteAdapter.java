package com.serhat.notesapp.ui.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.serhat.notesapp.R;
import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.databinding.CardNoteBinding;
import com.serhat.notesapp.ui.view.fragment.NotesFragmentDirections;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> notes;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        CardNoteBinding binding;

        public NoteViewHolder(@NonNull CardNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardNoteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_note, parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        holder.binding.setNote(note);
        holder.binding.setNoteAdapter(NoteAdapter.this);
    }

    public void navToNoteDetails(View view, Note note) {
        NotesFragmentDirections.NotesToNoteDetails notesToNoteDetails = NotesFragmentDirections.notesToNoteDetails(note.getNote_id());
        Navigation.findNavController(view).navigate(notesToNoteDetails);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
