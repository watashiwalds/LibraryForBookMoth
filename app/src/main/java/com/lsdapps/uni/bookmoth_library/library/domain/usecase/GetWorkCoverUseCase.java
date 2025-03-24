package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import android.graphics.Bitmap;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;

public class GetWorkCoverUseCase {
    private final LibApiRepository repo;

    public GetWorkCoverUseCase(LibApiRepository repo) {
        this.repo = repo;
    }

    public void run(String cover_url, InnerCallback<Bitmap> callback) {
        repo.getWorkCover(cover_url, callback);
    }
}
