package com.ifbaiano.powermap.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ifbaiano.powermap.factory.DataBindingFactory;
import com.ifbaiano.powermap.fragment.CarFragment;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    ActivityMenuBinding binding;
    DataBindingFactory bindingFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.doBinding();
    }

    private void doBinding(){
        bindingFactory = new DataBindingFactory(this, R.id.frameLayoutUser);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindingFactory.replaceFragment(new CarFragment());
        binding.bottomNavigationMenuUserTelaMenu.setOnItemSelectedListener(item -> bindingFactory.bindingMenu(item));
    }


}