package com.lsdapps.uni.bookmoth_library.library.domain.model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private int chapter_id;
    private int work_id;
    private String title;
    private String post_date;
    private String content_url;

    public Chapter() {}

    public int getChapter_id() {
        return chapter_id;
    }

    public int getWork_id() {
        return work_id;
    }

    public String getTitle() {
        return title;
    }

    public String getPost_date() {
        return post_date;
    }

    public String getContent_url() {
        return content_url;
    }
}
