package com.lsdapps.uni.bookmoth_library.library.ui.workdetail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.WorkDetailsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkDetailActivity extends AppCompatActivity {
    Work work;
    ArrayList<Chapter> chapters;

    RecyclerView rv_workDetails;
    WorkDetailsRecyclerViewAdapter rv_workDetails_adapter;

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
        fetchChapters(work.getWork_id());
    }

    private void initObjects() {
        work = (Work) getIntent().getSerializableExtra("work");
        chapters = new ArrayList<>();

        rv_workDetails = findViewById(R.id.wkdt_rv_details);
        rv_workDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_workDetails_adapter = new WorkDetailsRecyclerViewAdapter(work, chapters);
        rv_workDetails.setAdapter(rv_workDetails_adapter);
    }

    private void fetchChapters(int workId) {

    }
}