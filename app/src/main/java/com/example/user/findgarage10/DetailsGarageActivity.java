package com.example.user.findgarage10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.findgarage10.db.OfferDAO;
import com.example.user.findgarage10.model.Garage;
import com.example.user.findgarage10.model.Offer;
import com.example.user.findgarage10.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

public class DetailsGarageActivity extends AppCompatActivity {


    private Button btn_send_devis;
    private EditText et_description_devis;
    private User userConnected;
    private Garage garageSelected;
    private OfferDAO offerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_garage);

        btn_send_devis = (Button) findViewById(R.id.details_garage_btn_send_devis);
        et_description_devis = (EditText) findViewById(R.id.form_devis_description);

        btn_send_devis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDevis();
            }
        });
    }

    private void sendDevis() {
        initTarget();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Offer offer = new Offer(1,1,date,et_description_devis.getText().toString());
        offerDAO = new OfferDAO(this);
        offerDAO = offerDAO.openWritable();
        offerDAO.insertOffer(offer);
        offerDAO.close();

        Toast.makeText(this, "Devis sent", Toast.LENGTH_SHORT).show();
        finish();
        Intent goBackToListDevis = new Intent(this, UserMyDevisActivity.class);

        goBackToListDevis.putExtra("user", userConnected);
        goBackToListDevis.putExtra("garage", userConnected);

        startActivity(goBackToListDevis);
    }

    private void initTarget() {
        Bundle bundle = getIntent().getExtras();
        userConnected = bundle.getParcelable("user");
        garageSelected = bundle.getParcelable("garage");
    }

}
