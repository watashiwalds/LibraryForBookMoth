package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.GetChapterContentUseCase;

public class ReaderMainViewModel extends ViewModel {
    private final GetChapterContentUseCase getChapterContent = new GetChapterContentUseCase(new LibApiRepository());
    private final MutableLiveData<String> markdownString = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();

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

    private void recordRead(Chapter chapter) {}
}
