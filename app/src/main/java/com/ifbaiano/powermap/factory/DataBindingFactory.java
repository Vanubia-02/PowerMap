package com.ifbaiano.powermap.factory;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.activity.carModel.ListCarModels;
import com.ifbaiano.powermap.fragment.FragmentProfileAdmin;
import com.ifbaiano.powermap.fragment.MapFragment;
import com.ifbaiano.powermap.fragment.ModelsFragment;
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
            replaceFragment(new ModelsFragment());
            if (!(activity instanceof ListCarModels)) {
                intent = new Intent(activity.getApplicationContext(), ListCarModels.class);
                activity.startActivity(intent);
            }

        } else if (itemId == R.id.icon_useres_admin) {
            replaceFragment(new UsersFragment());
            Toast.makeText(activity, "usarios em admin", Toast.LENGTH_SHORT).show();

            // if (!(activity instanceof ListCarModels)) {
                //intent = new Intent(activity.getApplicationContext(), ListCarModels.class);
                //activity.startActivity(intent);
            //}

        } else if (itemId == R.id.icon_profile_admin) {

            replaceFragment(new FragmentProfileAdmin());
            Toast.makeText(activity, "perfil do admin", Toast.LENGTH_SHORT).show();

            // if (!(activity instanceof ListCarModels)) {
            //intent = new Intent(activity.getApplicationContext(), ListCarModels.class);
            //activity.startActivity(intent);
            //}
        }
        else if (itemId == R.id.icon_car_user) {
            Toast.makeText(activity, "carros do usario", Toast.LENGTH_SHORT).show();
           //replaceFragment(new CarFragment());
            //if (!(activity instanceof ListCarModels)) {
           //     intent = new Intent(activity.getApplicationContext(), ListCarModels.class);
           //     activity.startActivity(intent);
            //}
        } else if (itemId == R.id.icon_map_user) {
            replaceFragment(new MapFragment());
            Toast.makeText(activity, "mapa do usuario", Toast.LENGTH_SHORT).show();

            // if (!(activity instanceof ListCarModels)) {
            //intent = new Intent(activity.getApplicationContext(), ListCarModels.class);
            //activity.startActivity(intent);
            //}

        } else if (itemId == R.id.icon_schedule_user) {
            replaceFragment(new ScheduleFragment());
            Toast.makeText(activity, "agenda do usario", Toast.LENGTH_SHORT).show();

            // if (!(activity instanceof ListCarModels)) {
            //intent = new Intent(activity.getApplicationContext(), ListCarModels.class);
            //activity.startActivity(intent);
            //}

        } else if (itemId == R.id.icon_profile_user) {
            replaceFragment(new ProfileFragment());
            Toast.makeText(activity, "perfil do usario", Toast.LENGTH_SHORT).show();

            // if (!(activity instanceof ListCarModels)) {
            //intent = new Intent(activity.getApplicationContext(), ListCarModels.class);
            //activity.startActivity(intent);
            //}
        }

        if (intent != null) {
            activity.startActivity(intent);
            return true;
        }

        return false;

    }

}
