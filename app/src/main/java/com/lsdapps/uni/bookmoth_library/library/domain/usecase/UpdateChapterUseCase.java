package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;

import java.io.File;

public class UpdateChapterUseCase {
    private LibApiRepository repo;
    public UpdateChapterUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(String token, int chapter_id, File content, String filename, Chapter info, InnerCallback<String> callback) {
        repo.putChapter(token, chapter_id, content, filename, info, callback);
    }
}
