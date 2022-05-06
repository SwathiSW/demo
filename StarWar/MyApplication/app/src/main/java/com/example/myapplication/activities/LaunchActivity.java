package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchActivity extends AppCompatActivity {
    ImageView character, text;
    Animation fade_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        character = findViewById(R.id.imageView3);
        text = findViewById(R.id.imageView5);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }

        character.setAnimation(fade_in);
        text.setAnimation(fade_in);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(LaunchActivity.this, SearchActivity.class));

            }
        }, 5000);

    }
}