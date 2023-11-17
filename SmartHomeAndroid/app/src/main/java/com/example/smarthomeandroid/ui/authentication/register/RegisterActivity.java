package com.example.smarthomeandroid.ui.authentication.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.authentication.register.registerfragment.RegisterFragment;
import com.example.smarthomeandroid.ui.authentication.register.registerverifyfragment.RegisterVerifyFragment;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {
    private Toolbar register_toolbar;
    private FrameLayout register_frame_layout;
    private RegisterFragment registerFragment;
    private RegisterVerifyFragment registerVerifyFragment;
    private FragmentTransaction transaction;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

    }

    private void init(){

        presenter = new RegisterPresenter(this);
        setupUI();
        registerFragment = new RegisterFragment(this);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.register_frame_layout,registerFragment)
                .commitNow();

        startFloatUpAnimation();

    }

    private void setupUI() {
        register_toolbar = findViewById(R.id.register_toolbar);
        register_toolbar.setNavigationIcon(getDrawable(R.drawable.ic_back_arrow));
        register_frame_layout = findViewById(R.id.register_frame_layout);

        setSupportActionBar(register_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 移除預設標題
        register_toolbar.setNavigationOnClickListener(this::onBackPressed);

    }

    //驗證頁面返回進入
    public void onRegisterVerifyBack(int i) {
        if(i == 0) {
            getSupportFragmentManager().beginTransaction()
                    .remove(registerVerifyFragment)
                    .show(registerFragment)
                    .commit();

            startFloatUpAnimation();
        } else {

            finish();
        }

    }
    //按下註冊
    public void onRegisterClicked() {
        registerVerifyFragment = new RegisterVerifyFragment(this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.register_frame_layout,registerVerifyFragment)
                .show(registerFragment)
                .commit();

        startFloatUpAnimation();
    }


    // 讓 FrameLayout 加入動畫
    private void startFloatUpAnimation() {
        ObjectAnimator floatUpAnimator = ObjectAnimator.ofFloat(
                register_frame_layout,
                "translationY",
                1600f,
                0f
        );
        floatUpAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        floatUpAnimator.setDuration(1000);
        floatUpAnimator.start();
    }

    private void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }

}