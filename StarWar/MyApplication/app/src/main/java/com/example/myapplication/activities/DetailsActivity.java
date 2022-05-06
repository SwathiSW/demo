package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.adapters.film_adapter;
import com.example.myapplication.models.Homeworld_model;
import com.example.myapplication.interfaces.Api_Interface_Homeworld;
import com.example.myapplication.R;
import com.example.myapplication.view_Model.films_viewmodel;
import com.example.myapplication.classes.getId;
import com.example.myapplication.models.film_model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    TextView a_name, a_skin, a_hair, a_height, a_mass, a_eye, a_homeworld, a_gender, a_birth;
    String name, skin, hair, height, eye, mass, gender, birth, homeworld, url;
    ArrayList<String> films = new ArrayList<>();
    List<film_model> fl;
    RecyclerView rec;
    film_adapter adapter;
    public static ProgressBar pb;
    films_viewmodel view_model;
    film_model mod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Character Details");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }

        a_name = findViewById(R.id.a_name);
        a_skin = findViewById(R.id.a_skin);
        a_hair = findViewById(R.id.a_hair);
        a_height = findViewById(R.id.a_height);
        a_mass = findViewById(R.id.a_mass);
        a_eye = findViewById(R.id.a_eye);
        a_homeworld = findViewById(R.id.a_homeworld);
        a_gender = findViewById(R.id.a_gender);
        a_birth = findViewById(R.id.a_birth);
        pb = findViewById(R.id.progressBar2);

        name = getIntent().getStringExtra("name");
        skin = getIntent().getStringExtra("skin");
        hair = getIntent().getStringExtra("hair");
        height = getIntent().getStringExtra("height");
        mass = getIntent().getStringExtra("mass");
        eye = getIntent().getStringExtra("eye");
        gender = getIntent().getStringExtra("gender");
        birth = getIntent().getStringExtra("dob");
        url = getIntent().getStringExtra("url");
        films = getIntent().getStringArrayListExtra("films");

        getHomeWorld(getIntent().getStringExtra("homeworld"));

        a_name.setText(name);
        a_skin.setText(skin);
        a_hair.setText(hair);
        a_height.setText(height);
        a_gender.setText(gender);
        a_eye.setText(eye);
        a_birth.setText(birth);
        a_mass.setText(mass);
        a_homeworld.setText(homeworld);

        rec = findViewById(R.id.film_rec);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        fl = new ArrayList<>();

        view_model = ViewModelProviders.of(this).get(films_viewmodel.class);
        view_model.getName_model().observe(this, new Observer<film_model>() {
            @Override
            public void onChanged(film_model model) {
                if (model != null) {
                    mod = model;
                    adapter.setData(fl);
                    fl.add(mod);
                } else {

                }

            }
        });
        view_model.makeApiCall(films);

        adapter = new film_adapter(DetailsActivity.this, fl);
        rec.setAdapter(adapter);

    }

    private void getHomeWorld(String homeworld) {

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://swapi.dev/api/")
                .build();

        Api_Interface_Homeworld inter = retrofit.create(Api_Interface_Homeworld.class);

        Call<Homeworld_model> call = inter.getData(new getId().get(url));

        call.enqueue(new Callback<Homeworld_model>() {
            @Override
            public void onResponse(Call<Homeworld_model> call, Response<Homeworld_model> response) {

                if (response.isSuccessful()) {
                    a_homeworld.setText(response.body().getName());
                }

            }

            @Override
            public void onFailure(Call<Homeworld_model> call, Throwable t) {
                a_homeworld.setText("Error");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(DetailsActivity.this, SearchActivity.class));
        return true;
    }
}