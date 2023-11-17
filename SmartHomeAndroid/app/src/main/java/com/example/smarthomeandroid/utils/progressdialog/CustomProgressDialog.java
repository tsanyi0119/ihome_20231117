package com.example.smarthomeandroid.utils.progressdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.smarthomeandroid.R;

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(@NonNull Context context) {
        super(context);
    }

    public static CustomProgressDialog show(Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        // 设置对话框的大小和位置等属性
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            // 设置对话框的位置
            params.gravity = Gravity.CENTER;
            // 设置对话框的大小
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }

        dialog.show();
        return dialog;
    }

    public void dismissDialog() {
        dismiss();
    }
}
