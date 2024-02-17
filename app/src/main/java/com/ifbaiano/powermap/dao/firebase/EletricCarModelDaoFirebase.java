package com.ifbaiano.powermap.dao.firebase;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifbaiano.powermap.dao.contracts.EletricCarModelDao;
import com.ifbaiano.powermap.model.EletricCarModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EletricCarModelDaoFirebase implements EletricCarModelDao {

    private final String TABLE_NAME = "electric_car_models";
    private final FirebaseDatabase firebaseDatabase;

    public EletricCarModelDaoFirebase(Context ctx) {
        FirebaseApp.initializeApp(ctx);
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
    public  ArrayList<EletricCarModel> findAll() {
        Query query = firebaseDatabase.getReference(TABLE_NAME);
        try {
            DataSnapshot dataSnapshot = Tasks.await(query.get());

            if (dataSnapshot.exists()) {
                ArrayList<EletricCarModel> carModels = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EletricCarModel carModel = snapshot.getValue(EletricCarModel.class);
                    carModels.add(carModel);
                }
                return carModels;
            }
            return null;
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }
    }


    @Override
    public  ArrayList<EletricCarModel> findByCarId(final String carId) {
        Query query = firebaseDatabase.getReference(TABLE_NAME).
                orderByChild("cars_id");

        try {
            DataSnapshot dataSnapshot = Tasks.await(query.get());

            if (dataSnapshot.exists()) {
                ArrayList<EletricCarModel> carModels = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EletricCarModel carModel = snapshot.getValue(EletricCarModel.class);
                    carModels.add(carModel);
                }
                return carModels;
            }
            return null;
        } catch (ExecutionException | InterruptedException e) {
            return null;
        }

    }
}
