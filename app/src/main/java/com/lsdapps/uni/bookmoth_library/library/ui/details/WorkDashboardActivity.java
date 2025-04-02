package com.lsdapps.uni.bookmoth_library.library.ui.details;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.utils.DateTimeFormat;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.WorkDashboardRecyclerViewAdapter;
import com.lsdapps.uni.bookmoth_library.library.ui.viewmodel.WorkDashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class WorkDashboardActivity extends AppCompatActivity {
    private WorkDashboardViewModel viewModel;

    private RecyclerView rv;
    private WorkDashboardRecyclerViewAdapter rv_adapter;

    private Work work;
    private List<Chapter> chapters = new ArrayList<>();
    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_work_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        work = (Work) getIntent().getSerializableExtra("work");

        initObjects();
        initFunctions();
        initLiveData();

        viewModel.fetchWork(work.getWork_id());
    }

    private void initObjects() {
        viewModel = new ViewModelProvider(this).get(WorkDashboardViewModel.class);

        rv = findViewById(R.id.workdash_rv_details);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_adapter = new WorkDashboardRecyclerViewAdapter(work, chapters);
        rv.setAdapter(rv_adapter);

        btn_back = findViewById(R.id.imgbtn_back);
    }

    private void initFunctions() {
        btn_back.setOnClickListener(v -> finish());
    }

    private void initLiveData() {
        viewModel.getWorkStats().observe(this, v -> rv.post(() -> {
            ((TextView)findViewById(R.id.workdash_tv_view)).setText(String.valueOf(v.getView_count()));
            ((TextView)findViewById(R.id.workdash_tv_follow)).setText(String.valueOf(v.getFollow_count()));
            ((TextView)findViewById(R.id.workdash_tv_lastupdate)).setText(DateTimeFormat.format(v.getLast_update(), DateTimeFormat.DATE_ONLY));
            ((TextView)findViewById(R.id.workdash_tv_price)).setText(String.valueOf(v.getPrice()));
        }));
        viewModel.getChapters().observe(this, v -> {
            chapters.clear();
            chapters.addAll(v);
            rv_adapter.notifyDataSetChanged();
        });
    }
}