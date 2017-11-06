package com.example.user.findgarage10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.findgarage10.model.Offer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserConfirmedDevisActivity extends AppCompatActivity {

    private ListView lv_confirmed_offer;
    private Button btn_back_home;
    private FirebaseDatabase database;
    private DatabaseReference rootRef;
    private List<Offer> offers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_confirmed_devis);


        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference();

        initView();
        initListView();

        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackHome();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initListView();
    }

    private void initView() {
        lv_confirmed_offer = (ListView) findViewById(R.id.lv_list_user_confirmed_offer);
        btn_back_home = (Button) findViewById(R.id.btn_confirmed_devis_back_hom);
    }

    private void initListView() {
        offers = new ArrayList();
        rootRef = FirebaseDatabase.getInstance().getReference("userDevis");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String user = snapshot.child("refUser").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);
                    if(user.compareTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()) == 0 && status != "pending"){
                        String refGarage = snapshot.child("refGarage").getValue(String.class);
                        String date = snapshot.child("date").getValue(String.class);
                        String devisDescription = snapshot.child("devisDescription").getValue(String.class);

                        Offer devis = new Offer(refGarage, date, devisDescription);
                        devis.setConfirmed_offer(status);
                        offers.add(devis);
                    }
                }
                ArrayAdapter<Offer> adapter = new ArrayAdapter<>(UserConfirmedDevisActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, offers);
                lv_confirmed_offer.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("val", "Failed to read value.", error.toException());
            }
        });
    }

    private void goBackHome() {
        Intent intent = new Intent(this, UserListNearestGarageActivity.class);
        startActivity(intent);
    }
}
