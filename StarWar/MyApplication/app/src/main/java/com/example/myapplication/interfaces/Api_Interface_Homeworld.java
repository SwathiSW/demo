package com.example.myapplication.interfaces;

import com.example.myapplication.models.Homeworld_model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api_Interface_Homeworld {

    @GET("planets/{id}")
    Call<Homeworld_model> getData(@Path("id") int Id);

}
