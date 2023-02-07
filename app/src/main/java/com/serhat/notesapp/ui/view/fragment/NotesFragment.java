package com.serhat.notesapp.ui.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.serhat.notesapp.R;
import com.serhat.notesapp.databinding.FragmentNotesBinding;
import com.serhat.notesapp.ui.view.adapter.NoteAdapter;
import com.serhat.notesapp.ui.viewmodel.NotesViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotesFragment extends Fragment {
    private FragmentNotesBinding binding;
    private NotesViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false);
        binding.setNotesFragment(this);

        viewModel.getToastObserver().observe(getViewLifecycleOwner(), value -> {
            if (!value.isEmpty()) Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show();
        });

        viewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
            binding.setNoteAdapter(new NoteAdapter(notes));
            binding.rvNotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        });

        return binding.getRoot();
    }

    public void navToAddNote() {
        Navigation.findNavController(binding.fabNavToAddNoteFromMain).navigate(R.id.notesToAddNote);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotesViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadNotes();
    }
}