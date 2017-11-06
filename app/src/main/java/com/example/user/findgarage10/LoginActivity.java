package com.example.user.findgarage10;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    //region declaration
    private EditText login_et_username;
    private EditText login_et_password;
    private Button login_btn_login;
    private Button btn_goto_signup;

    private FirebaseAuth mAuth;
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
        btn_goto_signup = (Button) findViewById(R.id.login_btn_sign_up);
        mAuth = FirebaseAuth.getInstance();


        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_et_username.getText().toString();
                String password = login_et_password.getText().toString();
                signInUserWithEmail(email, password);
            }
        });
        btn_goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goToSignUp();
                String email = login_et_username.getText().toString();
                String password = login_et_password.getText().toString();
                createUserWithEmail(email, password);
            }
        });
    }

    public void createUserWithEmail(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("user", "createUserWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Account created", Toast.LENGTH_LONG).show();
                        } else {
                            Log.w("user", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signInUserWithEmail(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("user", "signInWithEmail:success");
                            goToNearestGarage();
                        } else {
                            Log.w("user", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void goToSignUp(){
        startActivity(new Intent(this, RegisterUserActivity.class));
    }

    private void goToNearestGarage() {
        Intent intent;
        intent = new Intent(this, UserListNearestGarageActivity.class);
        startActivity(intent);
    }

}
