package com.lsdapps.uni.bookmoth_library.library.domain.usecase;

import com.lsdapps.uni.bookmoth_library.library.data.repo.SharedPreferencesRepository;

public class ManageSettingUseCase {
    private final SharedPreferencesRepository repo;
    public ManageSettingUseCase(SharedPreferencesRepository repo) {
        this.repo = repo;
    }

    public void setReaderFont(String fontName) {
        repo.setReaderFont(fontName);
    }
    public String getReaderFont() {
        return repo.getReaderFont();
    }
    public void setReaderTextSize(float textSize) {
        repo.setReaderTextSize(textSize);
    }
    public float getReaderTextSize() {
        return repo.getReaderTextSize();
    }
    public void setReaderBrightness(int value) {
        repo.setReaderBrightness(value);
    }
    public int getReaderBrightness() {
        return repo.getReaderBrightness();
    }
    public void setReaderTextColor(int intARGB) {repo.setReaderTextColor(intARGB);}
    public int getReaderTextColor() {return repo.getReaderTextColor();}
}
