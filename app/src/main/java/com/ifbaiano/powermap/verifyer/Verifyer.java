package com.ifbaiano.powermap.verifyer;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.ifbaiano.powermap.R;

public class Verifyer {
    private Context ctx;

    public Verifyer(Context ctx) {
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
