package com.ifbaiano.powermap.verifier;

import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.ifbaiano.powermap.R;

import java.time.Year;
import java.util.Objects;

public class Verifier {
    private Context ctx;

    public Verifier(Context ctx) {
        this.ctx = ctx;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    protected boolean validateField(TextInputEditText field, int errorMessageResId) {
        if (TextUtils.isEmpty(field.getText())) {
            field.setError(this.getCtx().getString(errorMessageResId));
            return false;
        }
        return true;
    }

    public boolean validateYear(TextInputEditText year, int errorMessageResId) {
        if (!year.getText().toString().isEmpty()) {
            int inputYear = Integer.valueOf(year.getText().toString());
            int currentYear = Year.now().getValue();
            if (inputYear > currentYear) {
                year.setError(this.getCtx().getString(errorMessageResId));
                return false;
            }
        }
        return true;
    }


    protected boolean validateImage(boolean field, AppCompatButton imgBtn, int errorMessageResId){
        if (!field){
            imgBtn.setBackgroundResource(R.drawable.button_submit_image_red);
//            Toast.makeText(ctx, this.getCtx().getString(errorMessageResId), Toast.LENGTH_SHORT).show();
        }
        else{
            imgBtn.setBackgroundResource(R.drawable.button_submit_image);
        }

        return field;
    }

}
