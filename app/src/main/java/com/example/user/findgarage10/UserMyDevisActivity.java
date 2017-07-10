package com.example.user.findgarage10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.user.findgarage10.db.OfferDAO;
import com.example.user.findgarage10.model.Offer;

public class UserMyDevisActivity extends AppCompatActivity {

    //region declaration
    private ListView listMyDevis;
    private OfferDAO offerDAO;
    private Button btn_back_home;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_devis);

        initView();

        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });
    }

    private void backHome() {
        Intent intent = new Intent(this, UserListNearestGarageActivity.class);
        Bundle bundle = this.getIntent().getExtras();
        intent.putExtra("user", bundle.getParcelable("user"));
        startActivity(intent);
    }

    private void initView() {
        listMyDevis = (ListView) findViewById(R.id.my_devis_lstView);
        offerDAO = new OfferDAO(this);
        btn_back_home = (Button) findViewById(R.id.btn_my_devis_back_home);
        initList();

    }

    private void initList(){
        /*offerDAO = offerDAO.openWritable();
        offerDAO.initOfferDb();*/
        offerDAO.openReadable();
        Offer[] offers = offerDAO.getNotConfirmedOffers();
        ArrayAdapter<Offer> adapter = new ArrayAdapter<Offer>(this, android.R.layout.simple_list_item_1, android.R.id.text1, offers);
        listMyDevis.setAdapter(adapter);
    }
}
