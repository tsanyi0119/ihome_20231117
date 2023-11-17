package com.example.smarthomeandroid.ui.device.light;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.device.newdevice.NewDeviceActivity;

import java.util.ArrayList;
import java.util.List;

public class LightActivity extends AppCompatActivity implements LightContract.View{
    private Toolbar light_toolbar;
    private ImageButton light_add_imageButton;
    private GridLayout light_gridLayout;
    private View light_linearLayout;
    private LottieAnimationView light_add_lottieAnimationView;
    private ImageButton light_item_imageButton;
    private LightPresenter presenter;
    private RelativeLayout relativeLayout;
    private List<ImageButton> lightItemButtons = new ArrayList<>();
    private int currentColorIndex;
    private int[] colors = {
            R.color.primary_100,
            R.color.primary_200,
            R.color.primary_300,
            R.color.purple_200
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        init();
    }

    private void init(){
        presenter = new LightPresenter(this);
        presenter.getLightSensor();
        setupUI();

    }

    private void setupUI() {
        setupToolbar();
        setupGridLayout();
        setupButton();

    }

    private void setupGridLayout() {
        light_gridLayout = findViewById(R.id.light_gridLayout);
        light_gridLayout.setColumnCount(2);
    }

    private void setupToolbar() {
        light_toolbar = findViewById(R.id.light_toolbar);
        light_toolbar.setNavigationIcon(getDrawable(R.drawable.ic_back_arrow));

        setSupportActionBar(light_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 移除預設標題
        light_toolbar.setNavigationOnClickListener(this::onBackPressed);
    }

    private void setupButton() {
        light_add_imageButton = findViewById(R.id.light_add_imageButton);
        light_add_imageButton.setOnClickListener(this::onAddButtonClick);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onAddButtonClick(View view) {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View childView = inflater.inflate(R.layout.item_light_grid, null);
//
//        int position = light_gridLayout.getChildCount();
//        childView.setTag(position);
        Intent intent = new Intent(this, NewDeviceActivity.class);
        startActivity(intent);



//        light_item_imageButton = childView.findViewById(R.id.light_item_imageButton);
//        lightItemButtons.add(light_item_imageButton);
//        light_linearLayout = childView.findViewById(R.id.light_item_linearLayout);
//        light_item_imageButton.setImageDrawable(getDrawable(R.drawable.ph_bulb_dark));
//        relativeLayout = childView.findViewById(R.id.light_item_relativeLayout);

        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), "長按 事件触发", Toast.LENGTH_SHORT).show();
                showDuplicateUserDialog();
                return true;
            }
        });

//        light_gridLayout.addView(childView, light_gridLayout.getChildCount());
//        relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = (int) childView.getTag();
//                Log.d("Clicked Item", "Position: " + position);
//                light_item_imageButton = lightItemButtons.get(position);
//                Drawable currentDrawable = light_item_imageButton.getDrawable();
//                Drawable drawableDark = getDrawable(R.drawable.ph_bulb_dark);
//                Drawable drawableBright = getDrawable(R.drawable.ph_bulb_bright);
//                if (currentDrawable != null) {
//                    if (currentDrawable.getConstantState().equals(drawableDark.getConstantState())) {
//                        light_item_imageButton.setImageDrawable(drawableBright);
//                    } else if (currentDrawable.getConstantState().equals(drawableBright.getConstantState())) {
//                        light_item_imageButton.setImageDrawable(drawableDark);
//                    }
//                }
//            }
//        });

//        light_gridLayout.addView(childView, light_gridLayout.getChildCount());
    }

    @SuppressLint("MissingInflatedId")
    private void showDuplicateUserDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 建立自訂的佈局檔案的 View 物件
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_lightset, null);

        currentColorIndex = 0;

        // 取得佈局中的元件
        TextView textView8 = dialogView.findViewById(R.id.textView8);
        ImageView imageView4 = dialogView.findViewById(R.id.imageView4);
        EditText editTextTextPersonName = dialogView.findViewById(R.id.editTextTextPersonName);

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
    public void showLightSensor() {

    }
}