package com.example.user.findgarage10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.findgarage10.db.OfferDAO;
import com.example.user.findgarage10.model.Garage;
import com.example.user.findgarage10.model.Offer;
import com.example.user.findgarage10.model.User;
import com.example.user.findgarage10.util.FireBaseController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsGarageActivity extends AppCompatActivity {


    private Button btn_send_devis;
    private Button btn_back_home;
    private TextView label_details_garage;
    private TextView label_tel_garage;
    private EditText et_description_devis;
    private User userConnected;
    private Garage garageSelected;
    private FirebaseDatabase database;
    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_garage);

        database = FirebaseDatabase.getInstance();
        rootRef = database.getReference();

        initView();

        btn_send_devis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDevis();
            }
        });
        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });
    }

    private void initView() {
        btn_send_devis = (Button) findViewById(R.id.details_garage_btn_send_devis);
        btn_back_home = (Button) findViewById(R.id.details_garage_back_home);
        et_description_devis = (EditText) findViewById(R.id.form_devis_description);
        label_details_garage = (TextView) findViewById(R.id.details_garage_label_details_garage);
        label_tel_garage = (TextView) findViewById(R.id.details_garage_label_num_garage);
    }

     public void sendDevis() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String devisDescription = et_description_devis.getText().toString();

         rootRef = database.getReference("userDevis").push();

         rootRef.child("refUser").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
         rootRef.child("refGarage").setValue("ixelles");
         rootRef.child("devisDescription").setValue(devisDescription);
         rootRef.child("status").setValue("pending");
         rootRef.child("date").setValue(date);

        startActivity(new Intent(DetailsGarageActivity.this, UserMyDevisActivity.class));
    }

    private void backHome() {
        Intent goBackHome = new Intent(this, UserListNearestGarageActivity.class);
        startActivity(goBackHome);
    }

    private void initTarget() {
        Bundle bundle = getIntent().getExtras();
        userConnected = bundle.getParcelable("user");
        garageSelected = bundle.getParcelable("garage");
    }

}
