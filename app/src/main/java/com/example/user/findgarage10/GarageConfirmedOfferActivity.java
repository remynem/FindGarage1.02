package com.example.user.findgarage10;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.findgarage10.db.OfferDAO;
import com.example.user.findgarage10.model.Garage;
import com.example.user.findgarage10.model.Offer;

public class GarageConfirmedOfferActivity extends AppCompatActivity {

    private ListView lv_confirmed_offer;
    private Button btn_back_home;
    private OfferDAO offerDAO;
    private Garage garage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_confirmed_offer);

        initView();
        initFields();
        initListView();

        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lv_confirmed_offer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Offer offerSelected = (Offer) lv_confirmed_offer.getItemAtPosition(i);
                goToUpdateStatus(offerSelected);
            }
        });
    }

    private void goToUpdateStatus(Offer offerSelected) {
        Intent intent = new Intent(this, ReservationUpdatesActivity.class);
        intent.putExtra("offer", offerSelected);
        startActivity(intent);
    }

    private void initView() {
        lv_confirmed_offer = (ListView) findViewById(R.id.lv_list_garage_confirmed_offer);
        btn_back_home = (Button) findViewById(R.id.btn_confirmed_devis_back_hom);
    }

    private void initFields(){
        Bundle bundle = getIntent().getExtras();
        garage = bundle.getParcelable("garage");
    }

    private void initListView(){
        offerDAO = new OfferDAO(this);
        offerDAO = offerDAO.openReadable();
        Offer[] confirmedOffers = offerDAO.getConfirmedOffersForGarage(garage.getNum_garage());
        ArrayAdapter<Offer> adapter = new ArrayAdapter<Offer>(this, android.R.layout.simple_list_item_1, android.R.id.text1, confirmedOffers);
        lv_confirmed_offer.setAdapter(adapter);
    }
}
