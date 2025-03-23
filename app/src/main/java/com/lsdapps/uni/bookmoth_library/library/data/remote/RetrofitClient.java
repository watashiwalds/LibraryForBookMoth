package com.lsdapps.uni.bookmoth_library.library.data.remote;

import com.lsdapps.uni.bookmoth_library.library.core.ApiConst;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit rtf;

    public static Retrofit getInstance() {
        if (rtf == null) {
            rtf = new Retrofit.Builder()
                    .baseUrl(ApiConst.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rtf;
    }
}
