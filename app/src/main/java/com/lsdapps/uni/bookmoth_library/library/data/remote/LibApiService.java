package com.lsdapps.uni.bookmoth_library.library.data.remote;

import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface LibApiService {
    @Streaming
    @GET("/libapi/cdn/read/{content}")
    Call<ResponseBody> getChapterContent(@Path("content") String content_url);

    @GET("/libapi/cdn/cover/{cover}")
    Call<ResponseBody> getWorkCover(@Path("cover") String cover_url);

    @GET("/libapi/owned")
    Call<List<Work>> getOwnedWorks(@Header("Authorization") String token);

    @GET("/libapi/works")
    Call<List<Work>> getWorks(@QueryMap Map<String, String> query);

    @GET("/libapi/work/{wid}")
    Call<Work> getWorkById(@Path("wid") int work_id);

    @GET("/libapi/work/{wid}/chapters")
    Call<List<Chapter>> getChaptersOfWork(@Path("wid") int work_id, @QueryMap Map<String, String> query);

    @GET("/libapi/chapter/{cid}")
    Call<Chapter> getChapterById(@Path("cid") int chapter_id);
}
