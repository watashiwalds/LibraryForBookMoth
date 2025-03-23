package com.lsdapps.uni.bookmoth_library.library.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.core.utils.ErrorDialog;
import com.lsdapps.uni.bookmoth_library.library.data.repo.WorkRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetWorksUseCase;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.OnItemClickListener;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.WorkRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReaderFragment extends Fragment {
    View view;
    ArrayList<Work> works;
    RecyclerView rv_works;
    WorkRecyclerViewAdapter rv_works_adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library_reader, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        initObjects();
        fetchOwnedWorks();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initObjects() {
        works = new ArrayList<Work>();

        rv_works = view.findViewById(R.id.lib_rv_readlist);
        rv_works.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        rv_works_adapter = new WorkRecyclerViewAdapter(works, pos -> Toast.makeText(getContext(), "TEMP_ clicked " + pos, Toast.LENGTH_SHORT).show());
        rv_works.setAdapter(rv_works_adapter);
    }

    private void fetchOwnedWorks() {
        new GetWorksUseCase(new WorkRepository()).run(null, new InnerCallback<List<Work>>() {
            @Override
            public void onSuccess(List<Work> body) {
                works.addAll(body);
                rv_works_adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                ErrorDialog.showError(getParentFragment().getContext(), errorMessage);
            }
        });
    }
}
