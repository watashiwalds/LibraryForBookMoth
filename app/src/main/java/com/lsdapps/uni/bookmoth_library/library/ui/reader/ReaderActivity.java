package com.lsdapps.uni.bookmoth_library.library.ui.reader;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    private Boolean barVisible = true;
    private void setNavbarVisibility(Boolean bool) {
        if (bool && !barVisible) {
            barVisible = true;
            UniversalAnimate.animateWallHiding(headerBar, UniversalAnimate.PLACEMENT_TOP, false);
            UniversalAnimate.animateWallHiding(bottomBar, UniversalAnimate.PLACEMENT_BOTTOM, false);
        } else if (!bool && barVisible) {
            barVisible = false;
            UniversalAnimate.animateWallHiding(headerBar, UniversalAnimate.PLACEMENT_TOP, true);
            UniversalAnimate.animateWallHiding(bottomBar, UniversalAnimate.PLACEMENT_BOTTOM, true);
        }
    }

    private void initGraphical() {
        headerBar.addView(getLayoutInflater().inflate(R.layout.toolbar_rdr_header, headerBar, false));
        bottomBar.addView(getLayoutInflater().inflate(R.layout.toolbar_rdr_bottom, bottomBar, false));
        nestedContainer.post(() -> {
            nestedContainer.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY == 0 || scrollY >= (nestedContainer.getChildAt(0).getHeight() - nestedContainer.getHeight() - 1)) {
                    setNavbarVisibility(true);
                    return;
                }
                if (scrollY > oldScrollY) setNavbarVisibility(false);
            });
        });
        contentView.setOnClickListener(view -> {
            setNavbarVisibility(!barVisible);
        });
    }

    private void initFunctions() {
        contentView.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                menu.clear();
                menu.add(0, 1, 0, R.string.contentselection_discuss).setOnMenuItemClickListener(item -> {
                    Toast.makeText(contentView.getContext(), "in-dev, post related", Toast.LENGTH_SHORT).show();
                    actionMode.finish();
                    return true;
                });
                menu.add(0, 2, 1, R.string.contentselection_search).setOnMenuItemClickListener(item -> {
                    Toast.makeText(contentView.getContext(), "in-dev, post/store related", Toast.LENGTH_SHORT).show();
                    actionMode.finish();
                    return true;
                });
                menu.add(0, 3, 2, R.string.contentselection_report).setOnMenuItemClickListener(item -> {
                    Toast.makeText(contentView.getContext(), "in-dev, community related", Toast.LENGTH_SHORT).show();
                    actionMode.finish();
                    return true;
                });
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        }); //custom action for selection
        headerBar.findViewById(R.id.imgbtn_back).setOnClickListener(v -> {
            finish();
        });
    }
}