package com.example.smarthomeandroid.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smarthomeandroid.R;

public class MainHomeDialog {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button home_add_button;
    private EditText home_name_editText;
    private MainHomeDialog.OnClickListener onSubmitClickListener;
    private Context context;

    public MainHomeDialog(Activity activity) {
        init(activity);
        context = activity;
    }
    @SuppressLint("SetTextI18n")
    private void init(Activity activity) {
        dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.item_main_home_dialog, null);
        home_name_editText = dialogView.findViewById(R.id.home_name_editText);
        dialog = dialogBuilder.create();
        dialog.setView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUpUI(dialogView);
    }

    private void setUpUI(View dialogView) {
        home_add_button = dialogView.findViewById(R.id.home_add_button);
        home_add_button.setOnClickListener(this::onSubmitClicked);
    }

    private void onSubmitClicked(View view) {
        if(!home_name_editText.getText().toString().equals("")) {
            dialog.dismiss();
            onSubmitClickListener.onSubmitClick(home_name_editText.getText().toString());
        } else {
            Toast.makeText(context,"家庭名稱不得為空!",Toast.LENGTH_SHORT).show();

        }

    }

    public void show() {
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public interface OnClickListener {
        void onSubmitClick(String homeName);
    }

    public void setOnSubmitClickListener(MainHomeDialog.OnClickListener onSubmitClickListener) {
        this.onSubmitClickListener = onSubmitClickListener;
    }
}

