package com.example.user.findgarage10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.findgarage10.db.UserDAO;
import com.example.user.findgarage10.model.User;
import com.example.user.findgarage10.util.HashMyString;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText et_firstName;
    private EditText et_lastName;
    private EditText et_email;
    private EditText et_password;
    private EditText et_tel;
    private EditText et_adress;

    private Button btn_save;
    private Button btn_cancel;

    private UserDAO userDAO;
    private HashMyString hashMyString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        initView();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserAccount();
                goBackHome();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackHome();
            }
        });
    }

    private void goBackHome() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void createUserAccount() {
        String firstNAME = getStringFromEditText(et_firstName);
        String lastNAME = getStringFromEditText(et_lastName);
        String email = getStringFromEditText(et_email);
        String pwd = getStringFromEditText(et_password);
        String tel = getStringFromEditText(et_tel);
        String adress = getStringFromEditText(et_adress);

        hashMyString = new HashMyString(pwd);
        User newUser = new User(firstNAME, lastNAME, email, hashMyString.getMyHash(), tel, adress);
        userDAO = new UserDAO(this);
        userDAO = userDAO.openWritable();
        userDAO.insertUser(newUser);
        userDAO.close();
        Toast.makeText(this, "Account created, now u can sign in", Toast.LENGTH_LONG).show();
    }

    private void initView() {
        et_firstName = (EditText) findViewById(R.id.register_firstName_user);
        et_lastName = (EditText) findViewById(R.id.register_lastName_user);
        et_email = (EditText) findViewById(R.id.register_email_user);
        et_password = (EditText) findViewById(R.id.register_password_user);
        et_tel = (EditText) findViewById(R.id.register_tel_user);
        et_adress = (EditText) findViewById(R.id.register_adresse_user);
        btn_save = (Button) findViewById(R.id.btn_register_save);
        btn_cancel = (Button) findViewById(R.id.btn_register_cancel);
    }

    private String getStringFromEditText(EditText et){
        return et.getText().toString();
    }
}
