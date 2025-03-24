package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;

import java.util.List;

public class GetOwnedWorksUseCase {
    private LibApiRepository repo;
    public GetOwnedWorksUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(String token, InnerCallback<List<Work>> callback) {
        repo.getOwnedWorks(token, callback);
    }
}
