package com.example.smarthomeandroid.ui.authentication.register.registerfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.authentication.register.RegisterActivity;
import com.example.smarthomeandroid.utils.progressdialog.CustomProgressDialog;


public class RegisterFragment extends Fragment implements RegisterFragmentContract.View {
    private Button register_button;
    private RegisterFragmentPresenter presenter;
    private RegisterActivity activity;
    private EditText email_edittext;
    private EditText password_edittext;
    private EditText username_edittext;
    private CustomProgressDialog progressDialog;

    public RegisterFragment(RegisterActivity activity) {
        presenter = new RegisterFragmentPresenter(this, activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        register_button = view.findViewById(R.id.register_button);
        username_edittext = view.findViewById(R.id.register_username_edittext);
        email_edittext = view.findViewById(R.id.register_email_edittext);
        password_edittext = view.findViewById(R.id.register_password_edittext);

        register_button.setOnClickListener(this::registerClicked);

    }



    private void registerClicked(View view) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("fcm", Context.MODE_PRIVATE);
        String fcmId = sharedPreferences.getString("fcmId","");
        progressDialog = CustomProgressDialog.show(activity);
        presenter.register(username_edittext.getText().toString(),
                email_edittext.getText().toString(),
                password_edittext.getText().toString(),
                fcmId);
    }
    @Override
    public void onRegisterSuccess() {
        progressDialog.dismissDialog();
        activity.onRegisterClicked();
    }

    @Override
    public void onRegisterFail() {
        progressDialog.dismissDialog();
        Toast.makeText(activity, "Register Fail", Toast.LENGTH_SHORT).show();
    }
}