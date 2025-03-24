package com.lsdapps.uni.bookmoth_library.library.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.ApiConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.core.utils.ErrorDialog;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetOwnedWorksUseCase;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.WorkItemRecyclerViewAdapter;
import com.lsdapps.uni.bookmoth_library.library.ui.workdetail.WorkDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ReaderFragment extends Fragment {
    View view;
    ArrayList<Work> works;
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
        fetchOwnedWorks();
    }

    private void initObjects() {
        works = new ArrayList<Work>();

        rv_works = view.findViewById(R.id.lib_rv_writelist);
        rv_works.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        rv_works_adapter = new WorkItemRecyclerViewAdapter(works, pos -> {
            Intent detailsActiv = new Intent(getContext(), WorkDetailActivity.class);
            detailsActiv.putExtra("work", works.get(pos));
            startActivity(detailsActiv);
        });
        rv_works.setAdapter(rv_works_adapter);
    }

    private void fetchOwnedWorks() {
        new GetOwnedWorksUseCase(new LibApiRepository()).run(ApiConst.TEST_TOKEN, new InnerCallback<List<Work>>() {
            @Override
            public void onSuccess(List<Work> body) {
                if (!works.isEmpty()) works.clear();
                works.addAll(body);
                rv_works_adapter.notifyItemRangeInserted(0, works.size());
            }

            @Override
            public void onError(String errorMessage) {
                ErrorDialog.showError(getParentFragment().getContext(), errorMessage);
            }
        });
    }
}
