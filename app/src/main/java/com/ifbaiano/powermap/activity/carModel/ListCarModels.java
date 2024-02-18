package com.ifbaiano.powermap.activity.carModel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ifbaiano.powermap.fragment.CarFragment;
import com.ifbaiano.powermap.fragment.FragmentProfileAdmin;
import com.ifbaiano.powermap.fragment.ModelsFragment;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.fragment.UsersFragment;
import com.ifbaiano.powermap.databinding.ActivityListCarModelsBinding;

public class ListCarModels extends AppCompatActivity {

    ActivityListCarModelsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_car_models);

        binding = ActivityListCarModelsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ModelsFragment());

        // chame o
        binding.bottomNavigationMenuAdminlist.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.icon_car_admin) {
                replaceFragment(new CarFragment());
                Intent intent = new Intent(this, AddCarModelActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.icon_useres_admin) {
                Toast.makeText(this, "usuarios", Toast.LENGTH_SHORT).show();
                replaceFragment(new UsersFragment());
            } else if (itemId == R.id.icon_profile_admin) {
                replaceFragment(new FragmentProfileAdmin());
                Toast.makeText(this, "perfil admin", Toast.LENGTH_SHORT).show();

            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutAdminlist, fragment);
        fragmentTransaction.commit();
    }
}