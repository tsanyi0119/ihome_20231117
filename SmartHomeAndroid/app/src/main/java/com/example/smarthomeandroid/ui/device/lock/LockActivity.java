package com.example.smarthomeandroid.ui.device.lock;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.smarthomeandroid.R;

import java.util.ArrayList;
import java.util.List;

public class LockActivity extends AppCompatActivity implements LockContract.View{
    private Toolbar lock_toolbar;
    private ImageButton lock_add_imageButton;
    private GridLayout lock_gridLayout;
    private ImageButton lock_item_imageButton;
    private LockPresenter presenter;
    private List<ImageButton> lockItemButtons = new ArrayList<>();
    private RelativeLayout relativeLayout;
    private int currentColorIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        init();
    }

    private void init(){
        presenter = new LockPresenter(this);
        presenter.getLockSensor();
        setupUI();

    }

    private void setupUI() {
        setupToolbar();
        setupGridLayout();
        setupButton();

    }

    private void setupGridLayout() {
        lock_gridLayout = findViewById(R.id.lock_gridLayout);
        lock_gridLayout.setColumnCount(2);
    }

    private void setupToolbar() {
        lock_toolbar = findViewById(R.id.lock_toolbar);
        lock_toolbar.setNavigationIcon(getDrawable(R.drawable.ic_back_arrow));

        setSupportActionBar(lock_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 移除預設標題
        lock_toolbar.setNavigationOnClickListener(this::onBackPressed);
    }

    private void setupButton() {
        lock_add_imageButton = findViewById(R.id.lock_add_imageButton);
        lock_add_imageButton.setOnClickListener(this::onAddButtonClick);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onAddButtonClick(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View childView = inflater.inflate(R.layout.item_lock_grid, null);

        int position = lock_gridLayout.getChildCount();
        childView.setTag(position);

        lock_item_imageButton = childView.findViewById(R.id.lock_item_imageButton);
        lockItemButtons.add(lock_item_imageButton);
        lock_item_imageButton.setImageDrawable(getDrawable(R.drawable.ph_lock_item));
        relativeLayout = childView.findViewById(R.id.lock_item_relativeLayout);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) childView.getTag();
                Log.d("Clicked Item", "Position: " + position);
                lock_item_imageButton = lockItemButtons.get(position);
                Drawable currentDrawable = lock_item_imageButton.getDrawable();
                Drawable drawableDark = getDrawable(R.drawable.ph_lock_item);
                Drawable drawableBright = getDrawable(R.drawable.ph_unlock_item);
                if (currentDrawable != null) {
                    if (currentDrawable.getConstantState().equals(drawableDark.getConstantState())) {
                        lock_item_imageButton.setImageDrawable(drawableBright);
                    } else if (currentDrawable.getConstantState().equals(drawableBright.getConstantState())) {
                        lock_item_imageButton.setImageDrawable(drawableDark);
                    }
                }
            }
        });

        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "長按 事件触发", Toast.LENGTH_SHORT).show();
                showDuplicateUserDialog();
                return true;
            }
        });

        lock_gridLayout.addView(childView, lock_gridLayout.getChildCount());
    }

    @SuppressLint("MissingInflatedId")
    private void showDuplicateUserDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 建立自訂的佈局檔案的 View 物件
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_lockset, null);

        currentColorIndex = 0;

        // 取得佈局中的元件
        TextView textView8 = dialogView.findViewById(R.id.textView8);
        ImageView imageView4 = dialogView.findViewById(R.id.imageView4);
        EditText editTextTextPersonName = dialogView.findViewById(R.id.editTextTextPersonName);
        SeekBar seekBar = dialogView.findViewById(R.id.seekBar);

        // 設定 AlertDialog 的視圖為自訂的佈局
        builder.setView(dialogView);

        // 建立並顯示 AlertDialog
        AlertDialog alertDialog = builder.create();

        textView8.setVisibility(View.VISIBLE);
        editTextTextPersonName.setVisibility(View.GONE);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textView8.getVisibility() == View.VISIBLE){
                    editTextTextPersonName.setText(textView8.getText().toString());
                    editTextTextPersonName.setVisibility(View.VISIBLE);
                    textView8.setVisibility(View.GONE);
                }else if(textView8.getVisibility() == View.GONE){
                    textView8.setText(editTextTextPersonName.getText().toString());
                    editTextTextPersonName.setVisibility(View.GONE);
                    textView8.setVisibility(View.VISIBLE);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 根據進度值的範圍歸類
                if (progress >= 0 && progress <= 25) {
                    seekBar.setProgress(0);
                } else if (progress > 25 && progress <= 50) {
                    seekBar.setProgress(50);
                } else if (progress > 50 && progress <= 75) {
                    seekBar.setProgress(50);
                } else if (progress > 75 && progress <= 100) {
                    seekBar.setProgress(100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 開始觸摸SeekBar時的處理
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 停止觸摸SeekBar時的處理
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void onBackPressed(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
        finish();
    }

    @Override
    public void showLockSensor() {

    }
}