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
import com.ifbaiano.powermap.dao.contracts.HybridCarModelDao;
import com.ifbaiano.powermap.model.HybridCarModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HybridCarModelDaoFirebase implements HybridCarModelDao {

    private final String TABLE_NAME = "hybrid_car_models";
    private final FirebaseDatabase firebaseDatabase;

    public HybridCarModelDaoFirebase(Context ctx) {
        FirebaseApp.initializeApp(ctx);
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
    public ArrayList<HybridCarModel> findAll() {
        Query query = firebaseDatabase.getReference(TABLE_NAME);
        try {
            DataSnapshot dataSnapshot = Tasks.await(query.get());

            if (dataSnapshot.exists()) {
                ArrayList<HybridCarModel> carModels = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HybridCarModel carModel = snapshot.getValue(HybridCarModel.class);
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
    public ArrayList<HybridCarModel> findByCarId(final String carId) {
        Query query = firebaseDatabase.getReference(TABLE_NAME).
                orderByChild("cars_id").equalTo(carId);

        try {
            DataSnapshot dataSnapshot = Tasks.await(query.get());

            if (dataSnapshot.exists()) {
                ArrayList<HybridCarModel> carModels = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HybridCarModel carModel = snapshot.getValue(HybridCarModel.class);
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
