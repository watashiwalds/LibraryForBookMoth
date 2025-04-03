package com.lsdapps.uni.bookmoth_library.library.domain.model;

public class ReadHistory {
    private int chapter_id;
    private int work_id;
    private String post_date;
    private String read_date;

    public ReadHistory(int chapter_id, int work_id, String post_date, String read_date) {
        this.chapter_id = chapter_id;
        this.work_id = work_id;
        this.post_date = post_date;
        this.read_date = read_date;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public int getWork_id() {
        return work_id;
    }

    public String getPost_date() {
        return post_date;
    }

    public String getRead_date() {
        return read_date;
    }
}
