package com.example.smarthomeandroid.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smarthomeandroid.R;

public class BaseDialog {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button delete_home_submit_button;
    private OnClickListener onSubmitClickListener;
    private String buttonText;
    private String context;
    private String title;
    public BaseDialog() {
    }
    @SuppressLint("SetTextI18n")
    public void init(Activity activity, String title, String context, String buttonText) {
        this.buttonText = buttonText;
        this.context = context;
        this.title = title;
        dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.item_setting_delete_home, null);

        dialog = dialogBuilder.create();
        dialog.setView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUpUI(dialogView);
    }

    private void setUpUI(View dialogView) {
        TextView base_dialog_context_textView = dialogView.findViewById(R.id.base_dialog_context_textView);
        base_dialog_context_textView.setText(context);

        TextView base_dialog_title_textView = dialogView.findViewById(R.id.base_dialog_title_textView);
        base_dialog_title_textView.setText(title);

        delete_home_submit_button = dialogView.findViewById(R.id.base_dialog_submit_button);
        delete_home_submit_button.setText(buttonText);
        if (buttonText.equals("確認")) {
            delete_home_submit_button.setTextColor(Color.parseColor("#000000"));
        }
        delete_home_submit_button.setOnClickListener(this::onSubmitClicked);

    }

    private void onSubmitClicked(View view) {
        onSubmitClickListener.onSubmitClick();
    }

    public void show() {
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    public interface OnClickListener {
        void onSubmitClick();
    }

    public void setOnSubmitClickListener(OnClickListener onSubmitClickListener) {
        this.onSubmitClickListener = onSubmitClickListener;
    }
}
