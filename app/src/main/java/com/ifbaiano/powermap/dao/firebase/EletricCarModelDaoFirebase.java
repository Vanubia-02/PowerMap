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
import com.ifbaiano.powermap.dao.contracts.EletricCarModelDao;
import com.ifbaiano.powermap.model.EletricCarModel;
import com.ifbaiano.powermap.model.User;

import java.util.ArrayList;

public class EletricCarModelDaoFirebase implements EletricCarModelDao {

    private final String TABLE_NAME = "electric_car_models";
    private final FirebaseDatabase firebaseDatabase;

    public EletricCarModelDaoFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public EletricCarModel add(EletricCarModel carModel, String carId) {
        carModel.setId(firebaseDatabase.getReference(TABLE_NAME).push().getKey());
        firebaseDatabase.getReference(TABLE_NAME).child(carModel.getId()).setValue(carModel);
        return carModel;
    }

    @Override
    public EletricCarModel edit(EletricCarModel carModel) {
        firebaseDatabase.getReference(TABLE_NAME).child(carModel.getId()).setValue(carModel);
        return carModel;
    }

    @Override
    public Boolean remove(EletricCarModel carModel) {
        firebaseDatabase.getReference(TABLE_NAME).child(carModel.getId()).removeValue();
        return true;
    }

    @Override
    public EletricCarModel findOne(String id) {
        final EletricCarModel[] foundCarModel = new EletricCarModel[1];
        firebaseDatabase.getReference(TABLE_NAME).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foundCarModel[0] = dataSnapshot.getValue(EletricCarModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        return foundCarModel[0];
    }

    @Override
    public void findAll(DataCallback< EletricCarModel> callback) {
        Query query = firebaseDatabase.getReference(TABLE_NAME);
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<EletricCarModel> allCarModels = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        EletricCarModel carModel = snapshot.getValue(EletricCarModel.class);
                        allCarModels.add(carModel);
                    }
                    callback.onDataLoaded(allCarModels);
                } else {
                    callback.onError("Erro ao carregar os modelos de carros elétricos: " + task.getException().getMessage());
                }
            }
        });
    }


    @Override
    public void findByCarId(final String carId, final DataCallback<EletricCarModel> callback) {
        Query query = firebaseDatabase.getReference(TABLE_NAME).
                orderByChild("cars_id");
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<EletricCarModel> carModels = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        EletricCarModel carModel = snapshot.getValue(EletricCarModel.class);
                        carModels.add(carModel);
                    }
                    callback.onDataLoaded(carModels);
                } else {
                    callback.onError("Erro ao buscar os modelos de carros elétricos por ID de carro: " + task.getException().getMessage());
                }
            }
        });
    }
}
