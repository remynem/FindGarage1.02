package com.example.user.findgarage10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //region declaration
    private Button btn_main_goto_login;
    private Button btn_main_goto_signup;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCenter.start(getApplication(), "0afb88c1-6e69-4ca8-94b5-7d1e22f8d151",
                Analytics.class, Crashes.class);
        initView();
    }

    private void initView() {
        btn_main_goto_login = (Button) findViewById(R.id.main_goto_login);
        btn_main_goto_signup = (Button) findViewById(R.id.main_goto_signup);

        btn_main_goto_login.setOnClickListener(this);
        btn_main_goto_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.main_goto_login:
                gotoLogin();
                break;
            case R.id.main_goto_signup:
                gotoSignUp();
                break;
            default:
                break;
        }
    }

    private void gotoLogin() {
        gotoActivity(LoginActivity.class);
    }

    private void gotoSignUp() {
        gotoActivity(RegisterActivity.class);
    }

    private void gotoActivity(Class target) {
        Intent intent = new Intent(this, target);
        startActivity(intent);
    }
}
