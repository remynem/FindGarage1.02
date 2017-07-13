package com.example.user.findgarage10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.findgarage10.db.GarageDAO;
import com.example.user.findgarage10.model.Garage;
import com.example.user.findgarage10.util.HashMyString;

public class RegisterGarageActivity extends AppCompatActivity {

    private EditText et_nameGarage;
    private EditText et_domainGarage;
    private EditText et_pwdGarage;
    private EditText et_emailGarage;
    private EditText et_telGarage;
    private EditText et_adressGarage;

    private Button registerGarage;
    private Button cancel;

    private GarageDAO garageDAO;
    private HashMyString hashMyString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_garage);

        initView();

        registerGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
                goBackHome();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackHome();
            }
        });
    }

    private void goBackHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToRegister() {
        String nameGarage = getStringFromEditText(et_nameGarage);
        String domainGarage = getStringFromEditText(et_domainGarage);
        String emailGarage = getStringFromEditText(et_emailGarage);
        String pwdGarage = getStringFromEditText(et_pwdGarage);
        String telGarage = getStringFromEditText(et_telGarage);
        String adressGarage = getStringFromEditText(et_adressGarage);

        hashMyString = new HashMyString(pwdGarage);
        Garage newGarage = new Garage(nameGarage, domainGarage, emailGarage, hashMyString.getMyHash(), telGarage, adressGarage);

        garageDAO = new GarageDAO(this);
        garageDAO = garageDAO.openWritable();
        garageDAO.insertGarage(newGarage);

        Toast.makeText(this, "Account created, now u can sign in", Toast.LENGTH_LONG).show();
    }

    private void initView() {
        et_nameGarage = (EditText) findViewById(R.id.register_name_garage);
        et_domainGarage = (EditText) findViewById(R.id.register_domaine_garage);
        et_pwdGarage = (EditText) findViewById(R.id.register_password_garage);
        et_emailGarage = (EditText) findViewById(R.id.register_email_garage);
        et_telGarage = (EditText) findViewById(R.id.register_tel_garage);
        et_adressGarage = (EditText) findViewById(R.id.register_adresse_garage);

        registerGarage = (Button) findViewById(R.id.btn_register_garage_save);
        cancel = (Button) findViewById(R.id.btn_register_garage_cancel);
    }

    private String getStringFromEditText(EditText et){
        return et.getText().toString();
    }

}
