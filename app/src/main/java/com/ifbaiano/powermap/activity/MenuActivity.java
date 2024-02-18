package com.ifbaiano.powermap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ifbaiano.powermap.fragment.CarFragment;
import com.ifbaiano.powermap.fragment.MapFragment;
import com.ifbaiano.powermap.fragment.ProfileFragment;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.fragment.ScheduleFragment;
import com.ifbaiano.powermap.activity.carModel.ListCarModels;
import com.ifbaiano.powermap.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_menu);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new CarFragment());

        // chame o
        binding.bottomNavigationMenuUserTelaMenu.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.icon_car_user) {
                replaceFragment(new CarFragment());
                Toast.makeText(this, "carro", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ListCarModels.class);
                startActivity(intent);
            } else if (itemId == R.id.icon_map_user) {
                replaceFragment(new MapFragment());
                Toast.makeText(this, "mapa", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.icon_schedule_user) {
                replaceFragment(new ScheduleFragment());
                Toast.makeText(this, "agenda", Toast.LENGTH_SHORT).show();

            } else if (itemId == R.id.icon_profile_user) {
                replaceFragment(new ProfileFragment());
                Toast.makeText(this, "perfil user", Toast.LENGTH_SHORT).show();

            }
            return true;
        });

    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutUser, fragment);
        fragmentTransaction.commit();
    }


}