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
}
