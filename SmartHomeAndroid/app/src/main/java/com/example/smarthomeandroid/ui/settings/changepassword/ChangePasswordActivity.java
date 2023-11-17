package com.example.smarthomeandroid.ui.settings.changepassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.utils.initialization.MyApplication;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.View{
    private EditText  old_password_editText, new_password_editText, verify_password_editText;
    private Button change_password_button;
    private Toolbar change_password_toolbar;
    private ChangePasswordPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        init();
    }

    private void init() {
        presenter = new ChangePasswordPresenter(this);
        setupUI();
    }

    private void setupUI() {
        setupEditText();
        setupButton();
        setupActionBar();
    }
    private void setupEditText() {
        old_password_editText = findViewById(R.id.change_password_old_editText);
        new_password_editText = findViewById(R.id.change_password_new_editText);
        verify_password_editText = findViewById(R.id.change_password_verify_editText);
    }

    private void setupButton() {
        change_password_button = findViewById(R.id.change_password_confirm_button);
        change_password_button.setOnClickListener(this::onConfirmClick);
    }

    private void setupActionBar() {
        change_password_toolbar = findViewById(R.id.change_password_toolbar);
        change_password_toolbar.setNavigationIcon(getDrawable(R.drawable.ic_back_arrow));
        change_password_toolbar = findViewById(R.id.change_password_toolbar);

        setSupportActionBar(change_password_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 移除預設標題
        change_password_toolbar.setNavigationOnClickListener(this::onBackPressed);
    }

    private void onConfirmClick(View view) {
        if(old_password_editText.getText().toString().isEmpty()){
            old_password_editText.setError("請輸入舊密碼");
            old_password_editText.requestFocus();
            return;
        } else if (new_password_editText.getText().toString().isEmpty()){
            new_password_editText.setError("請輸入新密碼");
            new_password_editText.requestFocus();
            return;
        } else if (verify_password_editText.getText().toString().isEmpty()){
            verify_password_editText.setError("請再次輸入新密碼");
            verify_password_editText.requestFocus();
            return;
        } else if (!new_password_editText.getText().toString().equals(verify_password_editText.getText().toString())){
            verify_password_editText.setError("輸入的密碼不一致");
            new_password_editText.setError("輸入的密碼不一致");
            verify_password_editText.requestFocus();
            return;
        } else {
            presenter.ChangePassword(new_password_editText.getText().toString(), old_password_editText.getText().toString());
        }
    }


    @Override
    public void onChangeSuccess() {
        MyApplication.getInstance().handleUnauthenticated();
        Toast.makeText(this, "重設密碼成功，請重新登入", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onChangeError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }
}