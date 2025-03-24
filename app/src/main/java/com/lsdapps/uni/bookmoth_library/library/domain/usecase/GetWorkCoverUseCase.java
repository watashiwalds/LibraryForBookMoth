package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import android.graphics.Bitmap;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.ApiRepository;

public class GetWorkCoverUseCase {
    private final ApiRepository repo;

    public GetWorkCoverUseCase(ApiRepository repo) {
        this.repo = repo;
    }

    public void run(String cover_url, InnerCallback<Bitmap> callback) {
        repo.getWorkCover(cover_url, callback);
    }
}
