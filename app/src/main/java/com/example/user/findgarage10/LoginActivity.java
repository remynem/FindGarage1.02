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

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static int CONNECTED = 1;
    //region declaration
    private EditText login_et_username;
    private EditText login_et_password;
    private Button login_btn_login;
    private Spinner spinner;
    private UserDAO userDAO;
    private GarageDAO garageDAO;
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
        if(loginType == "Garage"){
            Garage garage =  verifyConnexionGarage(login_et_username.getText().toString(), login_et_password.getText().toString());
            intent = new Intent(this, GarageMyDevisActivity.class);
            intent.putExtra("garage", garage);
            startActivity(intent);
        }else{
            User user = verifyConnexionUser(login_et_username.getText().toString(), login_et_password.getText().toString());
            if(user != null){
                intent = new Intent(this, UserListNearestGarageActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Connexion failed", Toast.LENGTH_LONG).show();
            }
        }

        //startActivity(intent);
    }

    private Garage verifyConnexionGarage(String name, String domaine) {
        garageDAO = new GarageDAO(this);
        garageDAO = garageDAO.openReadable();
        Garage toReturn = garageDAO.getGarageByLogin(name, domaine);
        if(toReturn == null){
            Toast.makeText(this, "Beug - garage", Toast.LENGTH_LONG).show();
            garageDAO.close();
        }else{
            Toast.makeText(this, "Garage" + domaine, Toast.LENGTH_LONG).show();
        }
        return toReturn;
    }

    public User verifyConnexionUser(String firstName, String lastName){

        userDAO = new UserDAO(this);
        userDAO.openWritable();
        userDAO.initTableUser();

        userDAO = userDAO.openReadable();

        if (userDAO.getUserByLogin(firstName, lastName) == null) {
            Toast.makeText(this, "Beug user", Toast.LENGTH_LONG).show();
            userDAO.close();
            return null;
        } else {
            User user = userDAO.getUserByLogin(firstName, lastName);
            Toast.makeText(this, "Bienvenue " + user.getNum_user(), Toast.LENGTH_LONG).show();
            return user;
        }
    }

}
