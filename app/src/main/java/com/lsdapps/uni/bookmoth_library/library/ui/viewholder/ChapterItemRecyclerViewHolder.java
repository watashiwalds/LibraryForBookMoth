package com.lsdapps.uni.bookmoth_library.library.ui.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.OnItemClickListener;

public class ChapterItemRecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView index;
    public TextView pdate;
    public TextView title;

    public ChapterItemRecyclerViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        index = itemView.findViewById(R.id.chapter_index);
        pdate = itemView.findViewById(R.id.chapter_pdate);
        title = itemView.findViewById(R.id.chapter_title);

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
