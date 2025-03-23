package com.lsdapps.uni.bookmoth_library.library.data.repo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;
import com.lsdapps.uni.bookmoth_library.library.data.remote.LibApiService;
import com.lsdapps.uni.bookmoth_library.library.data.remote.RetrofitClient;
import com.lsdapps.uni.bookmoth_library.library.core.InnerCallback;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkRepository {
    private LibApiService api;

    public WorkRepository() {
        api = RetrofitClient.getInstance().create(LibApiService.class);
    }

    public void getChapterContent(String content_url, InnerCallback<ResponseBody> callback) {

    }

    public void getWorkCover(String cover_url, InnerCallback<Bitmap> callback) {
        api.getWorkCover(cover_url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    byte[] imageData;
                    try {
                        imageData = response.body().bytes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    callback.onSuccess(BitmapFactory.decodeByteArray(imageData, 0, imageData.length));
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
        });
    }

    public void getOwnedWorks(String token, InnerCallback<List<Work>> callback) {
        api.getOwnedWorks(token).enqueue(NoProcessingCallback.make(callback));
    }

    public void getWorkById(int work_id, InnerCallback<Work> callback) {
        api.getWorkById(work_id).enqueue(NoProcessingCallback.make(callback));
    }

    public void getChaptersOfWork(int work_id, Map<String, String> args, InnerCallback<List<Chapter>> callback) {

    }

    public void getChapterById(int chapter_id, InnerCallback<Chapter> callback) {

    }

    public void getWorks(Map<String, String> args, InnerCallback<List<Work>> callback) {
        if (args == null) {
            args = Collections.emptyMap();
        }
        api.getWorks(args).enqueue(NoProcessingCallback.make(callback));
    }
}
