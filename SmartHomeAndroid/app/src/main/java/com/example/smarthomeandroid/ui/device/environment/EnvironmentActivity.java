package com.example.smarthomeandroid.ui.device.environment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.smarthomeandroid.R;

public class EnvironmentActivity extends AppCompatActivity {
    // TODO: 2023/10/12 壓住後停止Lottie動畫
    private Toolbar env_toolbar;
    private ConstraintLayout humidity_layout;
    private ConstraintLayout temperature_layout;
    private ConstraintLayout brightness_layout;
    private ConstraintLayout decibel_layout;
    private LottieAnimationView humidity_lottie;
    private LottieAnimationView temperature_lottie;
    private LottieAnimationView brightness_lottie;
    private LottieAnimationView decibel_lottie;
    private LottieAnimationView waves_lottie;
    private int humidity_state = 0;
    private int temperature_state = 0;
    private int brightness_state = 0;
    private int decibel_state = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);
        init();
    }
    private void init(){

        setupUI();
    }

    private void setupUI() {
        setupToolbar();
        setupLayout();
        setupLottie();
    }

    private void setupLottie() {
        brightness_lottie = findViewById(R.id.brightness_lottie);
        decibel_lottie = findViewById(R.id.decibel_lottie);
        humidity_lottie = findViewById(R.id.humidity_lottie);
        temperature_lottie = findViewById(R.id.temperature_lottie);
        waves_lottie = findViewById(R.id.waves_lottie);
    }

    private void setupLayout() {
        humidity_layout = findViewById(R.id.humidity_layout);
        temperature_layout = findViewById(R.id.temperature_layout);
        brightness_layout = findViewById(R.id.brightness_layout);
        decibel_layout = findViewById(R.id.decibel_layout);

        humidity_layout.setOnClickListener(this::humidityClicked);
        temperature_layout.setOnClickListener(this::temperatureClicked);
        brightness_layout.setOnClickListener(this::brightnessClicked);
        decibel_layout.setOnClickListener(this::decibelClicked);
    }

    private void brightnessClicked(View view) {
        if(brightness_state == 1) {
            brightness_lottie.playAnimation();
            brightness_lottie.setAlpha(1f);
            brightness_layout.setAlpha(1f);
            brightness_state = 0;
        } else {
            brightness_lottie.setProgress(0.4f);
            brightness_lottie.pauseAnimation();
            brightness_lottie.setAlpha(0.4f);
            brightness_layout.setAlpha(0.4f);
            brightness_state = 1;
        }
    }

    private void decibelClicked(View view) {
        if(decibel_state == 1) {
            decibel_lottie.playAnimation();
            decibel_lottie.setAlpha(1f);
            decibel_layout.setAlpha(1f);
            decibel_state = 0;
        } else {
            decibel_lottie.setProgress(0.58f);
            decibel_lottie.pauseAnimation();
            decibel_lottie.setAlpha(0.4f);
            decibel_layout.setAlpha(0.4f);
            decibel_state = 1;
        }

    }

    private void temperatureClicked(View view) {
        if (temperature_state == 1) {
            temperature_lottie.playAnimation();
            temperature_layout.setAlpha(1f);
            temperature_lottie.setAlpha(1f);
            temperature_state = 0;
        } else {
            temperature_lottie.setProgress(1);
            temperature_lottie.pauseAnimation();
            temperature_layout.setAlpha(0.4f);
            temperature_lottie.setAlpha(0.4f);
            temperature_state = 1;
        }
    }

    private void humidityClicked(View view) {
        if (humidity_state == 1) {
            humidity_lottie.playAnimation();
            waves_lottie.playAnimation();
            humidity_layout.setAlpha(1f);
            humidity_lottie.setAlpha(1f);
            waves_lottie.setAlpha(1f);
            humidity_state = 0;
        } else {
            humidity_lottie.setProgress(0);
            waves_lottie.setProgress(0);
            waves_lottie.pauseAnimation();
            humidity_lottie.pauseAnimation();
            humidity_layout.setAlpha(0.4f);
            humidity_lottie.setAlpha(0.4f);
            waves_lottie.setAlpha(0.4f);
            humidity_state = 1;
        }
    }


    private void setupToolbar() {
        env_toolbar = findViewById(R.id.env_toolbar);
        env_toolbar.setNavigationIcon(getDrawable(R.drawable.ic_back_arrow));

        setSupportActionBar(env_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 移除預設標題
        env_toolbar.setNavigationOnClickListener(this::onBackPressed);
    }

    private void onBackPressed(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }
}