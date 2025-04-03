package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.data.repo.ReadHistoryRepo;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetChaptersOfWorkUseCase;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.ReadHistoryUseCase;

import java.util.ArrayList;
import java.util.List;

public class WorkDetailsViewModel extends AndroidViewModel {
    private final GetChaptersOfWorkUseCase getChaptersOfWork = new GetChaptersOfWorkUseCase(new LibApiRepository());
    private final MutableLiveData<List<Chapter>> chapters = new MutableLiveData<>();
    private List<Integer> readChapters = new ArrayList<>();

    public WorkDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Chapter>> getChapters() {return chapters;}
    public void fetchChapters(int work_id) {
        getChaptersOfWork.run(work_id, null, new InnerCallback<List<Chapter>>() {
            @Override
            public void onSuccess(List<Chapter> body) {
                makeReadChapters(body);
                chapters.setValue(body);
            }

            @Override
            public void onError(String errorMessage) {}
        });
    }
    public List<Integer> getReadChapters() {return readChapters;}

    private final ReadHistoryUseCase readHistory = new ReadHistoryUseCase(new ReadHistoryRepo(getApplication()));
    private void makeReadChapters(List<Chapter> chapters) {
        readChapters.clear();
        for (Chapter c : chapters) {
            if (!readHistory.isOutdated(c.getChapter_id(), c.getPost_date())) readChapters.add(c.getChapter_id());
        }
    }
}
