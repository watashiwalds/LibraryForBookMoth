package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;

public class GetWorkByIdUseCase {
    private final LibApiRepository repo;

    public GetWorkByIdUseCase(LibApiRepository repo) {
        this.repo = repo;
    }

    public void run(int work_id, InnerCallback<Work> callback) {
        repo.getWorkById(work_id, callback);
    }
}
