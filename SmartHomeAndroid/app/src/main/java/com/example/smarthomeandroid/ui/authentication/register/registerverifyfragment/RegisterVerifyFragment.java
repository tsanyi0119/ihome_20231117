package com.example.smarthomeandroid.ui.authentication.register.registerverifyfragment;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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


public class RegisterVerifyFragment extends Fragment implements RegisterVerifyFragmentContract.View {
    private Button register_verify_button;
    private RegisterVerifyFragmentPresenter presenter;
    private EditText verify_edittext,verify_edittext2,verify_edittext3,verify_edittext4,verify_edittext5,verify_edittext6;
    private RegisterActivity activity;
    private CustomProgressDialog progressDialog;
    public RegisterVerifyFragment(RegisterActivity activity) {
        presenter = new RegisterVerifyFragmentPresenter(this,activity);
        this.activity =activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_verify, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

    }
    private void init(View view) {
        setupUI(view);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                activity.onRegisterVerifyBack(0);
            }
        });
    }

    private void setupUI(View view) {
        register_verify_button = view.findViewById(R.id.register_verify_button);
        verify_edittext = view.findViewById(R.id.register_verify_edittext);
        verify_edittext2 = view.findViewById(R.id.register_verify_edittext2);
        verify_edittext3 = view.findViewById(R.id.register_verify_edittext3);
        verify_edittext4 = view.findViewById(R.id.register_verify_edittext4);
        verify_edittext5 = view.findViewById(R.id.register_verify_edittext5);
        verify_edittext6 = view.findViewById(R.id.register_verify_edittext6);
        register_verify_button.setOnClickListener(this::registerVerifyClicked);
    }

    private void registerVerifyClicked(View view) {
        String verifyCode = verify_edittext.getText().toString() +
                        verify_edittext2.getText().toString() +
                        verify_edittext3.getText().toString() +
                        verify_edittext4.getText().toString() +
                        verify_edittext5.getText().toString() +
                        verify_edittext6.getText().toString();
        progressDialog = CustomProgressDialog.show(view.getContext());
        presenter.registerVerify(verifyCode);

    }


    @Override
    public void registerVerifySuccess() {
        progressDialog.dismissDialog();
        activity.onRegisterVerifyBack(1);
    }

    @Override
    public void registerVerifyFail(String message) {
        progressDialog.dismissDialog();
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}