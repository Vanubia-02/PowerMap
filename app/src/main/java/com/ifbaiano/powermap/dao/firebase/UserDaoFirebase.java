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
import com.ifbaiano.powermap.dao.contracts.UserDao;
import com.ifbaiano.powermap.factory.UserFactory;
import com.ifbaiano.powermap.model.User;
import java.util.ArrayList;

public class UserDaoFirebase implements UserDao {

    private final String TABLE_NAME = "users";
    private final FirebaseDatabase firebaseDatabase;

    public UserDaoFirebase(Context ctx) {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public User add(User user) {
        user.setId(firebaseDatabase.getReference(TABLE_NAME).push().getKey());
        firebaseDatabase.getReference(TABLE_NAME).child(user.getId()).setValue(user);
        return user;
    }

    @Override
    public User edit(User user) {
        firebaseDatabase.getReference(TABLE_NAME).child(user.getId()).setValue(user);
        return user;
    }

    @Override
    public Boolean remove(User user) {
        firebaseDatabase.getReference(TABLE_NAME).child(user.getId()).removeValue();
        return true;
    }

    @Override
    public User findOne(final String id) {
        final User[] foundUser = new User[1];
        firebaseDatabase.getReference(TABLE_NAME).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foundUser[0] = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
        return foundUser[0];
    }

    @Override
    public void findAll(DataCallback<User> callback) {
        Query query = firebaseDatabase.getReference(TABLE_NAME);
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<User> allUsers = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        User user = snapshot.getValue(User.class);
                        allUsers.add(user);
                    }
                    callback.onDataLoaded(allUsers);
                } else {
                    callback.onError("Erro ao carregar os usuários: " + task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void findAllClients(DataCallback<User> callback) {
        Query query = firebaseDatabase.getReference(TABLE_NAME).orderByChild("isAdmin").equalTo(false);
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<User> clients = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        User user = snapshot.getValue(User.class);
                        clients.add(user);
                    }
                    callback.onDataLoaded(clients);
                } else {
                    callback.onError("Erro ao carregar os clientes: " + task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void findAllAdmins(DataCallback<User> callback) {
        Query query = firebaseDatabase.getReference(TABLE_NAME).orderByChild("isAdmin").equalTo(true);
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<User> admins = new ArrayList<>();
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        User user = snapshot.getValue(User.class);
                        admins.add(user);
                    }
                    callback.onDataLoaded(admins);
                } else {
                    callback.onError("Erro ao carregar os administradores: " + task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public Boolean changePassword(User user, String password) {
        user.setPassword(password);
        firebaseDatabase.getReference(TABLE_NAME).child(user.getId()).child("password").setValue(password);
        return true;
    }
}
