package com.lsdapps.uni.bookmoth_library.library.ui.library;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.data.repo.WorkRepository;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.WorkRecyclerViewAdapter;

import java.util.List;

public class LibraryMainFragment extends Fragment {
    Fragment readerFragment;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        readerFragment = new ReaderFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.lib_frame_main, readerFragment).commit();

        return inflater.inflate(R.layout.fragment_library_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }
}