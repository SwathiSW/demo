package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "TAGGG";
    ArrayList<String> arr = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        fetchData();



    }

    private void fetchData() {
        Retrofit retro = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://swapi.dev/api/")
                .build();

        Api_Interface inter = retro.create(Api_Interface.class);

        for (int i = 1; i < 82; i++) {
            Call<model> call = inter.getData(i);
            call.enqueue(new Callback<model>() {
                @Override
                public void onResponse(Call<model> call, Response<model> response) {

                    if(response.code() != 404) {
                        model mod = response.body();

                        String name = mod.getName();
                        String language = mod.getLanguage();
                        String population = mod.getPopulation();
                        String birth_year = mod.getBirth_year();
                        String homeworld = mod.getHomeworld();
                        String opening_crawl = mod.getOpening_crawl();
                        arr = mod.getArr();

                    }

                }

                @Override
                public void onFailure(Call<model> call, Throwable t) {
                    Toast.makeText(MainActivity2.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }



    }
}