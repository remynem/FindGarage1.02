package com.example.user.findgarage10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private Button goToRegisterUser;
    private Button goToRegisterGarage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        goToRegisterUser = (Button) findViewById(R.id.btn_goToRegisterUser);
        goToRegisterGarage = (Button) findViewById(R.id.btn_goToRegisterGarage);

        goToRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterUser();
            }
        });

        goToRegisterGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterGarage();
            }
        });
    }

    private void goToRegisterUser() {
        goToIntent(RegisterUserActivity.class);
    }

    private void goToRegisterGarage() {
        goToIntent(RegisterGarageActivity.class);
    }

    private void goToIntent(Class target){
        Intent intent = new Intent(this, target);
        startActivity(intent);
    }
}
