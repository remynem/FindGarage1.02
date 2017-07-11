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
import com.example.user.findgarage10.model.Offer;
import com.example.user.findgarage10.model.User;

public class UserMyDevisActivity extends AppCompatActivity {

    //region declaration
    private ListView listMyDevis;
    private User userConnected;
    private OfferDAO offerDAO;
    private Button btn_back_home;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_devis);

        initView();
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
    }

    private AlertDialog.Builder create(){
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
        offerDAO = new OfferDAO(this);
        /*offerDAO = offerDAO.openWritable();
        offerDAO.initOfferDb();*/
        Bundle bundle = this.getIntent().getExtras();
        userConnected = bundle.getParcelable("user");
        offerDAO.openReadable();
        Offer[] offers = offerDAO.getNotConfirmedOffersForUser(userConnected.getNum_user());
        ArrayAdapter<Offer> adapter = new ArrayAdapter<Offer>(this, android.R.layout.simple_list_item_1, android.R.id.text1, offers);
        listMyDevis.setAdapter(adapter);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return super.onCreateDialog(id);
    }
}
