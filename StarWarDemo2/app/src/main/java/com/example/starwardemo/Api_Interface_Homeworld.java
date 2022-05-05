package com.example.starwardemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api_Interface_Homeworld {
    @GET("planets/{id}")
    Call<Homeworld_model> getData(@Path("id")int Id);
}
