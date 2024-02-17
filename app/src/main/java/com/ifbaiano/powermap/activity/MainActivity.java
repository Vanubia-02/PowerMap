package com.ifbaiano.powermap.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ifbaiano.powermap.R;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint({"MissingInflateId","LocalSuppress"})
        ImageView img_logo = findViewById(R.id.gifImageLogo);

        Glide.with(this)
                .load(R.drawable.splash_logo).into(img_logo);

    }
}