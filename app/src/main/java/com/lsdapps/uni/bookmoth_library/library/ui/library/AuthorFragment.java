package com.lsdapps.uni.bookmoth_library.library.ui.library;

import android.os.Bundle;
import android.util.Log;
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
import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.core.utils.ErrorDialog;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetCreatedWorksUseCase;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.AuthorPageRecyclerViewAdapter;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.WorkItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AuthorFragment extends Fragment {
    GetCreatedWorksUseCase getCreatedWorks;
    View view;
    ArrayList<Work> works;
    RecyclerView rv_works;
    GridLayoutManager rv_layoutManager;
    AuthorPageRecyclerViewAdapter rv_works_adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library_author, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initObjects();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) fetchCreatedWorks();
    }

    private void initObjects() {
        getCreatedWorks = new GetCreatedWorksUseCase(new LibApiRepository());

        works = new ArrayList<Work>();

        rv_works = view.findViewById(R.id.lib_rv_writelist);
        rv_layoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        doLayoutConfig();
        rv_works.setLayoutManager(rv_layoutManager);

        rv_works_adapter = new AuthorPageRecyclerViewAdapter(works);
        rv_works.setAdapter(rv_works_adapter);
    }

    private void doLayoutConfig() {
        rv_layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == AuthorPageRecyclerViewAdapter.VIEWTYPE_QUICKACTION) return 3;
                return 1;
            }
        });
    }

    private void fetchCreatedWorks() {
        Log.d("reader fetchWorks", "fetchCreatedWorks: ");
        getCreatedWorks.run(AppConst.TEST_TOKEN, new InnerCallback<List<Work>>() {
            @Override
            public void onSuccess(List<Work> body) {
                if (!works.isEmpty()) works.clear();
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
