package com.lsdapps.uni.bookmoth_library.library.ui.viewholder;

import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;

public class QuickActionRecyclerViewHolder extends RecyclerView.ViewHolder {
    public FrameLayout btnNewChapter;
    public FrameLayout btnNewWork;

    public QuickActionRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        btnNewChapter = itemView.findViewById(R.id.author_fl_newchapter);
        btnNewWork = itemView.findViewById(R.id.author_fl_newwork);
    }
}
