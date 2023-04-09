package com.serhat.notesapp.ui.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serhat.notesapp.R;
import com.serhat.notesapp.databinding.FragmentAddNoteBinding;
import com.serhat.notesapp.ui.viewmodel.AddNoteViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.noties.markwon.Markwon;
import io.noties.markwon.editor.MarkwonEditor;
import io.noties.markwon.editor.MarkwonEditorTextWatcher;

@AndroidEntryPoint
public class AddNoteFragment extends Fragment {
    private FragmentAddNoteBinding binding;
    private AddNoteViewModel viewModel;
    private Markwon markwon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false);
        binding.setAddNoteFragment(this);

        markwon = Markwon.create(requireContext());
        binding.txtAddNoteContent.addTextChangedListener(MarkwonEditorTextWatcher.withProcess(MarkwonEditor.create(markwon)));

        viewModel.getOnSuccessObserver().observe(getViewLifecycleOwner(), value -> {
            if (value) navToMain();
        });
        viewModel.getToastObserver().observe(getViewLifecycleOwner(), value -> {
            if (!value.isEmpty()) Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show();
        });

        return binding.getRoot();
    }

    public void navToMain() {
        requireActivity().onBackPressed();
    }

    public void addNote(String note_title, String note_content) {
        int[] colors = requireContext().getResources().getIntArray(R.array.colors);
        viewModel.addNote(note_title, note_content, colors);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        super.onCreate(savedInstanceState);
    }
}