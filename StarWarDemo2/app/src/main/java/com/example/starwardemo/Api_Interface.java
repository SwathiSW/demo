package com.example.starwardemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api_Interface {

    @GET("people/{id}/")
    Call<model> getData(@Path("id")int Id);

}
