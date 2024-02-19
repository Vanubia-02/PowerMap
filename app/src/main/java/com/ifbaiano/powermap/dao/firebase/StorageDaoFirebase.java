package com.ifbaiano.powermap.dao.firebase;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ifbaiano.powermap.dao.contracts.StorageDao;
import com.squareup.picasso.Picasso;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class StorageDaoFirebase implements StorageDao {
    private StorageReference storageReference;
    public StorageDaoFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        this.storageReference = storage.getReference();
    }

    public String add(byte[] imageData, String child){
        String path = child + "/" + UUID.randomUUID().toString() + ".png";
        StorageReference imageRef = this.storageReference.child(path);

        UploadTask uploadTask = imageRef.putBytes(imageData);

        try {
            Tasks.await(uploadTask);

            if (uploadTask.isSuccessful()) {
                return path;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void transformInBitmap(String path, ImageView imageView, ProgressBar progressBar){
        StorageReference storageRef = this.storageReference.child(path);
        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Log.e("FirebaseImage", uri.toString());
            progressBar.setVisibility(View.GONE);
            Picasso.get().load(uri).into(imageView);
        }).addOnFailureListener(exception -> {
            Log.e("FirebaseImage", "Error downloading image URL: " + exception.getMessage());
        });
    }

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }
}
