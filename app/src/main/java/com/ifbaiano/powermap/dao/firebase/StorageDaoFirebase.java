package com.ifbaiano.powermap.dao.firebase;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ifbaiano.powermap.dao.contracts.StorageDao;

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

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }
}
