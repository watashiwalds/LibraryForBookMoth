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
import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;

import java.util.List;

public class WorkItemRecyclerViewAdapter extends RecyclerView.Adapter<WorkItemRecyclerViewAdapter.ViewHolder> {
    private List<Work> works;
    private OnItemClickListener listener;

    public WorkItemRecyclerViewAdapter(List<Work> works, OnItemClickListener listener) {
        this.works = works;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_card, parent, false);
        return new ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Work item = works.get(position);

        holder.title.setText(String.valueOf(item.getTitle()));
        holder.author.setText(String.valueOf(item.getAuthor_name()));
        if (item.getCover_url() != null && !item.getCover_url().isBlank())
            Glide.with(holder.itemView)
                .load(AppConst.API_URL + AppConst.CDN_COVER + item.getCover_url())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return works.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title;
        TextView author;
        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            cover = itemView.findViewById(R.id.awork_img_cover);
            title = itemView.findViewById(R.id.awork_tv_title);
            author = itemView.findViewById(R.id.awork_tv_author);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pos);
                    }
                }
            });
        }
    }
}
