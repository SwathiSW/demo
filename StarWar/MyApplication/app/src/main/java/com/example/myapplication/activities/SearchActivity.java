package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.myapplication.R;
import com.example.myapplication.models.model;
import com.example.myapplication.adapters.name_adapter;
import com.example.myapplication.view_Model.name_viewmodel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rec;
    public model mod;
    List<model> nl;
    EditText name;
    name_viewmodel view_model;
    name_adapter adapter;
    public static ProgressBar pb;
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        rec = findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setHasFixedSize(true);
        nl = new ArrayList<>();
        name = findViewById(R.id.name);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        refresh = findViewById(R.id.refresh);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(SearchActivity.this, SearchActivity.class));
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(name.getText().toString());
            }
        });

        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (name.getRight() - name.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //Perform Action

                        return true;
                    }
                }
                return false;
            }
        });


        view_model = ViewModelProviders.of(this).get(name_viewmodel.class);
        view_model.getName_model().observe(this, new Observer<model>() {
            @Override
            public void onChanged(model model) {
                if (model != null) {
                    mod = model;
                    adapter.setData(nl);
                    nl.add(mod);
                } else {

                }

            }
        });
        view_model.makeApiCall();

        adapter = new name_adapter(SearchActivity.this, nl);
        rec.setAdapter(adapter);


    }

    public void filter(String name) {
        ArrayList<model> nm = new ArrayList<>();
        for (model nm2 : nl) {
            if (nm2.getName().toLowerCase().contains(name.toLowerCase())) {
                nm.add(nm2);

            } else {

            }
        }
        if (nm.isEmpty()) {

        } else {
            adapter.filter(nm);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}