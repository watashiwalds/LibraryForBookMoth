package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.data.repo.ReadHistoryRepo;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetChaptersOfWorkUseCase;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetWorkByIdUseCase;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.ReadHistoryUseCase;

import java.util.ArrayList;
import java.util.List;

public class WorkDetailsViewModel extends AndroidViewModel {
    public LiveData<List<Chapter>> getChapters() {return chapters;}

    private final GetChaptersOfWorkUseCase getChaptersOfWork = new GetChaptersOfWorkUseCase(new LibApiRepository());
    private final MutableLiveData<List<Chapter>> chapters = new MutableLiveData<>();
    public WorkDetailsViewModel(@NonNull Application application) {
        super(application);
    }
    private void fetchChapters(int work_id) {
        getChaptersOfWork.run(work_id, null, new InnerCallback<List<Chapter>>() {
            @Override
            public void onSuccess(List<Chapter> body) {
                chapters.setValue(body);
                makeReadChapters(body);
            }

            @Override
            public void onError(String errorMessage) {}
        });
    }

    private final ReadHistoryUseCase readHistory = new ReadHistoryUseCase(new ReadHistoryRepo(getApplication()));
    private final MutableLiveData<List<Integer>> readChapters = new MutableLiveData<>();
    public LiveData<List<Integer>> getReadChapters() {return readChapters;}
    private void makeReadChapters(List<Chapter> chapters) {
        List<Integer> reads = new ArrayList<>();
        for (Chapter c : chapters) {
            if (!readHistory.isOutdated(c.getChapter_id(), c.getPost_date())) reads.add(c.getChapter_id());
        }
        readChapters.setValue(reads);
        Log.d("READED MODEL", String.valueOf(reads.size()));
    }

    private final GetWorkByIdUseCase getWorkById = new GetWorkByIdUseCase(new LibApiRepository());
    private final MutableLiveData<Work> work = new MutableLiveData<>();

    public LiveData<Work> getWork() {return work;}
    private void fetchWork(int work_id) {
        getWorkById.run(work_id, new InnerCallback<Work>() {
            @Override
            public void onSuccess(Work body) {
                work.setValue(body);
                fetchChapters(work_id);
            }

            @Override
            public void onError(String errorMessage) {}
        });
    }

    public void fetchData(int work_id) {
        fetchWork(work_id);
    }
}
