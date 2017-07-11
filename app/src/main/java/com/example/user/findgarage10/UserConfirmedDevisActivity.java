package com.example.user.findgarage10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.findgarage10.db.OfferDAO;
import com.example.user.findgarage10.model.Offer;
import com.example.user.findgarage10.model.User;

public class UserConfirmedDevisActivity extends AppCompatActivity {

    private ListView lv_confirmed_offer;
    private Button btn_back_home;
    private OfferDAO offerDAO;
    private User userConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_confirmed_devis);

        initView();
        initFields();
        initListView();

        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackHome();
            }
        });
    }

    private void goBackHome() {
        Intent intent = new Intent(this, UserMyDevisActivity.class);
        intent.putExtra("user", userConnected);
        startActivity(intent);
    }

    private void initFields() {
        Bundle bundle = getIntent().getExtras();
        userConnected = bundle.getParcelable("user");
    }

    private void initListView() {
        offerDAO = new OfferDAO(this);
        offerDAO = offerDAO.openReadable();
        Offer[] confirmedDevis = offerDAO.getConfirmedOffersForUser(userConnected.getNum_user());
        ArrayAdapter<Offer> adapter = new ArrayAdapter<Offer>(this, android.R.layout.simple_list_item_1, android.R.id.text1, confirmedDevis);
        lv_confirmed_offer.setAdapter(adapter);
    }

    private void initView() {
        lv_confirmed_offer = (ListView) findViewById(R.id.lv_list_user_confirmed_offer);
        btn_back_home = (Button) findViewById(R.id.btn_confirmed_devis_back_hom);
    }
}
