package com.example.user.findgarage10;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.findgarage10.db.GarageDAO;
import com.example.user.findgarage10.db.UserDAO;
import com.example.user.findgarage10.model.Garage;
import com.example.user.findgarage10.model.User;
import com.example.user.findgarage10.util.HashMyString;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    //region declaration
    private EditText login_et_username;
    private EditText login_et_password;
    private Button login_btn_login;
    private Spinner spinner;
    private UserDAO userDAO;
    private GarageDAO garageDAO;
    private HashMyString hashMyString;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void initView() {
        login_et_username = (EditText) findViewById(R.id.login_et_username);
        login_et_password = (EditText) findViewById(R.id.login_et_password);
        login_btn_login = (Button) findViewById(R.id.login_btn_login);

        initSpinnerTypeUser();

        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNearestGarage();
            }
        });
    }

    private void initSpinnerTypeUser() {
        spinner = (Spinner) findViewById(R.id.login_spinner_type_user);
        List<String> list_type_user = new ArrayList<>();
        list_type_user.add("User");
        list_type_user.add("Garage");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_type_user);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void goToNearestGarage() {
        Intent intent;
        String loginType = spinner.getSelectedItem().toString();
        if (loginType == "Garage") {
            String nameGarage = login_et_username.getText().toString();
            String pwdGarage = login_et_password.getText().toString();
            hashMyString = new HashMyString(pwdGarage);

            Garage garage = verifyConnexionGarage(nameGarage, hashMyString.getMyHash());
            if (garage != null) {
                intent = new Intent(this, GarageMyDevisActivity.class);
                intent.putExtra("garage", garage);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Connexion to database failed", Toast.LENGTH_LONG).show();
            }
        } else {
            String nameUser = login_et_username.getText().toString();
            String pwdUser = login_et_password.getText().toString();
            hashMyString = new HashMyString(pwdUser);
            User user = verifyConnexionUser(nameUser, hashMyString.getMyHash());
            if (user != null) {
                intent = new Intent(this, UserListNearestGarageActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Connexion to database failed", Toast.LENGTH_LONG).show();
            }
        }

        //startActivity(intent);
    }

    private Garage verifyConnexionGarage(String name, String password) {
        garageDAO = new GarageDAO(this);
        garageDAO = garageDAO.openReadable();
        Garage toReturn = garageDAO.getGarageByLogin(name, password);

        if (toReturn == null) {
            Toast.makeText(this, "login or password incorrect", Toast.LENGTH_LONG).show();
            garageDAO.close();
        } else {
            Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show();
        }
        return toReturn;
    }

    public User verifyConnexionUser(String firstName, String password) {

        userDAO = new UserDAO(this);
        userDAO = userDAO.openReadable();

        if (userDAO.getUserByLogin(firstName, password) == null) {
            Toast.makeText(this, "login or password incorrect", Toast.LENGTH_LONG).show();
            userDAO.close();
            return null;
        } else {
            User user = userDAO.getUserByLogin(firstName, password);
            //Toast.makeText(this, "Welcome " + user.getLastName_user(), Toast.LENGTH_LONG).show();
            return user;
        }
    }

}
