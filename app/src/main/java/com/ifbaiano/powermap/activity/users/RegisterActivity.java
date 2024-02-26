package com.ifbaiano.powermap.activity.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ifbaiano.powermap.R;

public class RegisterActivity extends AppCompatActivity {

    Button backButonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        backButonRegister = this.<Button>findViewById(R.id.backButonRegiter);

        backButonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(RegisterActivity.this, InitialUsersActivity.class);
                startActivity(intent);
            }
        });




    }
}