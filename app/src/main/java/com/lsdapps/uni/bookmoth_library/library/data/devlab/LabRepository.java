package com.lsdapps.uni.bookmoth_library.library.data.devlab;

import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;
import com.lsdapps.uni.bookmoth_library.library.data.remote.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabRepository {
    private LabApiService api;
    public LabRepository() {api = RetrofitClient.getInstance().create(LabApiService.class);}
    public void whoAmI(String token, InnerCallback<Integer> callback) {
        api.LAB_whoAmI(token).enqueue(NoProcessingCallback.make(callback));
    }
}

class NoProcessingCallback {
    static <T> Callback<T> make(InnerCallback<T> callback) {
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        callback.onError(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                callback.onError(t.toString());
            }
        };
    }
}