package com.example.smarthomeandroid.ui.device.monitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.device.light.LightPresenter;
import com.example.smarthomeandroid.ui.device.lock.LockPresenter;

import java.util.ArrayList;
import java.util.List;

public class MonitorActivity extends AppCompatActivity {
    private Toolbar monitor_toolbar;
    private ImageButton monitor_add_imageButton;
    private GridLayout monitor_gridLayout;
    private ImageButton monitor_item_imageButton;
    private List<ImageButton> monitorButtonsList = new ArrayList<>();
    private RelativeLayout monitor_relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        init();
    }

    private void init() {
        setupUI();
    }

    private void setupUI() {
        setupToolbar();
        setupGridLayout();
        setupButton();
    }

    private void setupGridLayout() {
        monitor_gridLayout = findViewById(R.id.monitor_gridLayout);
        monitor_gridLayout.setColumnCount(2);
    }

    private void setupToolbar() {
        monitor_toolbar = findViewById(R.id.light_toolbar);
        monitor_toolbar.setNavigationIcon(getDrawable(R.drawable.ic_back_arrow));

        setSupportActionBar(monitor_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 移除預設標題
        monitor_toolbar.setNavigationOnClickListener(this::onBackPressed);
    }

    private void setupButton() {
        monitor_add_imageButton = findViewById(R.id.monitor_add_imageButton);
        monitor_add_imageButton.setOnClickListener(this::onAddButtonClick);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "MissingInflatedId"})
    private void onAddButtonClick(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.item_lock_grid, null);

        int position = monitor_gridLayout.getChildCount();
        childView.setTag(position);

        monitor_item_imageButton = childView.findViewById(R.id.lock_item_imageButton);
        monitorButtonsList.add(monitor_item_imageButton);
        monitor_item_imageButton.setImageDrawable(getDrawable(R.drawable.ph_lock_item));
        monitor_relativeLayout = childView.findViewById(R.id.monitor_item_relativeLayout);
        monitor_relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) childView.getTag();
                Log.d("Clicked Item", "Position: " + position);
                monitor_item_imageButton = monitorButtonsList.get(position);
                Drawable currentDrawable = monitor_item_imageButton.getDrawable();
                Drawable drawableDark = getDrawable(R.drawable.ph_lock_item);
                Drawable drawableBright = getDrawable(R.drawable.ph_unlock_item);
                if (currentDrawable != null) {
                    if (currentDrawable.getConstantState().equals(drawableDark.getConstantState())) {
                        monitor_item_imageButton.setImageDrawable(drawableBright);
                    } else if (currentDrawable.getConstantState().equals(drawableBright.getConstantState())) {
                        monitor_item_imageButton.setImageDrawable(drawableDark);
                    }
                }
            }
        });

        monitor_gridLayout.addView(childView, monitor_gridLayout.getChildCount());
    }

    private void onBackPressed(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }
}