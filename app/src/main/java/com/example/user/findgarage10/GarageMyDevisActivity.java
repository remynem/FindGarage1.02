package com.example.user.findgarage10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.findgarage10.db.OfferDAO;
import com.example.user.findgarage10.model.Garage;
import com.example.user.findgarage10.model.Offer;

public class GarageMyDevisActivity extends AppCompatActivity {

    private ListView myPeddingOffers;
    private OfferDAO offerDAO;
    private Garage garageConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_my_devis);
        initField();
        initView();
        initListView();

        myPeddingOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Offer offerSelected = (Offer) myPeddingOffers.getItemAtPosition(i);
                goToSelectedItem(offerSelected);
            }
        });
    }

    private void goToSelectedItem(Offer offerSelected) {
        Toast.makeText(this, offerSelected.toString(), Toast.LENGTH_LONG).show();
        //TODO add popup for confirm or not the offer
        //TODO if confirm, add to resa & change status, else delete
    }

    private void initField(){
        Bundle bundle = getIntent().getExtras();
        garageConnected = bundle.getParcelable("garage");

    }

    private void initView() {
        myPeddingOffers = (ListView) findViewById(R.id.lv_list_garage_pedding_offer);
    }

    private void initListView(){
        offerDAO = new OfferDAO(this);
        offerDAO = offerDAO.openReadable();
        Offer[] peddingOffers = offerDAO.getNotConfirmedOffersForGarage(garageConnected.getNum_garage());
        ArrayAdapter<Offer> adapter = new ArrayAdapter<Offer>(this, android.R.layout.simple_list_item_1, android.R.id.text1, peddingOffers);
        myPeddingOffers.setAdapter(adapter);
    }
}
