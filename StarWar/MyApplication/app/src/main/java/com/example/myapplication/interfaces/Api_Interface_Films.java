package com.example.myapplication.interfaces;

import com.example.myapplication.models.film_model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api_Interface_Films {

    @GET("films/{id}")
    Call<film_model> getData(@Path("id") int Id);

}
