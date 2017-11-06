package com.example.user.findgarage10.util;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.user.findgarage10.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by remy on 5/11/17.
 */

public class FireBaseController {

    static FireBaseController sharedInstance = new FireBaseController();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference rootRef = database.getReference();
    private DatabaseReference userDevisRef;


    //Register User

    //SignIn User

    //Save User Devis
    public void saveUserDevis(String refUser, String refGarage, String devisDescription){
        userDevisRef = rootRef.child("usersDevis");
        userDevisRef.setValue(refUser);
        userDevisRef.setValue(refGarage);
        userDevisRef.setValue(devisDescription);
    }

    //Get User Pending Devis
    public void getUserPendingDevis(){
        userDevisRef = rootRef.child("usersDevis");
        userDevisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("Value", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });
    }

    //Get User Confirmed Devis



}
