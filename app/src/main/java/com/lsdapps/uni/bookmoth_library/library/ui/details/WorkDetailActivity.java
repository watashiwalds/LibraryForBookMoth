package com.lsdapps.uni.bookmoth_library.library.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
import com.lsdapps.uni.bookmoth_library.library.ui.reader.ReaderActivity;

import java.util.ArrayList;
import java.util.List;

public class WorkDetailActivity extends AppCompatActivity {
    GetChaptersOfWorkUseCase getChaptersOfWork;

    Work work;
    ArrayList<Chapter> chapters;

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
        fetchChapters(work.getWork_id());
    }

    private void initObjects() {
        getChaptersOfWork = new GetChaptersOfWorkUseCase(new LibApiRepository());

        work = (Work) getIntent().getSerializableExtra("work");
        chapters = new ArrayList<>();

        rv_workDetails = findViewById(R.id.wkdt_rv_details);
        rv_workDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_workDetails_adapter = new WorkDetailsRecyclerViewAdapter(work, chapters, pos -> {
            Intent reader = new Intent(this, ReaderActivity.class);
            Bundle req = ReaderActivity.makeRequirementBundle(chapters, work.getTitle(), pos);
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

    private void fetchChapters(int work_id) {
        getChaptersOfWork.run(work_id, null, new InnerCallback<List<Chapter>>() {
            @Override
            public void onSuccess(List<Chapter> body) {
                if (!chapters.isEmpty()) chapters.clear();
                chapters.addAll(body);
                rv_workDetails_adapter.notifyItemRangeInserted(1, chapters.size());
            }

            @Override
            public void onError(String errorMessage) {
                ErrorDialog.showError(WorkDetailActivity.this, errorMessage);
            }
        });
    }
}