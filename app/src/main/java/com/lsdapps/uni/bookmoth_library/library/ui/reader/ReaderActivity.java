package com.lsdapps.uni.bookmoth_library.library.ui.reader;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.utils.UniversalAnimate;

public class ReaderActivity extends AppCompatActivity {
    NestedScrollView nestedContainer;
    BottomAppBar headerBar;
    BottomAppBar bottomBar;

    TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initObjects();
        initGraphical();
        initFunctions();
    }

    private void initObjects() {
        nestedContainer = findViewById(R.id.rdr_nsv_content);
        headerBar = findViewById(R.id.rdr_tb_header);
        bottomBar = findViewById(R.id.rdr_tb_bottom);
        contentView = findViewById(R.id.rdr_tv_content);
    }

    private Boolean headerBarVisible = true;
    private void initGraphical() {
        headerBar.addView(getLayoutInflater().inflate(R.layout.toolbar_rdr_header, headerBar, false));
        bottomBar.addView(getLayoutInflater().inflate(R.layout.toolbar_rdr_bottom, bottomBar, false));
        nestedContainer.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > oldScrollY) {
                // Hide Toolbar
                if (headerBarVisible) {
                    headerBarVisible = false;
                    UniversalAnimate.animateWallHiding(headerBar, UniversalAnimate.PLACEMENT_TOP, true);
                }
            } else if (scrollY < oldScrollY) {
                // Show Toolbar
                if (!headerBarVisible) {
                    headerBarVisible = true;
                    UniversalAnimate.animateWallHiding(headerBar, UniversalAnimate.PLACEMENT_TOP, false);
                }
            }
        });
        contentView.setOnClickListener(view -> {
            Toast.makeText(this, "Clicked the NestedView", Toast.LENGTH_SHORT).show();
            UniversalAnimate.animateWallHiding(bottomBar, UniversalAnimate.PLACEMENT_BOTTOM, bottomBar.getTranslationX() <= 0);
            UniversalAnimate.animateWallHiding(headerBar, UniversalAnimate.PLACEMENT_TOP, headerBar.getTranslationX() >= 0);
        });
    }

    private void initFunctions() {
        headerBar.findViewById(R.id.imgbtn_back).setOnClickListener(v -> {
            finish();
        });
    }
}