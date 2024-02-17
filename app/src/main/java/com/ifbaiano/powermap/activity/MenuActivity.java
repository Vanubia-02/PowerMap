package com.ifbaiano.powermap.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ifbaiano.powermap.CarFragment;
import com.ifbaiano.powermap.MapFragment;
import com.ifbaiano.powermap.ProfileFragment;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.ScheduleFragment;
import com.ifbaiano.powermap.activity.carModel.AddCarModelActivity;
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

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.icon_car_user) {
                replaceFragment(new CarFragment());
                Intent intent = new Intent(this, AddCarModelActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.icon_map_user) {
                replaceFragment(new MapFragment());
            } else if (itemId == R.id.icon_schedule_user) {
                replaceFragment(new ScheduleFragment());
            }else if (itemId == R.id.icon_profile_user) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}