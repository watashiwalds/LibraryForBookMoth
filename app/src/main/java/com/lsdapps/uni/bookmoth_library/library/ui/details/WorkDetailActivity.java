package com.lsdapps.uni.bookmoth_library.library.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.core.utils.ErrorDialog;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetChaptersOfWorkUseCase;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.WorkDetailsRecyclerViewAdapter;
import com.lsdapps.uni.bookmoth_library.library.ui.reader.ReadingActivity;
import com.lsdapps.uni.bookmoth_library.library.ui.viewmodel.WorkDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class WorkDetailActivity extends AppCompatActivity {
    WorkDetailsViewModel viewModel;

    Work work;
    ArrayList<Chapter> chapters;
    ArrayList<Integer> readChapters;

    RecyclerView rv_workDetails;
    WorkDetailsRecyclerViewAdapter rv_workDetails_adapter;

    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_work_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setIntent(getIntent());

        initObjects();
        initFunctions();
        initLiveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rv_workDetails.post(() -> {
            viewModel.fetchData(work.getWork_id());
        });
    }

    private void initObjects() {
        viewModel = new ViewModelProvider(this).get(WorkDetailsViewModel.class);

        work = (Work) getIntent().getSerializableExtra("work");
        chapters = new ArrayList<>();
        readChapters = new ArrayList<>();

        rv_workDetails = findViewById(R.id.wkdt_rv_details);
        rv_workDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_workDetails_adapter = new WorkDetailsRecyclerViewAdapter(work, chapters, readChapters, pos -> {
            Intent reader = new Intent(this, ReadingActivity.class);
            Bundle req = ReadingActivity.makeRequirementBundle(chapters, work.getTitle(), pos);
            reader.putExtra("requirement", req);
            startActivity(reader);
        });
        rv_workDetails.setAdapter(rv_workDetails_adapter);

        btn_back = findViewById(R.id.imgbtn_back);
    }

    public void initFunctions() {
        btn_back.setOnClickListener(v -> {
            finish();
        });
    }

    private void initLiveData() {
        viewModel.getWork().observe(this, v -> {
            Work.cloneValue(work, v);
        });
        viewModel.getChapters().observe(this, v -> {
            chapters.clear();
            chapters.addAll(v);
        });
        viewModel.getReadChapters().observe(this, v -> {
            readChapters.clear();
            readChapters.addAll(v);
            rv_workDetails_adapter.notifyDataSetChanged();
        });
    }
}