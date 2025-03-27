package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.remote.LibApiService;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;

public class GetChapterContentUseCase {
    private LibApiRepository repo;
    public GetChapterContentUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(String token, String content_url, InnerCallback<String> callback) {
        repo.getChapterContent(token, content_url, callback);
    }
}
