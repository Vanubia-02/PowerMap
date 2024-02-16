package com.ifbaiano.powermap.dao.firebase;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class FirebaseStorageDao {
    private StorageReference storageReference;

    private String CHILD = "eletric_models/";
    public FirebaseStorageDao() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        this.storageReference = storage.getReference();
    }

    public String add(byte[] imageData){
        String path = this.CHILD + UUID.randomUUID().toString() + ".png";
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

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }
}
