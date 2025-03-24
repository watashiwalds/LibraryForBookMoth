package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;

import java.util.List;
import java.util.Map;

public class GetChaptersOfWorkUseCase {
    private LibApiRepository repo;
    public GetChaptersOfWorkUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(int work_id, Map<String, String> args, InnerCallback<List<Chapter>> callback) {
        repo.getChaptersOfWork(work_id, args, callback);
    }
}
