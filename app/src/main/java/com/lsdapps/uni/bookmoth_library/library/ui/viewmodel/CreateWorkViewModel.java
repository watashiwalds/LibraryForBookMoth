package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lsdapps.uni.bookmoth_library.library.core.AppConst;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.core.utils.ValueGen;
import com.lsdapps.uni.bookmoth_library.library.data.repo.LibApiRepository;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.domain.usecase.CreateWorkUseCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateWorkViewModel extends AndroidViewModel {
    private final CreateWorkUseCase createWork = new CreateWorkUseCase(new LibApiRepository());

    private final MutableLiveData<Bundle> infoBundle = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();

    public CreateWorkViewModel(@NonNull Application application) {
        super(application);
    }

    public void setInfoBundle(Bundle infos) {
        infoBundle.setValue(infos);

        File coverImage = new File("");
//        TODO: Make a working image parsing
        ValueGen.makeEmptyFile(coverImage);

        Work work =  new Work();
        work.setProfile_id(AppConst.PROFILE_ID);
        work.setTitle(infos.getString("title"));
        work.setPrice((double) infos.getInt("price"));
        work.setDescription(infos.getString("description"));

        createWork.createWork(AppConst.TEST_TOKEN, coverImage, work, new InnerCallback<String>() {
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

    public LiveData<String> getMessage() {return message;}
}
