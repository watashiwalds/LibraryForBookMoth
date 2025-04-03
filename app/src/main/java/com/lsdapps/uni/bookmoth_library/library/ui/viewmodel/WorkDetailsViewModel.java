package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetChaptersOfWorkUseCase;

import java.util.List;

public class WorkDetailsViewModel extends ViewModel {
    private final GetChaptersOfWorkUseCase getChaptersOfWork = new GetChaptersOfWorkUseCase(new LibApiRepository());
    private final MutableLiveData<List<Chapter>> chapters = new MutableLiveData<>();

    public LiveData<List<Chapter>> getChapters() {return chapters;}
    public void fetchChapters(int work_id) {
        getChaptersOfWork.run(work_id, null, new InnerCallback<List<Chapter>>() {
            @Override
            public void onSuccess(List<Chapter> body) {
                chapters.setValue(body);
            }

            @Override
            public void onError(String errorMessage) {}
        });
    }
}
