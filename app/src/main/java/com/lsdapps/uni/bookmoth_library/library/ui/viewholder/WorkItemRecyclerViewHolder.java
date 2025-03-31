package com.lsdapps.uni.bookmoth_library.library.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.OnItemClickListener;

public class WorkItemRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ImageView cover;
    public TextView title;
    public TextView author;

    public WorkItemRecyclerViewHolder(@NonNull View itemView, OnItemClickListener listener) {
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
