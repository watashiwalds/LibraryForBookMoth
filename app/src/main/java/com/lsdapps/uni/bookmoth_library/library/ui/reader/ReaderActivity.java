package com.lsdapps.uni.bookmoth_library.library.ui.reader;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.SeekBar;
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
import com.lsdapps.uni.bookmoth_library.library.core.ApiConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.core.utils.ErrorDialog;
import com.lsdapps.uni.bookmoth_library.library.core.utils.InnerToast;
import com.lsdapps.uni.bookmoth_library.library.core.utils.UniversalAnimate;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetChapterContentUseCase;

import java.util.List;
import java.util.Locale;

import io.noties.markwon.Markwon;

public class ReaderActivity extends AppCompatActivity {
    int activity_height;
    int content_height;

    GetChapterContentUseCase getChapterContent;

    NestedScrollView nestedContainer;
    BottomAppBar headerBar;
    BottomAppBar bottomBar;
    TextView contentView;

    FrameLayout scrollFrame;
    SeekBar scrollBar;
    TextView scrolledPage;
    int nowPage;

    TextView tv_title;
    TextView tv_chapindex;

    List<Chapter> chapters;
    Chapter chapter;
    int nowIndex;
    String work_title;
    Markwon makeMarkwon;

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

        chapters = (List<Chapter>) getIntent().getSerializableExtra("chapters");
        nowIndex = getIntent().getIntExtra("index", 0);
        work_title = getIntent().getStringExtra("worktitle");

        chapter = chapters.get(nowIndex);
        displayInformations();

        fetchContent();
    }

    private void initObjects() {
        getChapterContent = new GetChapterContentUseCase(new LibApiRepository());

        nestedContainer = findViewById(R.id.rdr_nsv_content);
        headerBar = findViewById(R.id.rdr_tb_header);
        bottomBar = findViewById(R.id.rdr_tb_bottom);
        contentView = findViewById(R.id.rdr_tv_content);

        scrollFrame = findViewById(R.id.rdr_fl_scroll);
        scrollBar = findViewById(R.id.rdr_sb_scroll);
        scrolledPage = findViewById(R.id.rdr_tv_scroll);

        makeMarkwon = Markwon.create(this);
    }

    private Boolean barVisible = true;
    private void setNavbarVisibility(Boolean bool) {
        if (bool && !barVisible) {
            barVisible = true;
            UniversalAnimate.animateWallHiding(headerBar, UniversalAnimate.PLACEMENT_TOP, false);
            UniversalAnimate.animateWallHiding(bottomBar, UniversalAnimate.PLACEMENT_BOTTOM, false);
            UniversalAnimate.animateWallHiding(scrollFrame, UniversalAnimate.PLACEMENT_END, false);
        } else if (!bool && barVisible) {
            barVisible = false;
            UniversalAnimate.animateWallHiding(headerBar, UniversalAnimate.PLACEMENT_TOP, true);
            UniversalAnimate.animateWallHiding(bottomBar, UniversalAnimate.PLACEMENT_BOTTOM, true);
            UniversalAnimate.animateWallHiding(scrollFrame, UniversalAnimate.PLACEMENT_END, true);
        }
    }

    private void initGraphical() {
        headerBar.addView(getLayoutInflater().inflate(R.layout.toolbar_rdr_header, headerBar, false));
        bottomBar.addView(getLayoutInflater().inflate(R.layout.toolbar_rdr_bottom, bottomBar, false));
        tv_title = headerBar.findViewById(R.id.rdr_tv_chaptitle);
        tv_chapindex = headerBar.findViewById(R.id.rdr_tv_chapindex);
        scrolledPage.setVisibility(View.GONE);
        scrollFrame.setVisibility(View.GONE);
    }

    private void initFunctions() {
        nestedContainer.post(() -> {
            nestedContainer.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY == 0 || scrollY >= (nestedContainer.getChildAt(0).getHeight() - nestedContainer.getHeight() - 1)) {
                    setNavbarVisibility(true);
                    return;
                }
                if (scrollY > oldScrollY) setNavbarVisibility(false);
                scrollBar.setProgress(scrollY);
            });
        });

        contentView.setOnClickListener(view -> {
            setNavbarVisibility(!barVisible);
        });
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

        bottomBar.findViewById(R.id.rdr_imgbtn_next).setOnClickListener(v -> {
            if (!fetchNextChapter())
                InnerToast.show(this, getString(R.string.reader_nextchapter_none));
            else
                InnerToast.show(this, String.format(Locale.getDefault(), "%s %d", getString(R.string.reader_nextchapter_done), nowIndex+1));
        });
        bottomBar.findViewById(R.id.rdr_imgbtn_prev).setOnClickListener(v -> {
            if (!fetchPrevChapter())
                InnerToast.show(this, getString(R.string.reader_prevchapter_none));
            else
                InnerToast.show(this, String.format(Locale.getDefault(), "%s %d", getString(R.string.reader_prevchapter_done), nowIndex+1));
        });

        scrollBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if ((i / activity_height) + 1 != nowPage) {
                    nowPage = (i / activity_height) + 1;
                    scrolledPage.setText(String.format(Locale.getDefault(), "%d/%d", nowPage, (scrollBar.getMax() / activity_height) + 1));
                }
                nestedContainer.scrollTo(0, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                scrolledPage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                scrolledPage.setVisibility(View.GONE);
            }
        });
    }

    private void initOnContentLoaded() {
        nestedContainer.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            activity_height = this.getWindow().getDecorView().getHeight();
            content_height = nestedContainer.getChildAt(0).getHeight() - nestedContainer.getHeight();
            scrollFrame.setVisibility(View.VISIBLE);
            scrollBar.setMax(content_height);
            nowPage = 0;
        });
    }

    private void displayInformations() {
        tv_title.setText(chapter.getTitle() != null ? chapter.getTitle() : work_title);
        tv_chapindex.setText(String.format(Locale.getDefault(), "%s %d %s", getString(R.string.chapter_chapter), nowIndex + 1, (chapter.getTitle() == null ? "" : " - " + work_title)));
    }

    private void fetchContent() {
        getChapterContent.run(ApiConst.TEST_TOKEN, chapter.getContent_url(), new InnerCallback<String>() {
            @Override
            public void onSuccess(String body) {
                makeMarkwon.setMarkdown(contentView, body);
                initOnContentLoaded();
            }

            @Override
            public void onError(String errorMessage) {
                ErrorDialog.showError(ReaderActivity.this, errorMessage);
            }
        });
    }

    private boolean fetchNextChapter() {
        if (nowIndex >= chapters.size() - 1) {
            return false;
        }
        chapter = chapters.get(++nowIndex);
        fetchContent();
        displayInformations();
        return true;
    }

    private boolean fetchPrevChapter() {
        if (nowIndex <= 0) {
            return false;
        }
        chapter = chapters.get(--nowIndex);
        fetchContent();
        displayInformations();
        return true;
    }
}