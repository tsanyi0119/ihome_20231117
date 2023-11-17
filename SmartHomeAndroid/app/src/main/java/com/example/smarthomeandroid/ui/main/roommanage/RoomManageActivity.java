package com.example.smarthomeandroid.ui.main.roommanage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.roomset.RoomSetActivity;
import com.example.smarthomeandroid.utils.api.soap.response.RoomResponse;

import java.util.ArrayList;
import java.util.List;

public class RoomManageActivity extends AppCompatActivity implements RoomManageContract.View{
    private ListView listView;
    private List<String> roomList = new ArrayList<>();
    private String homeName;
    private Long homeId;
    private RoomManageContract.Presenter presenter;
    private ImageButton lock_add_imageButton;
    private ImageView img_backMain;
    private int currentColorIndex;
    private int[] colors = {
            R.color.primary_100,
            R.color.primary_200,
            R.color.primary_300,
            R.color.purple_200
    };
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_manage);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            }
        }
    }

    private void init(){
        Intent intent = getIntent();
        roomList = intent.getStringArrayListExtra("roomList");
        homeName = intent.getStringExtra("homeName");
        homeId = intent.getLongExtra("homeId",0);
        presenter = new RoomManagePresenter(this);
        setupUI();
    }


    private void setupUI(){
        listView = findViewById(R.id.room_manage_listView);
        lock_add_imageButton = findViewById(R.id.lock_add_imageButton);
        img_backMain = findViewById(R.id.img_backRoomManage);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_roommanage_list, roomList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoomManageActivity.this,RoomSetActivity.class);
                intent.putStringArrayListExtra("roomList", (ArrayList<String>) roomList);
                intent.putExtra("postion", position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        lock_add_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDuplicateUserDialog();
            }
        });
        img_backMain.setOnClickListener(this::onBackClicked);

    }
    private void onBackClicked(View view) {
        super.onBackPressed();
        Intent resultIntent = new Intent();
//                resultIntent.putExtra("result_key", result);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    @SuppressLint("MissingInflatedId")
    private void showDuplicateUserDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 建立自訂的佈局檔案的 View 物件
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_addroom, null);

        currentColorIndex = 0;

        // 取得佈局中的元件
        TextView tv_home_name_show =dialogView.findViewById(R.id.tv_home_name_show);
        EditText edit_roon_name = dialogView.findViewById(R.id.edit_room_name);
        Button btn_date_summit = dialogView.findViewById(R.id.btn_date_summit);
        View view_color = dialogView.findViewById(R.id.view_color);
        View roomadd_title = dialogView.findViewById(R.id.roomadd_title);
        tv_home_name_show.setText(homeName);
        edit_roon_name.setText("");

        // 設定 AlertDialog 的視圖為自訂的佈局
        builder.setView(dialogView);

        // 建立並顯示 AlertDialog
        AlertDialog alertDialog = builder.create();

        btn_date_summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addRoom(homeId,edit_roon_name.getText().toString(),String.valueOf(colors[currentColorIndex]));
                alertDialog.dismiss();
            }
        });

        view_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentColorIndex = (currentColorIndex + 1) % colors.length;
                int backgroundColor = getResources().getColor(colors[currentColorIndex]);
                int borderColor = Color.BLACK;
                int borderWidth = 4;

                Drawable background = view_color.getBackground();
                if (background instanceof LayerDrawable) {
                    LayerDrawable layerDrawable = (LayerDrawable) background;
                    GradientDrawable shape = (GradientDrawable) layerDrawable.getDrawable(0);
                    shape.setColor(backgroundColor);
                    shape.setStroke(borderWidth, borderColor);
                }
                view_color.setBackground(background);

                Drawable titleBackground = roomadd_title.getBackground();
                if (titleBackground instanceof LayerDrawable) {
                    LayerDrawable roomLayerDrawable = (LayerDrawable) titleBackground;
                    GradientDrawable roomShape = (GradientDrawable) roomLayerDrawable.getDrawable(0);
                    roomShape.setColor(backgroundColor);
                    roomShape.setStroke(borderWidth, borderColor);
                }
                roomadd_title.setBackground(titleBackground);

                Drawable originalBackground = btn_date_summit.getBackground();
                GradientDrawable customBorder = new GradientDrawable();
                customBorder.setColor(backgroundColor);
                customBorder.setStroke(borderWidth, borderColor);
                float cornerRadiusInDp = 20;
                float cornerRadiusInPx = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        cornerRadiusInDp,
                        getResources().getDisplayMetrics()
                );
                customBorder.setCornerRadius(cornerRadiusInPx);
                Drawable[] layers = {originalBackground, customBorder};
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                btn_date_summit.setBackground(layerDrawable);
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    @Override
    public void showRoom(List<RoomResponse.RoomData> roomDataList) {
        roomList.clear();
        for(RoomResponse.RoomData roomData : roomDataList){
            roomList.add(roomData.getRoomName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_roommanage_list, roomList);

        listView.setAdapter(adapter);
    }
}