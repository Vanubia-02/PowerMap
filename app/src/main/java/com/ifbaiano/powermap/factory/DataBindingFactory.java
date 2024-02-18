package com.ifbaiano.powermap.factory;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.activity.carModel.AddCarModelActivity;
import com.ifbaiano.powermap.activity.carModel.ListCarModels;
import com.ifbaiano.powermap.fragment.CarFragment;
import com.ifbaiano.powermap.fragment.FragmentProfileAdmin;
import com.ifbaiano.powermap.fragment.MapFragment;
import com.ifbaiano.powermap.fragment.ProfileFragment;
import com.ifbaiano.powermap.fragment.ScheduleFragment;
import com.ifbaiano.powermap.fragment.UsersFragment;

public class DataBindingFactory {

    private final AppCompatActivity activity;
    private final int idFragment;

    public DataBindingFactory(AppCompatActivity activity, int idFragment) {
        this.activity = activity;
        this.idFragment = idFragment;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(idFragment, fragment);
        fragmentTransaction.commit();
    }

    public boolean bindingMenu(MenuItem item) {
        int itemId = item.getItemId();

        Intent intent = null;

        if (itemId == R.id.icon_car_admin) {
            replaceFragment(new CarFragment());
            intent = new Intent(activity.getApplicationContext(), AddCarModelActivity.class);

        } else if (itemId == R.id.icon_useres_admin) {
            replaceFragment(new UsersFragment());

        } else if (itemId == R.id.icon_profile_admin) {

            replaceFragment(new FragmentProfileAdmin());
        }
        else if (itemId == R.id.icon_car_user) {
            replaceFragment(new CarFragment());
            intent = new Intent(activity, ListCarModels.class);

        } else if (itemId == R.id.icon_map_user) {
            replaceFragment(new MapFragment());

        } else if (itemId == R.id.icon_schedule_user) {
            replaceFragment(new ScheduleFragment());

        } else if (itemId == R.id.icon_profile_user) {
            replaceFragment(new ProfileFragment());

        }

        if (intent != null) {
            activity.startActivity(intent);
            return true;
        }

        return false;

    }

}
