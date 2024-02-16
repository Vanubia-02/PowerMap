package com.ifbaiano.powermap.activity.carModel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ifbaiano.powermap.R;
import com.ifbaiano.powermap.dao.contracts.EletricCarModelDao;
import com.ifbaiano.powermap.dao.contracts.HybridCarModelDao;
import com.ifbaiano.powermap.dao.firebase.EletricCarModelDaoFirebase;
import com.ifbaiano.powermap.dao.firebase.HybridCarModelDaoFirebase;
import com.ifbaiano.powermap.dao.firebase.UserDaoFirebase;
import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.HybridCarModel;
import com.ifbaiano.powermap.model.User;
import com.ifbaiano.powermap.service.EletricCarModelService;
import com.ifbaiano.powermap.service.HybridCarModelService;
import com.ifbaiano.powermap.service.ImageService;
import com.ifbaiano.powermap.verifyer.CarModelVerifyer;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddCarModelActivity extends AppCompatActivity {
    TextInputEditText name, year, fuelConsumption, energyConsumption;
    ProgressBar progressBar;
    ImageView imageView;
    Boolean hasImg = false;
    AppCompatButton submitImgBtn, backButton, submitFormBtn;
    CarModelVerifyer verifyer;
    EletricCarModelDao eletricCarModelDao;
    HybridCarModelDao hybridCarModelDao;
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
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                            imageView.setImageBitmap(bitmap);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_model);
        type = R.id.hybrid;

        eletricCarModelDao = new EletricCarModelDaoFirebase(getApplicationContext());
        hybridCarModelDao = new HybridCarModelDaoFirebase(getApplicationContext());
        this.verifyer =  new CarModelVerifyer(getApplicationContext());

        this.syncViewObjects();

        submitFormBtn.setOnClickListener(v ->  submitForm());


        submitImgBtn.setOnClickListener(v -> submitImage());

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
    private void submitForm(){

        runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE);
            submitFormBtn.setVisibility(View.GONE);
        });

        new Thread(() -> {
            final boolean[] success = {false};

            if( type == R.id.eletric) {
                if(verifyer.verifyCarModel(
                        name, year, energyConsumption, submitImgBtn, byteArray != null
                )){
                    success[0] = new EletricCarModelService(eletricCarModelDao).add(new EletricCarModel(
                            null,
                            Objects.requireNonNull(name.getText()).toString(),
                            Integer.valueOf(Objects.requireNonNull(year.getText()).toString()),
                            "",
                            Float.parseFloat(Objects.requireNonNull(energyConsumption.getText()).toString())
                    ), null, byteArray.toByteArray());

                }
            }
            else if(type == R.id.hybrid){
                if(verifyer.verifyCarModel(
                        name, year, energyConsumption, submitImgBtn, byteArray != null, fuelConsumption
                )){
                    success[0] = new HybridCarModelService(hybridCarModelDao).add(new HybridCarModel(
                            null,
                            Objects.requireNonNull(name.getText()).toString(),
                            Integer.valueOf(year.getText().toString()),
                            "",
                            Float.parseFloat(Objects.requireNonNull(energyConsumption.getText()).toString()),
                            Float.parseFloat(Objects.requireNonNull(fuelConsumption.getText()).toString())
                    ), null, byteArray.toByteArray());
                }
            }
            runOnUiThread(() -> {
                if(success[0]){
                    startActivity(new Intent(AddCarModelActivity.this, ListCarModels.class));
                } else {
                    Toast.makeText(AddCarModelActivity.this,  R.string.error_data, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                submitFormBtn.setVisibility(View.VISIBLE);

            });
        }).start();
    }

}