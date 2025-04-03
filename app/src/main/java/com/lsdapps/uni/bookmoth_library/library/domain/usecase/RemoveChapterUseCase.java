package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;

public class RemoveChapterUseCase {
    private LibApiRepository repo;
    public RemoveChapterUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(String token, int chapter_id, InnerCallback<String> callback) {
        repo.deleteChapter(token, chapter_id, callback);
    }
}
