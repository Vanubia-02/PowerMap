package com.ifbaiano.powermap.dao.firebase;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifbaiano.powermap.dao.contracts.DataCallback;
import com.ifbaiano.powermap.dao.contracts.HybridCarModelDao;
import com.ifbaiano.powermap.model.HybridCarModel;

import java.util.ArrayList;

public class HybridCarModelDaoFirebase implements HybridCarModelDao {

    private final String TABLE_NAME = "hybrid_car_models";
    private final FirebaseDatabase firebaseDatabase;

    public HybridCarModelDaoFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public HybridCarModel add(HybridCarModel carModel, String carId) {
        carModel.setId(firebaseDatabase.getReference(TABLE_NAME).push().getKey());
        firebaseDatabase.getReference(TABLE_NAME).child(carModel.getId()).setValue(carModel);
        return carModel;
    }

    @Override
    public HybridCarModel edit(HybridCarModel carModel) {
        firebaseDatabase.getReference(TABLE_NAME).child(carModel.getId()).setValue(carModel);
        return carModel;
    }

    @Override
    public Boolean remove(HybridCarModel carModel) {
        firebaseDatabase.getReference(TABLE_NAME).child(carModel.getId()).removeValue();
        return true;
    }

    @Override
    public HybridCarModel findOne(String id) {
        final HybridCarModel[] foundCarModel = new HybridCarModel[1];
        firebaseDatabase.getReference(TABLE_NAME).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foundCarModel[0] = dataSnapshot.getValue(HybridCarModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        return foundCarModel[0];
    }

    @Override
    public void findAll(DataCallback<HybridCarModel> callback) {
        Query query = firebaseDatabase.getReference(TABLE_NAME).orderByChild("year");;
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<HybridCarModel> allCarModels = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        HybridCarModel carModel = snapshot.getValue(HybridCarModel.class);
                        allCarModels.add(carModel);
                    }
                    callback.onDataLoaded(allCarModels);
                } else {
                    callback.onError("Erro ao carregar os modelos de carros híbridos: " + task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void findByCarId(final String carId, final DataCallback<HybridCarModel> callback) {
        Query query = firebaseDatabase.getReference(TABLE_NAME).
                orderByChild("cars_id").equalTo(carId);
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<HybridCarModel> carModels = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        HybridCarModel carModel = snapshot.getValue(HybridCarModel.class);
                        carModels.add(carModel);
                    }
                    callback.onDataLoaded(carModels);
                } else {
                    callback.onError("Erro ao buscar os modelos de carros híbridos por ID de carro: " + task.getException().getMessage());
                }
            }
        });
    }
}
