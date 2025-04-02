package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.UpdateWorkUseCase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class UpdateWorkViewModel extends ViewModel {
    private final UpdateWorkUseCase updateWork = new UpdateWorkUseCase(new LibApiRepository());
    private final MutableLiveData<String> message = new MutableLiveData<>();

    public LiveData<String> getMessage() {return message;}

    public void doUpdateWork(Bundle infos, Context context) {
        File coverImage = null;
        if (infos.getParcelable("cover_uri") != null) try {
            InputStream inpStream = context.getContentResolver().openInputStream(infos.getParcelable("cover_uri"));
            if (!(inpStream == null)) {
                coverImage = new File(context.getCacheDir(), "coverImage");
                FileOutputStream outStream = new FileOutputStream(coverImage);
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

        Work workInfos = new Work();
        workInfos.setProfile_id(AppConst.PROFILE_ID);
        if (infos.containsKey("title")) workInfos.setTitle(infos.getString("title"));
        if (infos.containsKey("price")) workInfos.setPrice((double) infos.getInt("price"));
        if (infos.containsKey("description")) workInfos.setDescription(infos.getString("description"));

        updateWork.run(AppConst.TEST_TOKEN, infos.getInt("work_id"), coverImage, workInfos, new InnerCallback<String>() {
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
