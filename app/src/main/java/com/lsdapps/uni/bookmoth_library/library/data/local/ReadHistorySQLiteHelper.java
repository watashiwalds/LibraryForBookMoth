package com.lsdapps.uni.bookmoth_library.library.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.core.utils.DateTimeFormat;
import com.lsdapps.uni.bookmoth_library.library.domain.model.ReadHistory;

public class ReadHistorySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLENAME = "ReadHistories";
    public static final String ID = "chapter_id";
    public static final String WORK = "work_id";
    public static final String POSTDATE = "post_date";
    public static final String READDATE = "read_date";

    public ReadHistorySQLiteHelper(@Nullable Context context) {
        super(context, AppConst.SQLITEDB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + TABLENAME + " ( " +
                ID + " interger primary key, " +
                WORK + " interger, " +
                POSTDATE + " datetime, " +
                READDATE + " datetime )";
        sqLiteDatabase.execSQL(query);
    }

    public void record(ReadHistory rec) {
        ContentValues cv = new ContentValues();
        cv.put(ID, rec.getChapter_id());
        cv.put(WORK, rec.getWork_id());
        cv.put(POSTDATE, rec.getPost_date());
        cv.put(READDATE, rec.getRead_date());
        if (!isRead(rec.getChapter_id())) insert(cv);
        else update(rec.getChapter_id(), cv);
    }

    public boolean isRead(int chapter_id) {
        Cursor cr = getReadableDatabase().rawQuery("select " + ID + " from " + TABLENAME + " where " + ID + " = " + chapter_id, null);
        cr.moveToFirst();
        return !cr.isAfterLast();
    }

    public boolean isOutdated(int chapter_id, String post_date) {
        Cursor cr = getReadableDatabase().rawQuery("select " + POSTDATE + " from " + TABLENAME + " where " + ID + " = " + chapter_id, null);
        cr.moveToFirst();
        boolean res;
        if (cr.isAfterLast())
            res = true;
        else {
            String recorded_date = cr.getString(cr.getColumnIndexOrThrow(POSTDATE));
            res = !recorded_date.equalsIgnoreCase(DateTimeFormat.format(post_date, DateTimeFormat.SQLITE));
            if (res) {
                ContentValues cv = new ContentValues();
                cv.put(POSTDATE, DateTimeFormat.format(post_date, DateTimeFormat.SQLITE));
                update(chapter_id, cv);
            }
        }
        return res;
    }

    private void insert(ContentValues cv) {
        getWritableDatabase().insert(TABLENAME, null, cv);
    }

    private void update(int chapter_id, ContentValues cv) {
        if (cv.containsKey(ID)) cv.remove(ID);
        getWritableDatabase().update(TABLENAME, cv, ID + " = " + chapter_id, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
