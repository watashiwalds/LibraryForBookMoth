package com.lsdapps.uni.bookmoth_library.library.ui.viewholder;

import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.OnItemClickListener;

public class WorkDashboardActionRecyclerViewHolder extends RecyclerView.ViewHolder {
    public FrameLayout fl_addChapter;
    public FrameLayout fl_deleteWork;
    public FrameLayout fl_editWork;

    public WorkDashboardActionRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        fl_addChapter = itemView.findViewById(R.id.workdash_fl_newchapter);
        fl_deleteWork = itemView.findViewById(R.id.workdash_fl_deletework);
        fl_editWork = itemView.findViewById(R.id.workdash_fl_editdetail);
    }
}
