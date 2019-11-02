package com.example.barcodescanner.retrofit;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {

    String BASE_URL = "https://api2.moodi.org/userbymino/";

    @GET("{mino}")
    Call<User> getStudent(@Path("mino") String mino);
}
