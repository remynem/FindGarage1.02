package com.example.user.findgarage10;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.SupportMapFragment;

public class UserListNearestGarageActivity extends AppCompatActivity {
    private Button btnGotoGaragePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_nearest_garage);

        btnGotoGaragePage = (Button) findViewById(R.id.nearest_garage_btn_goto);
        btnGotoGaragePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoGaragePage();
            }
        });

        /*FragmentManager fragment = getFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragment.findFragmentById(R.id.fragment_map);*/

    }

    private void gotoGaragePage() {
        Intent intent = new Intent(this, DetailsGarageActivity.class);
        startActivity(intent);
    }
}
