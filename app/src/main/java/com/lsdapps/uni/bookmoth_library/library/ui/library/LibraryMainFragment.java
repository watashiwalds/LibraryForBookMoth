package com.lsdapps.uni.bookmoth_library.library.ui.library;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.lsdapps.uni.bookmoth_library.R;

public class LibraryMainFragment extends Fragment {
    FragmentTransaction mainFragTransact;
    Fragment readerFragment;
    Fragment authorFragment;
    View view;

    MaterialButton toggleMode;
    TextView dummyTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initObjects();
        initFunctions();
        changeListMode(MODE_READER);
    }

    private void initObjects() {
        mainFragTransact = getChildFragmentManager().beginTransaction();
        readerFragment = new ReaderFragment();
        authorFragment = new AuthorFragment();
        toggleMode = view.findViewById(R.id.lib_btnSwitchMode_main);
        dummyTitle = view.findViewById(R.id.dummyTitle);
    }

    private void initFunctions() {
        toggleMode.setOnClickListener(v -> {
            changeListMode(MODE_TOGGLE);
        });
    }

    private final int MODE_TOGGLE = 0;
    private final int MODE_READER = 1;
    private final int MODE_AUTHOR = 2;
    private void changeListMode(int switchMode) {
        switch (switchMode) {
            case MODE_TOGGLE:
                switchMode = toggleMode.getTag() == "reader" ? MODE_AUTHOR : MODE_READER;
                changeListMode(switchMode);
                break;
            case MODE_READER:
                changeFragment(readerFragment);
                changeActivityVisual(switchMode);
                break;
            case MODE_AUTHOR:
                changeFragment(authorFragment);
                changeActivityVisual(switchMode);
                break;
        }
    }

    private void changeFragment(Fragment frag) {
        getChildFragmentManager().beginTransaction().replace(R.id.lib_frame_main, frag).commit();
    }

    private void changeActivityVisual(int switchMode) {
        switch (switchMode) {
            case MODE_READER:
                toggleMode.setText(R.string.libmode_toAuthor);
                toggleMode.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_edit_square_24));
                toggleMode.setTag("reader");
                dummyTitle.setText(R.string.libmode_title_reader);
                break;
            case MODE_AUTHOR:
                toggleMode.setText(R.string.libmode_toReader);
                toggleMode.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_menu_book_24));
                toggleMode.setTag("author");
                dummyTitle.setText(R.string.libmode_title_author);
                break;
        }
    }
}