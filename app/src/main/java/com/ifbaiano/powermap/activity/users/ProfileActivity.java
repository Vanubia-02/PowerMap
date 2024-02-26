package com.ifbaiano.powermap.activity.users;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.databinding.ActivityProfileBinding;
import com.ifbaiano.powermap.factory.DataBindingFactory;
import com.ifbaiano.powermap.fragment.CarFragment;






public class ProfileActivity extends AppCompatActivity {
    DataBindingFactory bindingFactory;
    ActivityProfileBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.doBinding();
    }

    private void doBinding(){
        bindingFactory = new DataBindingFactory(this, R.id.frameLayoutUserProfile);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindingFactory.replaceFragment(new CarFragment());
        binding.bottomNavigationMenuProfile.setOnItemSelectedListener(item -> bindingFactory.bindingMenu(item));

    }
}