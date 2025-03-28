package com.lsdapps.uni.bookmoth_library.library.ui.reader;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private int nestedContainer_id;

    public ScrollFragment() {}

    public static ScrollFragment newInstance(int nestedContainer_id) {
        ScrollFragment sf = new ScrollFragment();
        Bundle args = new Bundle();
        args.putInt("nestedContainer_id", nestedContainer_id);
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nestedContainer_id = getArguments().getInt("nestedContainer_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reader_scroll, container, false);
        scrollBar = v.findViewById(R.id.rdr_sb_scroll);
        scrolledPage = v.findViewById(R.id.rdr_tv_scroll);
        nowPage = 0;
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nestedContainer = getActivity().findViewById(nestedContainer_id);
        initialize();
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