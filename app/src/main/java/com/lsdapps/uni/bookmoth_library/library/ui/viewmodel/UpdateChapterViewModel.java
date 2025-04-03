package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.UpdateChapterUseCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class UpdateChapterViewModel extends ViewModel {
    private final UpdateChapterUseCase updateChapter = new UpdateChapterUseCase(new LibApiRepository());
    private final MutableLiveData<String> message = new MutableLiveData<>();

    public LiveData<String> getMessage() {return message;}

    public void doUpdateChapter(Bundle infos, Context context) {
        String token = infos.getString("credential");

        File contentFile = null;
        if (infos.getParcelable("content_uri") != null) try {
            InputStream inpStream = context.getContentResolver().openInputStream(infos.getParcelable("content_uri"));
            if (!(inpStream == null)) {
                contentFile = new File(context.getCacheDir(), "contentFile");
                FileOutputStream outStream = new FileOutputStream(contentFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inpStream.read(buffer)) > 0) {
                    outStream.write(buffer, 0, length);
                }
                outStream.close();
            }
            inpStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Chapter chapter = new Chapter();
        chapter.setTitle(infos.getString("title"));

        int chapter_id = infos.getInt("chapter_id");

        String filename = infos.getString("filename");

        updateChapter.run(token, chapter_id, contentFile, filename, chapter, new InnerCallback<String>() {
            @Override
            public void onSuccess(String body) {
                message.setValue("");
            }

            @Override
            public void onError(String errorMessage) {
                message.setValue(errorMessage);
            }
        });
    }
}
