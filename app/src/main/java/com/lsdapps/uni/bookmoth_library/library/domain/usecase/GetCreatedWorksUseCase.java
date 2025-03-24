package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.ApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;

import java.util.List;

public class GetCreatedWorksUseCase {
    private ApiRepository repo;
    public GetCreatedWorksUseCase(ApiRepository repo) {this.repo = repo;}
    public void run(String token, InnerCallback<List<Work>> callback) {
        repo.getCreatedWorks(token, callback);
    }
}
