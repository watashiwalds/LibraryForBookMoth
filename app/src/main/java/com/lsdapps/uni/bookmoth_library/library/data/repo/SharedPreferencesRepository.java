package com.lsdapps.uni.bookmoth_library.library.data.repo;

import android.content.SharedPreferences;

public class SharedPreferencesRepository {
    private static final String SETTING_FONTFAMILY = "font_family";

    private final SharedPreferences setting;

    public SharedPreferencesRepository(SharedPreferences setting) {
        this.setting = setting;
    }

    public void saveReaderFont(String fontName) {
        setting.edit().putString(SETTING_FONTFAMILY, fontName).apply();
    }
    public String getReaderFont() {
        return setting.getString(SETTING_FONTFAMILY, "alegreya");
    }
}
