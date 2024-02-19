package com.ifbaiano.powermap.activity.carModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ifbaiano.powermap.adapter.ModelCarAdapter;
import com.ifbaiano.powermap.dao.firebase.EletricCarModelDaoFirebase;
import com.ifbaiano.powermap.dao.firebase.HybridCarModelDaoFirebase;
import com.ifbaiano.powermap.factory.DataBindingFactory;
import com.ifbaiano.powermap.fragment.ModelsFragment;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.databinding.ActivityListCarModelsBinding;
import com.ifbaiano.powermap.model.CarModel;
import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;
import com.ifbaiano.powermap.service.EletricCarModelService;
import com.ifbaiano.powermap.service.HybridCarModelService;

import java.util.ArrayList;

public class ListCarModels extends AppCompatActivity {

    ActivityListCarModelsBinding binding;
    AppCompatButton addBtn;
    ProgressBar progressBar;
    DataBindingFactory bindingFactory;
    RecyclerView recyclerView;
    ArrayList<CarModel> carModels;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_car_models);
        this.doBinding();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressBar);
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AddCarModelActivity.class));
        });
        listModels();
    }

    private void doBinding(){
        bindingFactory = new DataBindingFactory(this, R.id.frameLayoutAdminlist);
        binding = ActivityListCarModelsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindingFactory.replaceFragment(new ModelsFragment());
        binding.bottomNavigationMenuAdminlist.setOnItemSelectedListener( item -> bindingFactory.bindingMenu(item));
    }

    private void listModels(){

        new Thread(() -> {
            carModels = new ArrayList<>();
            ArrayList<EletricCarModel> eletricCarModels =
                    new EletricCarModelService(
                            new EletricCarModelDaoFirebase(getApplicationContext()),
                            null).listAll();

            ArrayList<HybridCarModel> hybridModels = new HybridCarModelService(
                    new HybridCarModelDaoFirebase(getApplicationContext()),
                    null).listAll();

            if(eletricCarModels != null){
                carModels.addAll(eletricCarModels);
            }

            if(hybridModels != null){
                carModels.addAll(hybridModels);
            }

            runOnUiThread(() -> {
                adapter = new ModelCarAdapter(carModels, getApplicationContext());
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
                recyclerView.setVisibility(View.VISIBLE);
            });

        }).start();
    }


}