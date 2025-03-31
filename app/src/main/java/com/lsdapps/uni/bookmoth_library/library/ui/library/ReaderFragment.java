package com.lsdapps.uni.bookmoth_library.library.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.core.utils.ErrorDialog;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetOwnedWorksUseCase;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.WorkItemRecyclerViewAdapter;
import com.lsdapps.uni.bookmoth_library.library.ui.viewmodel.LibraryWorkViewModel;
import com.lsdapps.uni.bookmoth_library.library.ui.workdetail.WorkDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ReaderFragment extends Fragment {
    LibraryWorkViewModel viewModel;
    View view;
    List<Work> works = new ArrayList<>();
    RecyclerView rv_works;
    WorkItemRecyclerViewAdapter rv_works_adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library_reader, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initObjects();
        initLiveData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) viewModel.fetchOwnedWorks();
    }

    private void initObjects() {
        viewModel = new ViewModelProvider(requireActivity()).get(LibraryWorkViewModel.class);

        rv_works = view.findViewById(R.id.lib_rv_writelist);
        rv_works.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        rv_works_adapter = new WorkItemRecyclerViewAdapter(works, pos -> {
            Intent detailsActiv = new Intent(getContext(), WorkDetailActivity.class);
            detailsActiv.putExtra("work", works.get(pos));
            startActivity(detailsActiv);
        });
        rv_works.setAdapter(rv_works_adapter);
    }

    private void initLiveData() {
        viewModel.getOwnedWorks().observe(requireActivity(), v -> {
            works.clear();
            works.addAll(v);
            rv_works_adapter.notifyDataSetChanged();
        });
    }
}
