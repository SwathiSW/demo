package com.example.myapplication.view_Model;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.interfaces.Api_Interface;
import com.example.myapplication.activities.SearchActivity;
import com.example.myapplication.classes.Retrofit_Instance;
import com.example.myapplication.models.model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class name_viewmodel extends ViewModel {
    int i = 1;

    private MutableLiveData<com.example.myapplication.models.model> model;

    public name_viewmodel() {
        model = new MutableLiveData<>();
    }

    public MutableLiveData<model> getName_model() {
        return model;
    }

    public void makeApiCall() {
        Api_Interface inter = Retrofit_Instance.getRetrofit().create(Api_Interface.class);

        for (i = 1; i <= 83; i++) {
            Call<model> call = inter.getData(i);
            call.enqueue(new Callback<com.example.myapplication.models.model>() {
                @Override
                public void onResponse(Call<com.example.myapplication.models.model> call, Response<com.example.myapplication.models.model> response) {
                    SearchActivity.pb.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        model.postValue(response.body());
                    }

                }

                @Override
                public void onFailure(Call<com.example.myapplication.models.model> call, Throwable t) {
                    model.postValue(null);
                    SearchActivity.pb.setVisibility(View.GONE);

                }
            });

        }


    }
}
