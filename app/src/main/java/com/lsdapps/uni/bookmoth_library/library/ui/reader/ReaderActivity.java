package com.lsdapps.uni.bookmoth_library.library.ui.reader;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

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
import com.lsdapps.uni.bookmoth_library.library.ui.viewmodel.ReaderScrollViewModel;

import java.util.List;
import java.util.Locale;

import io.noties.markwon.Markwon;

public class ReaderActivity extends AppCompatActivity {
    private ReaderScrollViewModel readerVM;
    private GetChapterContentUseCase getChapterContent;
    private FragmentManager fragmentManager;

    private NestedScrollView nestedContainer;
    private BottomAppBar headerBar;
    private BottomAppBar bottomBar;
    private TextView contentView;

    private FrameLayout rightExpansion;
    private ScrollFragment scrollFragment;

    private FrameLayout bottomExpansion;
    private final int EXPANSION_NONE = 0;
    private final int EXPANSION_TEXTFORMAT = 1;
    private final int EXPANSION_CHAPTERLIST = 2;
    private Fragment textformatFragment;
    private int nowBottomExpansion = EXPANSION_NONE;
    //TODO: Make a config loader from file for these value

    private TextView tv_title;
    private TextView tv_chapindex;

    private List<Chapter> chapters;
    private Chapter chapter;
    private int nowIndex;
    private String work_title;
    private Markwon makeMarkwon;

    private Boolean barVisible = true;

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

        readerVM = new ViewModelProvider(this).get(ReaderScrollViewModel.class);

        initObjects();
        initGraphical();
        initFunctions();
        initObservers();

        chapters = (List<Chapter>) getIntent().getSerializableExtra("chapters");
        nowIndex = getIntent().getIntExtra("index", 0);
        work_title = getIntent().getStringExtra("worktitle");

        chapter = chapters.get(nowIndex);
        displayHeaderInformation();

        fetchContent();
    }

    private void initObjects() {
        getChapterContent = new GetChapterContentUseCase(new LibApiRepository());
        fragmentManager = getSupportFragmentManager();

        nestedContainer = findViewById(R.id.rdr_nsv_content);
        headerBar = findViewById(R.id.rdr_tb_header);
        bottomBar = findViewById(R.id.rdr_tb_bottom);
        contentView = findViewById(R.id.rdr_tv_content);

        rightExpansion = findViewById(R.id.rdr_fl_rightexpand);
        bottomExpansion = findViewById(R.id.rdr_fl_bottomexpand);

        makeMarkwon = Markwon.create(this);

        expansionSetup();
    }

    private void expansionSetup() {
        scrollFragment = ScrollFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.rdr_fl_rightexpand, scrollFragment).commit();
        rightExpansion.setVisibility(View.GONE);

//        textformatFragment = TextFormatFragment.newInstance(R.id.rdr_tv_content);
//        expansion_chapterList = getLayoutInflater().inflate(R.layout.toolbar_rdr_popup_chapterlist, bottomExpansion, false);
    }

    private void setNavbarVisibility(Boolean bool) {
        if (bool && !barVisible) {
            barVisible = true;
            UniversalAnimate.animateWallHiding(headerBar, UniversalAnimate.PLACEMENT_TOP, false);
            UniversalAnimate.animateWallHiding(bottomBar, UniversalAnimate.PLACEMENT_BOTTOM, false);
            UniversalAnimate.animateWallHiding(rightExpansion, UniversalAnimate.PLACEMENT_END, false);
        } else if (!bool && barVisible) {
            barVisible = false;
            UniversalAnimate.animateWallHiding(headerBar, UniversalAnimate.PLACEMENT_TOP, true);
            UniversalAnimate.animateWallHiding(bottomBar, UniversalAnimate.PLACEMENT_BOTTOM, true);
            UniversalAnimate.animateWallHiding(rightExpansion, UniversalAnimate.PLACEMENT_END, true);
            nowBottomExpansion = EXPANSION_NONE;
            UniversalAnimate.animateWallHiding(bottomExpansion, UniversalAnimate.PLACEMENT_BOTTOM, true);
        }
    }

    private void initGraphical() {
        headerBar.addView(getLayoutInflater().inflate(R.layout.toolbar_rdr_header, headerBar, false));
        bottomBar.addView(getLayoutInflater().inflate(R.layout.toolbar_rdr_bottom, bottomBar, false));
        tv_title = headerBar.findViewById(R.id.rdr_tv_chaptitle);
        tv_chapindex = headerBar.findViewById(R.id.rdr_tv_chapindex);
    }

    private void initFunctions() {
        //hiding toolbars when scrolling, show on start/end of NestedScrollView
        nestedContainer.post(() -> {
            nestedContainer.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY == 0 || scrollY >= (nestedContainer.getChildAt(0).getHeight() - nestedContainer.getHeight() - 1)) {
                    setNavbarVisibility(true);
                    readerVM.setViewScrollPosition(scrollY);
                    return;
                }
                if (nowBottomExpansion != EXPANSION_NONE && barVisible) return;
                if (scrollY > oldScrollY) setNavbarVisibility(false);
                readerVM.setViewScrollPosition(scrollY);
            });
        });

        //toggle toolbars when click content TextView
        contentView.setOnClickListener(view -> {
            setNavbarVisibility(!barVisible);
        });

        //setup custom function when selecting text (also remove copy function)
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
        });

        //back button
        headerBar.findViewById(R.id.imgbtn_back).setOnClickListener(v -> {
            finish();
        });

        //next and previous chapter buttons
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

        //textFormat expansion
//        bottomBar.findViewById(R.id.rdr_imgbtn_textformat).setOnClickListener(v -> {
//            if (nowBottomExpansion == EXPANSION_TEXTFORMAT) {
//                UniversalAnimate.animateWallHiding(bottomExpansion, UniversalAnimate.PLACEMENT_BOTTOM, bottomExpansion.getTranslationY() == 0);
//            } else {
//                fragmentManager.beginTransaction().replace(R.id.rdr_fl_bottomexpand, textformatFragment).commit();
//                nowBottomExpansion = EXPANSION_TEXTFORMAT;
//                UniversalAnimate.animateWallHiding(bottomExpansion, UniversalAnimate.PLACEMENT_BOTTOM, false);
//            }
//        });
    }

    private void initObservers() {
        readerVM.getBarScrollPosition().observe(this, v -> nestedContainer.scrollTo(0, v));
    }

    private void initOnContentLoaded() {
        nestedContainer.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            rightExpansion.setVisibility(View.VISIBLE);
            readerVM.setHeights(this.getWindow().getDecorView().getHeight(), nestedContainer.getChildAt(0).getHeight() - nestedContainer.getHeight());
        });
    }

    private void displayHeaderInformation() {
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
        displayHeaderInformation();
        return true;
    }

    private boolean fetchPrevChapter() {
        if (nowIndex <= 0) {
            return false;
        }
        chapter = chapters.get(--nowIndex);
        fetchContent();
        displayHeaderInformation();
        return true;
    }
}