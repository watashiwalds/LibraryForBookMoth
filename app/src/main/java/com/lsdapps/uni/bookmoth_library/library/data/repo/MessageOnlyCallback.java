package com.lsdapps.uni.bookmoth_library.library.data.repo;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageOnlyCallback {
    static <ResponseBody> Callback<ResponseBody> make(InnerCallback<String> callback) {
        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().toString());
                } else {
                    try {
                        callback.onError(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.toString());
            }
        };
    }
}
