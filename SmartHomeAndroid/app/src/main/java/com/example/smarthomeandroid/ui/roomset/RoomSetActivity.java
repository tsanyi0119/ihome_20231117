package com.example.smarthomeandroid.ui.roomset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.smarthomeandroid.R;

import java.util.ArrayList;
import java.util.List;

public class RoomSetActivity extends AppCompatActivity {

    List<String> roomList = new ArrayList<>();

    private View view_color;
    private View roomset_linearLayout;
    private View roomset_title;
    private EditText edit_roomset_name;
    private ImageView img_backRoomManage;
    private int currentColorIndex = 0;
    private int position;
    private int[] colors = {
            R.color.primary_100,
            R.color.primary_200,
            R.color.primary_300,
            R.color.purple_200
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_set);
        init();
    }

    private void init(){
        Intent intent = getIntent();
        roomList = intent.getStringArrayListExtra("roomList");
        position = intent.getIntExtra("position", position);
        setupUI();
    }

    private void setupUI(){
        view_color = findViewById(R.id.view_color);
        img_backRoomManage = findViewById(R.id.img_backRoomManage);
        roomset_linearLayout = findViewById(R.id.roomset_linearLayout);
        roomset_title = findViewById(R.id.roomset_title);
        edit_roomset_name = findViewById(R.id.edit_roomset_name);
        edit_roomset_name.setText(roomList.get(position));

        view_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentColorIndex = (currentColorIndex + 1) % colors.length;
                int backgroundColor = getResources().getColor(colors[currentColorIndex]);
                int borderColor = Color.BLACK;
                int borderWidth = 4;

                roomset_title.setBackgroundColor(getResources().getColor(colors[currentColorIndex]));

                Drawable background = view_color.getBackground();
                if (background instanceof LayerDrawable) {
                    LayerDrawable layerDrawable = (LayerDrawable) background;
                    GradientDrawable shape = (GradientDrawable) layerDrawable.getDrawable(0);
                    shape.setColor(backgroundColor);

                    shape.setStroke(borderWidth, borderColor);
                }

                view_color.setBackground(background);

                Drawable roomBackground = roomset_linearLayout.getBackground();
                if (roomBackground instanceof LayerDrawable) {
                    LayerDrawable roomLayerDrawable = (LayerDrawable) roomBackground;
                    GradientDrawable roomShape = (GradientDrawable) roomLayerDrawable.getDrawable(0);
                    roomShape.setColor(backgroundColor);
                    roomShape.setStroke(borderWidth, borderColor);
                }
                roomset_linearLayout.setBackground(roomBackground);
            }
        });
        img_backRoomManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
//                resultIntent.putExtra("result_key", result);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}