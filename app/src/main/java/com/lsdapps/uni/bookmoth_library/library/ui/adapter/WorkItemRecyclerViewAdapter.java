package com.lsdapps.uni.bookmoth_library.library.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.ui.viewholder.WorkItemRecyclerViewHolder;

import java.util.List;

public class WorkItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Work> works;
    private OnItemClickListener listener;

    public WorkItemRecyclerViewAdapter(List<Work> works, OnItemClickListener listener) {
        this.works = works;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_card, parent, false);
        return new WorkItemRecyclerViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WorkItemRecyclerViewHolder hdr = (WorkItemRecyclerViewHolder) holder;
        Work item = works.get(position);

        hdr.title.setText(String.valueOf(item.getTitle()));
        hdr.author.setText(String.valueOf(item.getAuthor_name()));
        if (item.getCover_url() != null && !item.getCover_url().equalsIgnoreCase("null"))
            Glide.with(holder.itemView)
                    .load(AppConst.API_URL + AppConst.CDN_COVER + item.getCover_url() + "?v=" + System.currentTimeMillis())
                    .into(hdr.cover);
    }

    @Override
    public int getItemCount() {
        return works.size();
    }
}
