package com.example.smarthomeandroid.ui.device.newdevice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smarthomeandroid.R;

import android.Manifest;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class SearchDeviceFragment extends Fragment {
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 10;
    private BluetoothAdapter bluetoothAdapter;
    private Context context;
    private List<String> deviceList = new ArrayList<>();
    private ArrayAdapter<String> deviceListAdapter;
    ListView deviceListView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO: 2023/11/3 研究藍芽探索裝置之後顯示到ListView上。
        View searchDeviceView = inflater.inflate(R.layout.fragment_search_device, container, false);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        deviceListView = searchDeviceView.findViewById(R.id.deviceListView);

        if (bluetoothAdapter == null) {
            // Handle the case where the device doesn't support Bluetooth
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                // Bluetooth is not enabled, request permission to enable it
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_ENABLE_BT);
                    bluetoothAdapter.startDiscovery();
                } else {
                    // The permission is granted, start the activity to enable Bluetooth
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                }
            } else {
                // Bluetooth is enabled, proceed with your Bluetooth-related logic
                startBluetoothDiscovery();
            }
        }
        return searchDeviceView;
    }
    // 添加一个方法来开始蓝牙搜索
    private void startBluetoothDiscovery() {
        // 注册蓝牙广播接收器
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        requireContext().registerReceiver(bluetoothReceiver, filter);

        // 启动蓝牙搜索
        if (bluetoothAdapter != null) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                bluetoothAdapter.startDiscovery();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start the Bluetooth device discovery
                if (bluetoothAdapter != null) {
                    if (bluetoothAdapter.isEnabled()) {
                        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                            bluetoothAdapter.startDiscovery();
                        }
                    }
                }
            } else {
                // Handle the case where the user denied the permission
            }
        }
    }

    // 藍芽廣播接收器
    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // 将设备信息添加到列表
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    String deviceInfo = device.getName() + " - " + device.getAddress();
                    deviceList.add(deviceInfo);

                    // 通知适配器更新列表视图
                    deviceListAdapter.notifyDataSetChanged();
                }
            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        requireContext().unregisterReceiver(bluetoothReceiver);
    }
}