package com.example.user.findgarage10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.user.findgarage10.db.OfferDAO;
import com.example.user.findgarage10.model.Garage;
import com.example.user.findgarage10.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class ReservationUpdatesActivity extends AppCompatActivity {


    private TextView label_name_client;
    private TextView label_description;
    private TextView label_date;
    private Spinner statusSpinner;
    private Button btn_update_status;
    private Offer offerSent;
    private Garage garage;
    private OfferDAO offerDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_updates);

        initView();

        initFields();

        initSpinnerTypeUser();

        btn_update_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOfferStatus();
                goBackToConfirmedOffer();
            }
        });

    }

    private void updateOfferStatus() {
        int numOffer = offerSent.getNum_offer();
        String newStatus = statusSpinner.getSelectedItem().toString();
        offerDAO = new OfferDAO(this);
        offerDAO = offerDAO.openWritable();
        offerDAO.updateReservationStatus(numOffer, newStatus);
    }

    private void goBackToConfirmedOffer() {
        Intent intent = new Intent(this, GarageConfirmedOfferActivity.class);
        intent.putExtra("garage", garage);
        startActivity(intent);
    }

    private void initFields() {
        Bundle bundle = getIntent().getExtras();
        offerSent = bundle.getParcelable("offer");
        garage = bundle.getParcelable("garage");

        label_name_client.setText(" " + offerSent.getNum_user());
        label_date.setText(offerSent.getDate_offer());
        label_description.setText(offerSent.getDescription_offer());

    }

    private void initView() {
        label_name_client = (TextView) findViewById(R.id.label_detail_name_client);
        label_description = (TextView) findViewById(R.id.label_description_offer);
        label_date = (TextView) findViewById(R.id.label_received_date);
        statusSpinner = (Spinner) findViewById(R.id.spinner_update_offer_status);
        btn_update_status = (Button) findViewById(R.id.btn_update_offer_status);
        initSpinnerTypeUser();
    }

    private void initSpinnerTypeUser() {
        statusSpinner = (Spinner) findViewById(R.id.spinner_update_offer_status);
        List<String> list_type_user = new ArrayList<>();
        list_type_user.add("Car Ready");
        list_type_user.add("Piece ordered");
        list_type_user.add("In process");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_type_user);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
    }

}
