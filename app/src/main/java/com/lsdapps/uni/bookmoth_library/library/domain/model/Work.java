package com.lsdapps.uni.bookmoth_library.library.domain.model;

import java.io.Serializable;

public class Work implements Serializable {
    private int work_id;
    private int profile_id;
    private String author_name;
    private String title;
    private String post_date;
    private Double price;
    private int view_count;
    private String description;
    private String cover_url;

    public Work() {}

    public int getWork_id() {
        return work_id;
    }

    public int getProfile_id() {
        return profile_id;
    }
    public String getAuthor_name() {
        return author_name;
    }

    public String getTitle() {
        return title;
    }

    public String getPost_date() {
        return post_date;
    }

    public Double getPrice() {
        return price;
    }

    public int getView_count() {
        return view_count;
    }

    public String getDescription() {
        return description;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }
}
