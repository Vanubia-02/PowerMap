package com.ifbaiano.powermap.activity.carModel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ifbaiano.powermap.factory.DataBindingFactory;
import com.ifbaiano.powermap.fragment.ModelsFragment;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.databinding.ActivityListCarModelsBinding;

public class ListCarModels extends AppCompatActivity {

    ActivityListCarModelsBinding binding;
    DataBindingFactory bindingFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car_models);
        this.doBinding();
    }

    private void doBinding(){
        bindingFactory = new DataBindingFactory(this, R.id.frameLayoutAdminlist);
        binding = ActivityListCarModelsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindingFactory.replaceFragment(new ModelsFragment());
        binding.bottomNavigationMenuAdminlist.setOnItemSelectedListener( item -> bindingFactory.bindingMenu(item));
    }


}