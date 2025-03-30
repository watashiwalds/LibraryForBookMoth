package com.lsdapps.uni.bookmoth_library.library.ui.viewmodel;

import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lsdapps.uni.bookmoth_library.library.domain.usecase.ManageSettingUseCase;

public class ReaderColorAdjustViewModel extends ViewModel {
    private final MutableLiveData<Integer> brightness = new MutableLiveData<>();
    private final MutableLiveData<Integer> colorTint = new MutableLiveData<>();
    private final MutableLiveData<Integer> textColor = new MutableLiveData<>();
    private final MutableLiveData<Integer> frameColor = new MutableLiveData<>();

    public MutableLiveData<Integer> getBrightness() {return brightness;}
    public MutableLiveData<Integer> getColorTint() {return colorTint;}
    public MutableLiveData<Integer> getTextColor() {return textColor;}
    public MutableLiveData<Integer> getFrameColor() {return frameColor;}

    public void setBrightness(int value) {
        brightness.setValue(value);
        setting.setReaderBrightness(value);
    }
    public void setColorTint(int value) {colorTint.setValue(value);}
    public void setTextColor(int rid) {textColor.setValue(rid);}
    public void setFrameColor(int rid) {frameColor.setValue(rid);}

    private ManageSettingUseCase setting;
    public void loadSettings(ManageSettingUseCase setting) {
        setColorTint(Color.parseColor("#FF000000"));

        this.setting = setting;
        setBrightness(setting.getReaderBrightness());
    }
}
