package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.data.repo.ReadHistoryRepo;
import com.lsdapps.uni.bookmoth_library.library.domain.model.ReadHistory;

public class ReadHistoryUseCase {
    private ReadHistoryRepo repo;
    public ReadHistoryUseCase(ReadHistoryRepo repo) {this.repo = repo;}
    public void record(ReadHistory rh) {repo.record(rh);}
    public boolean isRead(int chapter_id) {return repo.isRead(chapter_id);}
    public boolean isOutdated(int chapter_id, String post_date) {return repo.isOutdated(chapter_id, post_date);}
}
