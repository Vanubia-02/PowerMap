package com.ifbaiano.powermap.factory;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.ifbaiano.powermap.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BitmapCustomFactory {

    private final AppCompatActivity activity;
    private byte[] byteArray;
    private final ImageView imageView;
    AppCompatButton submitImgBtn;



    public BitmapCustomFactory(AppCompatActivity activity, byte[] byteArray, ImageView imageView, AppCompatButton submitImgBtn) {
        this.activity = activity;
        this.byteArray = byteArray;
        this.imageView = imageView;
        this.submitImgBtn = submitImgBtn;
    }

    public void onResult(ActivityResult result){
        {
            if (result.getResultCode() == Activity.RESULT_OK
                    && result.getData() != null) {
                Uri selectedImageUri = result.getData().getData();

                try {
                    assert selectedImageUri != null;
                    InputStream inputStream = activity.getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(inputStream);

                    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteOutput);
                    byteArray = byteOutput.toByteArray();
                    imageView.setImageBitmap(bitmap);
                    submitImgBtn.setBackgroundResource(R.drawable.button_submit_image);

                } catch (FileNotFoundException e) {
                    Toast.makeText(activity.getApplicationContext(), R.string.image_not_found, Toast.LENGTH_SHORT ).show();
                }
            }
        }
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}
