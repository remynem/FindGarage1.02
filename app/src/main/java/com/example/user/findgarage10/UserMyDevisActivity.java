package com.example.user.findgarage10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.findgarage10.db.OfferDAO;
import com.example.user.findgarage10.db.UserDAO;
import com.example.user.findgarage10.model.Offer;
import com.example.user.findgarage10.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserMyDevisActivity extends AppCompatActivity {

    //region declaration
    private ListView listMyDevis;
    private User userConnected;
    private OfferDAO offerDAO;
    private Button btn_back_home;
    private Button btn_show_confirmed_devis;
    private FirebaseDatabase database;
    private DatabaseReference rootRef;
    private List<Offer> offers;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_devis);

        initView();

        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference();

        listMyDevis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //AlertDialog dialog = create();
            }
        });
        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });
        btn_show_confirmed_devis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToConfirmedDevis();
            }
        });
    }

    private void goToConfirmedDevis() {
        Intent intent = new Intent(this, UserConfirmedDevisActivity.class);
        startActivity(intent);
    }

    private AlertDialog.Builder create() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Erase hard drive")
                .setMessage("Are you sure?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int numOffer) {
                        finish();
                        Log.i("Dialog", null);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
        return builder;
    }

    private void backHome() {
        Intent intent = new Intent(this, UserListNearestGarageActivity.class);
        startActivity(intent);
    }

    private void initView() {
        listMyDevis = (ListView) findViewById(R.id.my_devis_lstView);
        offerDAO = new OfferDAO(this);
        btn_back_home = (Button) findViewById(R.id.btn_my_devis_back_home);
        btn_show_confirmed_devis = (Button) findViewById(R.id.btn_my_devis_show_confirmed);
        initList();

    }

    private void initList() {

        offers = new ArrayList();
        rootRef = FirebaseDatabase.getInstance().getReference("userDevis");
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String user = snapshot.child("refUser").getValue(String.class);
                    if(user.compareTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()) == 0){
                        String refGarage = snapshot.child("refGarage").getValue(String.class);
                        String date = snapshot.child("date").getValue(String.class);
                        String devisDescription = snapshot.child("devisDescription").getValue(String.class);

                        Offer devis = new Offer(refGarage, date, devisDescription);
                        offers.add(devis);
                    }
                }
                ArrayAdapter<Offer> adapter = new ArrayAdapter<>(UserMyDevisActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, offers);
                listMyDevis.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("val", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return super.onCreateDialog(id);
    }
}
