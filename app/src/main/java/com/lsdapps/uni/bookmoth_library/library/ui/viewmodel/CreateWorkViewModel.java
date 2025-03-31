package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateWorkViewModel extends ViewModel {
    private final MutableLiveData<Bundle> infoBundle = new MutableLiveData<>();
    public void setInfoBundle(Bundle infos) {infoBundle.setValue(infos);}
}
