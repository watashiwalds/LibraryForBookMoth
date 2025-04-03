package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.data.repo.ReadHistoryRepo;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.model.ReadHistory;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetChapterContentUseCase;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.ReadHistoryUseCase;

import java.time.Instant;
import java.time.LocalDateTime;

public class ReaderMainViewModel extends AndroidViewModel {
    private final GetChapterContentUseCase getChapterContent = new GetChapterContentUseCase(new LibApiRepository());
    private final MutableLiveData<String> markdownString = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();

    public ReaderMainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMarkdownString() {return markdownString;}
    public LiveData<String> getMessage() {return message;}
    public void fetchChapterContent(Chapter chapter) {
        getChapterContent.run(AppConst.TEST_TOKEN, chapter.getContent_url(), new InnerCallback<String>() {
            @Override
            public void onSuccess(String body) {
                markdownString.setValue(body);
                recordRead(chapter);
            }

            @Override
            public void onError(String errorMessage) {
                message.setValue(errorMessage);
            }
        });
    }

    private final ReadHistoryUseCase readHistory = new ReadHistoryUseCase(new ReadHistoryRepo(getApplication()));
    private void recordRead(Chapter chapter) {
        ReadHistory rh = new ReadHistory(chapter.getChapter_id(), chapter.getWork_id(), chapter.getPost_date());
        readHistory.record(rh);
    }
}
