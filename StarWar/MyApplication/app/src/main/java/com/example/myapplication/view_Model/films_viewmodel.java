package com.example.myapplication.view_Model;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.interfaces.Api_Interface_Films;
import com.example.myapplication.activities.DetailsActivity;
import com.example.myapplication.classes.Retrofit_Instance;
import com.example.myapplication.classes.getId;
import com.example.myapplication.models.film_model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class films_viewmodel extends ViewModel {
    int i = 1;

    private MutableLiveData<film_model> model;

    public films_viewmodel() {
        model = new MutableLiveData<>();
    }

    public MutableLiveData<film_model> getName_model() {
        return model;
    }

    public void makeApiCall(ArrayList<String> arr) {
        Api_Interface_Films inter = Retrofit_Instance.getRetrofit().create(Api_Interface_Films.class);

        for (i = 0; i < arr.size(); i++) {
            Call<film_model> call = inter.getData(new getId().get(arr.get(i)));
            call.enqueue(new Callback<film_model>() {
                @Override
                public void onResponse(Call<film_model> call, Response<film_model> response) {
                    DetailsActivity.pb.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        model.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<film_model> call, Throwable t) {
                    model.postValue(null);
                    DetailsActivity.pb.setVisibility(View.GONE);
                }
            });

        }

    }
}
