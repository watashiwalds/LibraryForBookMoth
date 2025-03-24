package com.lsdapps.uni.bookmoth_library.library.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.ApiConst;
import com.lsdapps.uni.bookmoth_library.library.core.utils.DateTimeFormat;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.ui.viewholder.ChapterItemRecyclerViewHolder;
import com.lsdapps.uni.bookmoth_library.library.ui.viewholder.WorkInfosCardRecyclerViewHolder;

import java.util.List;
import java.util.Locale;

public class WorkDetailsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int VIEWTYPE_HEADER = 0;
    private static int VIEWTYPE_ITEM = 1;

    private Work work;
    private List<Chapter> chapters;
    private OnItemClickListener chapterClick;

    public WorkDetailsRecyclerViewAdapter(Work work, List<Chapter> chapters, OnItemClickListener chapterClick) {
        this.work = work;
        this.chapters = chapters;
        this.chapterClick = chapterClick;
    }

    @Override
    public int getItemViewType(int pos) {
        return pos == 0 ? VIEWTYPE_HEADER : VIEWTYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEWTYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_workdetail_overallinfos, parent, false);
            return new WorkInfosCardRecyclerViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_list, parent, false);
            return new ChapterItemRecyclerViewHolder(v, chapterClick);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WorkInfosCardRecyclerViewHolder) {
            WorkInfosCardRecyclerViewHolder hdr = (WorkInfosCardRecyclerViewHolder) holder;
            if (work.getCover_url() != null && !work.getCover_url().isBlank()) {
                Glide.with(hdr.cover)
                        .load(ApiConst.API_URL + ApiConst.CDN_COVER + work.getCover_url())
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(hdr.cover);
            }
            hdr.author.setText(work.getAuthor_name());
            hdr.title.setText(work.getTitle());
            hdr.pdate.setText(DateTimeFormat.format(work.getPost_date(), DateTimeFormat.DATE_ONLY));
            hdr.viewcount.setText(String.valueOf(work.getView_count()));
            hdr.price.setText(String.format(Locale.getDefault(), "%.2f", work.getPrice()));
            hdr.desc.setText(work.getDescription());
        } else if (holder instanceof ChapterItemRecyclerViewHolder) {
            ChapterItemRecyclerViewHolder hdr = (ChapterItemRecyclerViewHolder) holder;
            Chapter item = chapters.get(position - 1); //exclude title in RecyclerView
            hdr.index.setText(item.getContent_url().split("\\.")[0].split("_")[1]);
            hdr.pdate.setText(DateTimeFormat.format(item.getPost_date(), DateTimeFormat.DATE_TIME));
            hdr.title.setText(item.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return chapters.size() + 1;
    }
}
