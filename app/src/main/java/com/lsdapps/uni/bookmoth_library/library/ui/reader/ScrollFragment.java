package com.lsdapps.uni.bookmoth_library.library.ui.reader;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.utils.InnerToast;

import java.util.Locale;

public class ScrollFragment extends Fragment {
    private int activity_height;
    private int content_height;

    private SeekBar scrollBar;
    private TextView scrolledPage;
    private int nowPage;

    private NestedScrollView nestedContainer;

    private ScrollFragment(NestedScrollView n) {
        activity_height = 0;
        content_height = 1;
        nestedContainer = n;
    };

    public static ScrollFragment newInstance(NestedScrollView nestedContainer) {
        return new ScrollFragment(nestedContainer);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reader_scroll, container, false);
        scrollBar = v.findViewById(R.id.rdr_sb_scroll);
        scrolledPage = v.findViewById(R.id.rdr_tv_scroll);
        nowPage = 0;
        initialize();
        return v;
    }

    private void initialize() {
        scrolledPage.setVisibility(View.GONE);
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

    private void refreshScrollObjects() {
        scrollBar.setMax(content_height);
        nowPage = 0;
    }

    void setProgress(int newProgress) {
        scrollBar.setProgress(newProgress);
    }

    void setHeightValues(int activityHeight, int contentHeight) {
        activity_height = activityHeight;
        content_height = contentHeight;
        refreshScrollObjects();
    }
}