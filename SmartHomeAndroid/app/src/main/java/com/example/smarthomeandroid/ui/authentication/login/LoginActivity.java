package com.example.smarthomeandroid.ui.authentication.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.authentication.register.RegisterActivity;
import com.example.smarthomeandroid.ui.main.MainActivity;
import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.progressdialog.CustomProgressDialog;
import com.example.smarthomeandroid.utils.sharedpreferences.LoginSharedPreferences;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{
    private Button login_button;
    private Button register_button;
    private EditText email_edittext;
    private EditText password_edittext;
    private EditText username_edittext;
    private LoginPresenter presenter;
    private ConstraintLayout login_constraint_layout;
    private LoginSharedPreferences loginSharedPreferences;
    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        //判斷有沒有token存在，有的會就直接用
        if (!loginSharedPreferences.getToken().isEmpty()){
            Log.e("TAG", "onCreate: " + loginSharedPreferences.getToken());
            ApiClient.getSmartHomeInstance().setAuthToken(loginSharedPreferences.getToken(), loginSharedPreferences.getRefreshToken());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void init() {
        loginSharedPreferences = new LoginSharedPreferences(this);
        presenter = new LoginPresenter(this,this);
        setupUI();
    }

    private void setupUI() {
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.login_register_button);
        email_edittext = findViewById(R.id.login_email_edittext);
        password_edittext = findViewById(R.id.login_password_edittext);
        login_constraint_layout = findViewById(R.id.login_constraint_layout);

        login_button.setOnClickListener(this::loginClicked);
        register_button.setOnClickListener(this::registerClicked);


    }

    private void startFloatUpAnimation() {
        ObjectAnimator floatUpAnimator = ObjectAnimator.ofFloat(
                login_constraint_layout,
                "translationY",
                1500f,
                0f
        );
        floatUpAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        floatUpAnimator.setDuration(1000);
        floatUpAnimator.start();
    }


    private void registerClicked(View view) {
        overridePendingTransition(0, 0);
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startFloatUpAnimation();
    }

    private void loginClicked(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
        progressDialog = CustomProgressDialog.show(this);
        presenter.login(email_edittext.getText().toString(),
                password_edittext.getText().toString());
    }

    @Override
    public void loginError(String error) {
        progressDialog.dismissDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        progressDialog.dismissDialog();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void incompleteInput() {
        progressDialog.dismissDialog();
        Toast.makeText(this, "帳號密碼輸入不完整", Toast.LENGTH_SHORT).show();
    }
}