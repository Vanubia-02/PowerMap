package com.ifbaiano.powermap.verifyer;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.ifbaiano.powermap.R;

import java.util.List;

public class CarModelVerifyer extends Verifyer{

    public CarModelVerifyer(Context ctx) {
        super(ctx);
    }

    public boolean verifyCarModel(TextInputEditText name, TextInputEditText year, TextInputEditText energyConsumption, AppCompatButton imgBtn, boolean hasImg) {
        boolean isValid = true;

        isValid = validateField(name, R.string.name_error);
        isValid &= validateField(year, R.string.year_error);
        isValid &= validateField(energyConsumption, R.string.energy_consumption_error);
        isValid &= validateImage(hasImg, imgBtn, R.string.image_error);

        return isValid;
    }

    public boolean verifyCarModel(TextInputEditText name, TextInputEditText year, TextInputEditText energyConsumption,  AppCompatButton imgBtn, Boolean hasImg, TextInputEditText fuelEnergyConsumption) {
        boolean isValid = true;
        isValid &=  this.verifyCarModel(
                name, year, energyConsumption, imgBtn, hasImg
        );
        return isValid && validateField(fuelEnergyConsumption, R.string.fuel_consumption_error);
    }

}
