package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddChapterViewModel extends ViewModel {
    private final MutableLiveData<Bundle> infoBundle = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();

    public void setInfoBundle(Bundle infos) {
        infoBundle.setValue(infos);
    }

    public LiveData<String> getMessage() {return message;}
}
