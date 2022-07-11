package com.example.gpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements dialog.DialogInterface{
    RecyclerView rec;
    adapter adapter ;
    ArrayList<String> names = new ArrayList<>();
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        rec = findViewById(R.id.rec);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        rec.setLayoutManager(manager);
        db = new Database(this);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String des = pref.getString("login", "false");

        Toast.makeText(this, ""+des, Toast.LENGTH_SHORT).show();

        if(des == "false"){

            Database db2 = new Database(this);

            db2.insert("Anil Yerram Reddy");
            db2.insert("Srini Sandaka");
            db2.insert("Siva Dega");
            db2.insert("Nasir Ahmed");
            db2.insert("Indira");
            db2.insert("Sanjana");

            SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("login", "true");
            myEdit.apply();
        }


//        names.add("Anil Yerram Reddy");
//        names.add("Srini Sandaka");
//        names.add("Siva Dega");
//        names.add("Nasir Ahmed");
//        names.add("Indira");
//        names.add("Sanjana");


        Cursor cur = db.view();

        if(cur.getCount() > 0){
            while (cur.moveToNext()){
                names.add(cur.getString(1));
            }
        }

        adapter = new adapter(this, names);
        rec.setAdapter(adapter);
        names.add(names.size(), "ADD NEW");
        adapter.notifyDataSetChanged();

    }

    @Override
    public void applyText(String name) {
        adapter.setName(name);
    }

}