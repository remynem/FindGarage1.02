package com.example.user.findgarage10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DetailsGarageActivity extends AppCompatActivity {


    private Button btn_send_devis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_garage);

        btn_send_devis = (Button) findViewById(R.id.details_garage_btn_send_devis);

        btn_send_devis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDevis();
            }
        });
    }

    private void sendDevis() {
        Toast.makeText(this, "Devis sent", Toast.LENGTH_SHORT).show();
        finish();
        Intent goBackToListDevis = new Intent(this, UserMyDevisActivity.class);
        startActivity(goBackToListDevis);
    }
}
