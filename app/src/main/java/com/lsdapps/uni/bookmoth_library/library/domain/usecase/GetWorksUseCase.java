package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.data.repo.ApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;

import java.util.List;
import java.util.Map;

public class GetWorksUseCase {
    private final ApiRepository repo;

    public GetWorksUseCase(ApiRepository repo) {
        this.repo = repo;
    }

    public void run(Map<String, String> args, InnerCallback<List<Work>> callback) {
        repo.getWorks(args, callback);
    }
}
