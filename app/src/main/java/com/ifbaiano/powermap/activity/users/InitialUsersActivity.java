package com.ifbaiano.powermap.activity.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ifbaiano.powermap.R;

public class InitialUsersActivity extends AppCompatActivity {

    Button createBtn, enterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_users);
        createBtn = findViewById(R.id.createBtn);
        enterBtn = findViewById(R.id.enterBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(InitialUsersActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(InitialUsersActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}