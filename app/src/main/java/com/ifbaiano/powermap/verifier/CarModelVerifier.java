package com.ifbaiano.powermap.verifier;

import android.content.Context;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.ifbaiano.powermap.R;

import java.time.Year;
import java.util.Objects;


public class CarModelVerifier extends Verifier {

    public CarModelVerifier(Context ctx) {
        super(ctx);
    }

    public boolean verifyCarModel(TextInputEditText name, TextInputEditText year, TextInputEditText energyConsumption, AppCompatButton imgBtn, boolean hasImg) {
        boolean isValid = true;

        isValid = validateField(name, R.string.name_error);
        isValid &= validateField(year, R.string.year_error);
        isValid &= validateYear(year, R.string.wrong_year_error);
        isValid &= validateField(energyConsumption, R.string.energy_consumption_error);
        isValid &= validateImage(hasImg, imgBtn, R.string.image_error);

        return isValid;
    }

    public boolean verifyCarModel(TextInputEditText name, TextInputEditText year, TextInputEditText energyConsumption,  AppCompatButton imgBtn, Boolean hasImg, TextInputEditText fuelEnergyConsumption) {
        boolean isValid = true;
        isValid = this.verifyCarModel(
                name, year, energyConsumption, imgBtn, hasImg
        );
        isValid &= validateField(fuelEnergyConsumption, R.string.fuel_consumption_error);
        return isValid;
    }



}
