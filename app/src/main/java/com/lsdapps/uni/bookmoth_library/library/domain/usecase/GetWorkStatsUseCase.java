package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;

import okhttp3.ResponseBody;

public class GetWorkStatsUseCase {
    private LibApiRepository repo;
    public GetWorkStatsUseCase(LibApiRepository repo) {this.repo = repo;}
    public void run(String token, int work_id, InnerCallback<ResponseBody> callback) {
        repo.getWorkStats(token, work_id, callback);
    }
}
