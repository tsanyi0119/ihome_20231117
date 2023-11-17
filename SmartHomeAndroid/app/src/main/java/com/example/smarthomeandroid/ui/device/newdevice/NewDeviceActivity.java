package com.example.smarthomeandroid.ui.device.newdevice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.device.newdevice.connectbluefragment.ConnectBlueFragment;

public class NewDeviceActivity extends AppCompatActivity {
    private Toolbar new_device_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device);
        init();



    }
    private void init() {
        setupToolbar();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.new_device_fragment_container, new ConnectBlueFragment(this))
                .commit();
    }
    private void setupToolbar() {
        new_device_toolbar = findViewById(R.id.new_device_toolbar);
        new_device_toolbar.setNavigationIcon(getDrawable(R.drawable.ic_close_image_foreground));

        setSupportActionBar(new_device_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 移除預設標題
        new_device_toolbar.setNavigationOnClickListener(this::onBackPressed);
    }

    private void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }
}