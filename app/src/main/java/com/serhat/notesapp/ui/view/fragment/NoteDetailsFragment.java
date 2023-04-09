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

import com.google.android.material.snackbar.Snackbar;
import com.serhat.notesapp.R;
import com.serhat.notesapp.data.model.Note;
import com.serhat.notesapp.databinding.FragmentNoteDetailsBinding;
import com.serhat.notesapp.ui.viewmodel.NoteDetailsViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.noties.markwon.Markwon;

@AndroidEntryPoint
public class NoteDetailsFragment extends Fragment {
    private FragmentNoteDetailsBinding binding;
    private NoteDetailsViewModel viewModel;
    private Markwon markwon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_details, container, false);
        binding.setNoteDetailsFragment(this);
        markwon = Markwon.create(requireContext());

        viewModel.onSuccessObserver.observe(getViewLifecycleOwner(), value -> {
            if (value) navToMain();
        });
        viewModel.getToastObserver().observe(getViewLifecycleOwner(), value -> {
            if (!value.isEmpty()) Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show();
        });

        viewModel.getNote().observe(getViewLifecycleOwner(), note -> {
            binding.setNote(note);
            markwon.setMarkdown(binding.lblNoteDetailsContent, note.getNote_content());
        });

        return binding.getRoot();
    }

    public void navToMain() {
        requireActivity().onBackPressed();
    }

    public void navToEditNote(View view, Note note) {
        NoteDetailsFragmentDirections.NoteDetailsToEditNote noteDetailsToEditNote = NoteDetailsFragmentDirections.noteDetailsToEditNote(note);
        Navigation.findNavController(view).navigate(noteDetailsToEditNote);
    }

    public void deleteNote(View view, int note_id) {
        Snackbar.make(view, getString(R.string.msg_confirm_delete), Snackbar.LENGTH_LONG).setAction(getString(R.string.btn_yes), v -> viewModel.deleteNote(note_id)).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NoteDetailsViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        NoteDetailsFragmentArgs bundle = NoteDetailsFragmentArgs.fromBundle(getArguments());
        int note_id = bundle.getNoteId();

        viewModel.getNoteDetails(note_id);
    }
}