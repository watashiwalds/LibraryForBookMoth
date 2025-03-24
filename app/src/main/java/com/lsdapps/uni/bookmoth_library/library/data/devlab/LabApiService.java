package com.lsdapps.uni.bookmoth_library.library.data.devlab;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface LabApiService {
    @GET("/liblab/whoami")
    Call<Integer> LAB_whoAmI(@Header("Authorization") String token);
}
