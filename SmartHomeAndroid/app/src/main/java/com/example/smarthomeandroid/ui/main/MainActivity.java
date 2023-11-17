package com.example.smarthomeandroid.ui.main;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.device.environment.EnvironmentActivity;
import com.example.smarthomeandroid.ui.device.light.LightActivity;
import com.example.smarthomeandroid.ui.device.lock.LockActivity;
import com.example.smarthomeandroid.ui.device.monitor.imagemain.MonitorVideoActivity;
import com.example.smarthomeandroid.ui.main.roommanage.RoomManageActivity;
import com.example.smarthomeandroid.ui.settings.setting.SettingActivity;
import com.example.smarthomeandroid.utils.api.soap.response.DeviceQtyResponse;
import com.example.smarthomeandroid.utils.api.soap.response.HomeResponse;
import com.example.smarthomeandroid.utils.progressdialog.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private RecyclerView recyclerView;
    private MainAdapter mAdapter;
    private MainPresenter presenter;
    private TextView t_textView;
    private TextView ws_textView;
    private TextView rh_textView;
    private TextView pop6h_textView;
    private TextView light_textView;
    private TextView light_qty_textView;
    private TextView monitor_textView;
    private TextView monitor_qty_textView;
    private TextView environment_textView;
    private TextView environment_qty_textView;
    private TextView lock_textView;
    private TextView lock_qty_textView;
    private LinearLayout home_name_linearLayout;
    private LottieAnimationView wx_imageView;
    private ImageButton room_setting_imageButton;
    private CustomProgressDialog progressDialog;
    private String roomSelect;
    private TextView home_textView;
    private Long homeId;
    private MainHomeDialog mainHomeDialog;
    private ImageButton main_setting_imageButton;
    private List<String> roomList = new ArrayList<>();
    private List<String> roomNameList = new ArrayList<>();
    private List<HomeResponse.HomeData> homeDataList = new ArrayList<>();
    private List<String> homeList = new ArrayList<>();
    private static final int REQUEST_CODE_ROOM_MANAGE = 1; // 定义一个请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private void init() {
        presenter = new MainPresenter(this);
        presenter.getWeatherData("臺中市", "北區");
        setupUI();
        progressDialog = CustomProgressDialog.show(this);
        //抓住家資料
        presenter.getHomeData();
        mainHomeDialog = new MainHomeDialog(this);
        mainHomeDialog.setOnSubmitClickListener(getOnSubmitClickListener());
        light_textView.setText("燈光");
        monitor_textView.setText("監視器:");
        environment_textView.setText("環境感測");
        lock_textView.setText("鎖具");
    }

    private void showCustomPopupWindow() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.item_main_roomcontrol, null);

        ListView listView = popupView.findViewById(R.id.room_listView);
        View room_mange_view = popupView.findViewById(R.id.room_manage_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_main_list, roomList);

        listView.setAdapter(adapter);

        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        int[] location = new int[2];
        room_setting_imageButton.getLocationOnScreen(location);
        popupWindow.showAtLocation(room_setting_imageButton, Gravity.NO_GRAVITY, location[0] + room_setting_imageButton.getWidth(), location[1] + room_setting_imageButton.getHeight() + 10);

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        room_mange_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Intent intent = new Intent(MainActivity.this, RoomManageActivity.class);
                intent.putExtra("homeName", roomSelect);
                intent.putStringArrayListExtra("roomList", (ArrayList<String>) roomList);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                recyclerView.smoothScrollToPosition(position);
            }
        });
    }

    private void showCustomHomePopupWindow(View view) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.item_main_home_control, null);

        ListView listView = popupView.findViewById(R.id.home_listView);
        View home_manage_view = popupView.findViewById(R.id.home_manage_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_main_list, homeList);

        listView.setAdapter(adapter);

        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        int[] location = new int[2];
        home_textView.getLocationOnScreen(location);
        popupWindow.showAtLocation(home_textView, Gravity.NO_GRAVITY, location[0] - 30, location[1] + room_setting_imageButton.getHeight() + 20);

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        home_manage_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                mainHomeDialog.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                home_textView.setText(homeList.get(position));

                mAdapter.setRoomList(homeDataList.get(position).getRooms());
                presenter.getDeviceQty(homeDataList.get(position).getRooms().get(0).getId());
                roomSelect = homeDataList.get(position).getHomeName();
                homeId = homeDataList.get(position).getId();
                // 在homeData方法中設置RecyclerView的點擊監聽
                mAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        HomeResponse.HomeData.Room selectedRoom = homeDataList.get(0).getRooms().get(position);
                        Long selectedRoomId = selectedRoom.getId();
                        //抓取指定房間設備數量
                        presenter.getDeviceQty(selectedRoomId);
                    }
                });
                popupWindow.dismiss();

            }
        });
    }

    private void setupUI() {
        setupTextView();
        setupImageView();
//        setupRecyclerView();
        setupImageButton();
        setupLayout();
    }

    private void setupLayout() {
        findViewById(R.id.main_light_include).setOnClickListener(onChangeActivityClickListener);
        findViewById(R.id.main_lock_include).setOnClickListener(onChangeActivityClickListener);
        findViewById(R.id.main_environment_include).setOnClickListener(onChangeActivityClickListener);
        findViewById(R.id.main_monitor_include).setOnClickListener(onChangeActivityClickListener);
        home_name_linearLayout = findViewById(R.id.home_name_linearLayout);

        home_name_linearLayout.setOnClickListener(this::showCustomHomePopupWindow);

    }

    private View.OnClickListener onChangeActivityClickListener = new View.OnClickListener() {
        @SuppressLint("UnsafeOptInUsageError")
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.main_light_include:
                    intent.setClass(MainActivity.this, LightActivity.class);
                    break;
                case R.id.main_lock_include:
                    intent.setClass(MainActivity.this, LockActivity.class);
                    break;
                case R.id.main_environment_include:
                    intent.setClass(MainActivity.this, EnvironmentActivity.class);
                    break;
                case R.id.main_monitor_include:
                    intent.setClass(MainActivity.this, MonitorVideoActivity.class);
                    break;
                case R.id.main_setting_imageButton:
                    intent.setClass(MainActivity.this, SettingActivity.class);
                    intent.putExtra("homeName", roomSelect);
                    intent.putExtra("homeCount", homeDataList.size());
                    intent.putExtra("homeId", homeId);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
    };


    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.main_room_recyclerView);
        mAdapter = new MainAdapter(this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(mAdapter);
    }

    private void setupImageView() {
        wx_imageView = findViewById(R.id.wx_imageView);
    }

    private void setupImageButton() {
        main_setting_imageButton = findViewById(R.id.main_setting_imageButton);
        main_setting_imageButton.setOnClickListener(onChangeActivityClickListener);
        room_setting_imageButton = findViewById(R.id.room_setting_imageButton);
        room_setting_imageButton.setOnClickListener(this::roomSettingOnClick);
    }

    private void setupTextView() {
        t_textView = findViewById(R.id.t_textView);
        pop6h_textView = findViewById(R.id.pop6h_textView);
        rh_textView = findViewById(R.id.rh_textView);
        ws_textView = findViewById(R.id.ws_textView);
        home_textView = findViewById(R.id.home_textView);
        light_textView = findViewById(R.id.main_light_include).findViewById(R.id.device_type);
        light_textView = findViewById(R.id.main_light_include).findViewById(R.id.device_type);
        light_qty_textView = findViewById(R.id.main_light_include).findViewById(R.id.device_qty);
        monitor_textView = findViewById(R.id.main_monitor_include).findViewById(R.id.device_type);
        monitor_qty_textView = findViewById(R.id.main_monitor_include).findViewById(R.id.device_qty);
        environment_textView = findViewById(R.id.main_environment_include).findViewById(R.id.device_type);
        environment_qty_textView = findViewById(R.id.main_environment_include).findViewById(R.id.device_qty);
        lock_textView = findViewById(R.id.main_lock_include).findViewById(R.id.device_type);
        lock_qty_textView = findViewById(R.id.main_lock_include).findViewById(R.id.device_qty);
    }


    private void roomSettingOnClick(View view) {
        showCustomPopupWindow();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setWeatherData(String wx, String t, String rh, String ws, String pop6h) {
        t_textView.setText(t);
        pop6h_textView.setText("降雨機率 : " + pop6h + "%");
        rh_textView.setText("濕度 : " + rh + "%");
        Log.e(TAG, "setWeatherData: " + wx);
        if (ws.equals("Nan"))
            ws_textView.setText("風速 : Nan km/h");
        else
            ws_textView.setText("風速 : " + Float.parseFloat(ws) * 3.6 + " km/h");
        switch (wx) {
            case "晴":
                wx_imageView.setAnimation("animation_sunny.json");
                break;
            case "多雲":
                wx_imageView.setAnimation("animation_cloudy.json");
                break;
            case "晴時多雲":
                wx_imageView.setAnimation("animation_partly_cloudy.json");
                break;
            case "短暫陣雨":
            case "雷雨":
            case "短暫陣雨或雷雨":
                wx_imageView.setAnimation("animation_partly_shower.json");
                break;
        }
    }

    @Override
    public void homeData(List<HomeResponse.HomeData> homeDataList) {
        progressDialog.dismissDialog();
        Log.e("lee", "homeData: " + homeDataList.size());
        Log.e("lee", "homeData: " + homeDataList.get(0).getHomeName());
        roomList.clear();
        homeList.clear();
        this.homeDataList = homeDataList;
        for (HomeResponse.HomeData.Room room : homeDataList.get(0).getRooms()) {
            roomNameList.add(room.getRoomName());
            roomList.add(room.getRoomName());
        }
        for (HomeResponse.HomeData homeData : homeDataList) {
            homeList.add(homeData.getHomeName());
        }

        home_textView.setText(homeList.get(0));

        setupRecyclerView();
        mAdapter.setRoomList(homeDataList.get(0).getRooms());
        //進畫面就抓第一個房間設備數量
        Long firstRoomId = homeDataList.get(0).getRooms().get(0).getId();
        presenter.getDeviceQty(firstRoomId);
        roomSelect = homeDataList.get(0).getHomeName();
        homeId = homeDataList.get(0).getId();
        // 在homeData方法中設置RecyclerView的點擊監聽
        mAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HomeResponse.HomeData.Room selectedRoom = homeDataList.get(0).getRooms().get(position);
                Long selectedRoomId = selectedRoom.getId();
                //抓取指定房間設備數量
                presenter.getDeviceQty(selectedRoomId);

            }
        });
    }

    public MainHomeDialog.OnClickListener getOnSubmitClickListener() {
        return new MainHomeDialog.OnClickListener() {
            @Override
            public void onSubmitClick(String homeName) {
                presenter.addHome(homeName);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("lee", "onActivityResult: " + "2222222");

        if (requestCode == REQUEST_CODE_ROOM_MANAGE && resultCode == RESULT_OK) {
            presenter.getHomeData();
            Log.e("lee", "onActivityResult: " + "test");
        }
    }

    @Override
    public void DeviceQty(DeviceQtyResponse response) {
        light_qty_textView.setText(response.getLight().toString());
        monitor_qty_textView.setText(response.getMonitor().toString());
        environment_qty_textView.setText(response.getEnvironment().toString());
        lock_qty_textView.setText(response.getLock().toString());
    }

    @Override
    public void addHomeSuccess() {
        presenter.getHomeData();
    }

    @Override
    public void addHomeFail() {
        Toast.makeText(this, "新增失敗", Toast.LENGTH_SHORT).show();
    }
}