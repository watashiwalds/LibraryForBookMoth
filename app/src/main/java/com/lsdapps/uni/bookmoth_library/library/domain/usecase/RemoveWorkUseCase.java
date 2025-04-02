package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;

public class RemoveWorkUseCase {
    private LibApiRepository repo;
    public RemoveWorkUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(String token, int work_id, InnerCallback<String> callback) {
        repo.deleteWork(token, work_id, callback);
    }
}
