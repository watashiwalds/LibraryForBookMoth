package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;

import java.io.File;

public class AddChapterUseCase {
    private LibApiRepository repo;
    public AddChapterUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(String token, int work_id, File content, String filename, Chapter info, InnerCallback<String> callback) {
        repo.postChapter(token, work_id, content, filename, info, callback);
    }
}
