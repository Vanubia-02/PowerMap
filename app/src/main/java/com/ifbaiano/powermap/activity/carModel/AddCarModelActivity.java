package com.ifbaiano.powermap.activity.carModel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.ifbaiano.powermap.CarFragment;
import com.ifbaiano.powermap.FragmentProfileAdmin;
import com.ifbaiano.powermap.ModelsFragment;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.UsersFragment;
import com.ifbaiano.powermap.dao.contracts.EletricCarModelDao;
import com.ifbaiano.powermap.dao.contracts.HybridCarModelDao;
import com.ifbaiano.powermap.dao.contracts.StorageDao;
import com.ifbaiano.powermap.dao.firebase.EletricCarModelDaoFirebase;
import com.ifbaiano.powermap.dao.firebase.HybridCarModelDaoFirebase;
import com.ifbaiano.powermap.dao.firebase.StorageDaoFirebase;
import com.ifbaiano.powermap.databinding.ActivityAddCarModelBinding;
import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;
import com.ifbaiano.powermap.service.EletricCarModelService;
import com.ifbaiano.powermap.service.HybridCarModelService;
import com.ifbaiano.powermap.verifier.CarModelVerifier;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;


public class AddCarModelActivity extends AppCompatActivity {

    ActivityAddCarModelBinding binding;
    TextInputEditText name, year, fuelConsumption, energyConsumption;
    ProgressBar progressBar;
    ImageView imageView;
    Boolean hasImg = false;
    AppCompatButton submitImgBtn, backButton, submitFormBtn;
    CarModelVerifier verifier;
    EletricCarModelDao eletricCarModelDao;
    HybridCarModelDao hybridCarModelDao;
    StorageDao storageDao;
    int type;
    ByteArrayOutputStream byteArray = null;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();

                        try {
                            assert selectedImageUri != null;
                            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                            byteArray = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                            imageView.setImageBitmap(bitmap);
                            submitImgBtn.setBackgroundResource(R.drawable.button_submit_image);

                        } catch (FileNotFoundException e) {
                            Toast.makeText(AddCarModelActivity.this, R.string.image_not_found, Toast.LENGTH_SHORT );
                        }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddCarModelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ModelsFragment());

        // chame o
        binding.bottomNavigationMenuAdmin.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.icon_car_admin) {
                replaceFragment(new CarFragment());
                Toast.makeText(this, "modelo", Toast.LENGTH_SHORT).show();

            } else if (itemId == R.id.icon_useres_admin) {
                replaceFragment(new UsersFragment());
                Toast.makeText(this, "usuarios", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ListCarModels.class);
                startActivity(intent);
            } else if (itemId == R.id.icon_profile_admin) {
                replaceFragment(new FragmentProfileAdmin());
                Toast.makeText(this, "perfil admin", Toast.LENGTH_SHORT).show();

            }
            return true;
        });


        setContentView(R.layout.activity_add_car_model);
        type = R.id.hybrid;

        eletricCarModelDao = new EletricCarModelDaoFirebase(getApplicationContext());
        hybridCarModelDao = new HybridCarModelDaoFirebase(getApplicationContext());
        storageDao = new StorageDaoFirebase();

        this.verifier =  new CarModelVerifier(getApplicationContext());

        this.syncViewObjects();

        submitFormBtn.setOnClickListener(v ->  submitForm());
        submitImgBtn.setOnClickListener(v -> submitImage());
        backButton.setOnClickListener(v -> backActivity());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutAdmin, fragment);
        fragmentTransaction.commit();
    }

    public void onRadioButtonClicked(@NonNull View view) {
        boolean checked = ((RadioButton) view).isChecked();
        this.type = view.getId();

        if(view.getId() == R.id.eletric && checked){
            fuelConsumption.setVisibility(View.GONE);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) submitFormBtn.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_TOP, R.id.energyConsumption);
            submitFormBtn.setLayoutParams(params);
        }
        else if(view.getId() == R.id.hybrid && checked) {
            fuelConsumption.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) submitFormBtn.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_TOP, R.id.fuelConsumption);
            submitFormBtn.setLayoutParams(params);
        }

    }

    private void syncViewObjects(){
        name = findViewById(R.id.name);
        year = findViewById(R.id.year);
        energyConsumption = findViewById(R.id.energyConsumption);
        fuelConsumption = findViewById(R.id.fuelConsumption);
        submitImgBtn = findViewById(R.id.submitImage);
        backButton = findViewById(R.id.backButon);
        submitFormBtn = findViewById(R.id.submitForm);
        imageView = findViewById(R.id.imageView);
        progressBar  = findViewById(R.id.progressBar);
    }

    private void submitImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launcher.launch(intent);
    }

    private void backActivity(){
        startActivity(new Intent(AddCarModelActivity.this, ListCarModels.class));
    }
    private void submitForm() {
        progressBar.setVisibility(View.VISIBLE);
        submitFormBtn.setVisibility(View.GONE);

        boolean verifyValid = false;

        if (type == R.id.eletric) {
            verifyValid = verifier.verifyCarModel(name, year, energyConsumption, submitImgBtn, byteArray != null);
        } else if (type == R.id.hybrid) {
            verifyValid = verifier.verifyCarModel(name, year, energyConsumption, submitImgBtn, byteArray != null, fuelConsumption);
        }

        if (verifyValid) {
            new Thread(() -> {
                final boolean[] success = {false};
                if (type == R.id.eletric) {
                    success[0] = new EletricCarModelService(eletricCarModelDao, storageDao).add(
                            new EletricCarModel(
                                    null,
                                    Objects.requireNonNull(name.getText()).toString(),
                                    Integer.valueOf(Objects.requireNonNull(year.getText()).toString()),
                                    "",
                                    Float.parseFloat(Objects.requireNonNull(energyConsumption.getText()).toString())
                            ),
                            null,
                            byteArray.toByteArray()
                    );
                } else if (type == R.id.hybrid) {
                    success[0] = new HybridCarModelService(hybridCarModelDao, storageDao).add(
                            new HybridCarModel(
                                    null,
                                    Objects.requireNonNull(name.getText()).toString(),
                                    Integer.valueOf(Objects.requireNonNull(year.getText()).toString()),
                                    "",
                                    Float.parseFloat(Objects.requireNonNull(energyConsumption.getText()).toString()),
                                    Float.parseFloat(Objects.requireNonNull(fuelConsumption.getText()).toString())
                            ),
                            null,
                            byteArray.toByteArray()
                    );
                }

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    submitFormBtn.setVisibility(View.VISIBLE);

                    if (success[0]) {
                        backActivity();
                    } else {
                        Toast.makeText(AddCarModelActivity.this, R.string.error_data, Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        } else {
            progressBar.setVisibility(View.GONE);
            submitFormBtn.setVisibility(View.VISIBLE);
        }
    }


}