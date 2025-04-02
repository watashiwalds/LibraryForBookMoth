package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;

import java.io.File;

public class AddWorkUseCase {
    private LibApiRepository repo;
    public AddWorkUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(String token, File cover, Work work, InnerCallback<String> callback) {
        repo.postWork(token, cover, work, callback);
    }
}
