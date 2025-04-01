package com.lsdapps.uni.bookmoth_library.library.data.remote;

import com.lsdapps.uni.bookmoth_library.library.domain.model.Chapter;
import com.lsdapps.uni.bookmoth_library.library.domain.model.Work;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface LibApiService {
    //CDN Call
    @Streaming
    @GET("/libapi/cdn/read/{content}")
    Call<ResponseBody> getChapterContent(@Header("Authorization") String token, @Path("content") String content_url);

    @GET("/libapi/cdn/cover/{cover}")
    Call<ResponseBody> getWorkCover(@Path("cover") String cover_url);

    //Work-related list call
    @GET("/libapi/owned")
    Call<List<Work>> getOwnedWorks(@Header("Authorization") String token);

    @GET("/libapi/created")
    Call<List<Work>> getCreatedWorks(@Header("Authorization") String token);

    @GET("/libapi/works")
    Call<List<Work>> getWorks(@QueryMap Map<String, String> query);

    @GET("/libapi/work/{wid}/chapters")
    Call<List<Chapter>> getChaptersOfWork(@Path("wid") int work_id, @QueryMap Map<String, String> query);

    //Individual object call
    @GET("/libapi/work/{wid}")
    Call<Work> getWorkById(@Path("wid") int work_id);

    @GET("/libapi/chapter/{cid}")
    Call<Chapter> getChapterById(@Path("cid") int chapter_id);

    @Multipart
    @POST("/libapi/work/post")
    Call<ResponseBody> postWork(
            @Header("Authorization") String token,
            @Part MultipartBody.Part cover,
            @Part("json") RequestBody info);

    @Multipart
    @POST("/libapi/work/{wid}/chapter/post")
    Call<ResponseBody> postChapter(
        @Header("Authorization") String token,
        @Path("wid") int work_id,
        @Part MultipartBody.Part content,
        @Part("json") RequestBody info
    );
}
