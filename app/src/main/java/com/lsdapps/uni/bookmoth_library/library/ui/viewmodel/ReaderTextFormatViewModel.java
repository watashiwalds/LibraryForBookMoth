package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReaderTextFormatViewModel extends ViewModel {
        //text size
    private final MutableLiveData<Float> textSize = new MutableLiveData<>();
    public LiveData<Float> getTextSize() {return textSize;}
    public void setTextSize(float value) {textSize.setValue(value);}
}
