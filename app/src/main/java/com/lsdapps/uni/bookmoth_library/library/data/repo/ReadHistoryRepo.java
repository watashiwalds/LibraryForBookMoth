package com.lsdapps.uni.bookmoth_library.library.data.repo;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.lsdapps.uni.bookmoth_library.library.data.local.ReadHistorySQLiteHelper;
import com.lsdapps.uni.bookmoth_library.library.domain.model.ReadHistory;

public class ReadHistoryRepo {
    private ReadHistorySQLiteHelper helper;
    public ReadHistoryRepo(Context context) {
        helper = new ReadHistorySQLiteHelper(context);
    }

    public void record(ReadHistory rh) {
        helper.record(rh);
    }

    public boolean isRead(int chapter_id) {
        return helper.isRead(chapter_id);
    }

    public boolean isOutdated(int chapter_id, String post_date) {
        return helper.isOutdated(chapter_id, post_date);
    }
}
