package com.ifbaiano.powermap.activity.carModel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import com.google.android.material.textfield.TextInputEditText;
import com.ifbaiano.powermap.factory.BitmapCustomFactory;
import com.ifbaiano.powermap.factory.DataBindingFactory;
import com.ifbaiano.powermap.fragment.ModelsFragment;
import com.ifbaiano.powermap.R;
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
    AppCompatButton submitImgBtn, backButton, submitFormBtn;
    CarModelVerifier verifier;
    EletricCarModelDao eletricCarModelDao;
    HybridCarModelDao hybridCarModelDao;
    StorageDao storageDao;
    int type;
    byte[] byteArray = null;
    DataBindingFactory bindingFactory;
    BitmapCustomFactory bitmapCustomFactory;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result ->  bitmapCustomFactory.onResult(result)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_model);
        type = R.id.hybrid;

        this.doBinding();
        this.syncViewObjects();
        this.makeInstances();

        submitFormBtn.setOnClickListener(v ->  submitForm());
        submitImgBtn.setOnClickListener(v -> submitImage());
        backButton.setOnClickListener(v -> backActivity());
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

    private void makeInstances(){
        eletricCarModelDao = new EletricCarModelDaoFirebase(getApplicationContext());
        hybridCarModelDao = new HybridCarModelDaoFirebase(getApplicationContext());
        storageDao = new StorageDaoFirebase();
        verifier =  new CarModelVerifier(getApplicationContext());
        bitmapCustomFactory = new BitmapCustomFactory(
                this, byteArray, imageView, submitImgBtn
        );
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
            verifyValid = verifier.verifyCarModel(name, year, energyConsumption, submitImgBtn,   bitmapCustomFactory.getByteArray() != null);
        } else if (type == R.id.hybrid) {
            verifyValid = verifier.verifyCarModel(name, year, energyConsumption, submitImgBtn,   bitmapCustomFactory.getByteArray() != null, fuelConsumption);
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
                            bitmapCustomFactory.getByteArray()
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
                            bitmapCustomFactory.getByteArray()
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

    private void doBinding(){
        bindingFactory = new DataBindingFactory(this, R.id.frameLayoutAdminlist);
        binding = ActivityAddCarModelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindingFactory.replaceFragment(new ModelsFragment());

        binding.bottomNavigationMenuAdmin.setOnItemSelectedListener(item -> bindingFactory.bindingMenu(item));

    }

}