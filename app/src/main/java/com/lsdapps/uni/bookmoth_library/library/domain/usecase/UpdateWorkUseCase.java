package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;

import java.io.File;

public class UpdateWorkUseCase {
    private LibApiRepository repo;
    public UpdateWorkUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(String token, int work_id, File cover, Work info, InnerCallback<String> callback) {
        repo.putWork(token, work_id, cover, info, callback);
    }
}
