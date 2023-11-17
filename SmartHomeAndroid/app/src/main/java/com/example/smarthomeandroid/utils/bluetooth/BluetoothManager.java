package com.example.smarthomeandroid.utils.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BluetoothManager {
    private final Context context;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothScanner;
    private BluetoothGatt bluetoothGatt;
    private String deviceAddress;
    private BluetoothGattService service;
    private BluetoothScanListener bluetoothScanListener;
    private List<String> deviceList = new ArrayList<>();

    public BluetoothManager(Context context) {
        this.context = context;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "設備不支援藍牙", Toast.LENGTH_SHORT).show();
        }
    }

    // 開始 BLE 設備掃描
    @SuppressLint("MissingPermission")
    public void startScanning() {
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            bluetoothScanner = bluetoothAdapter.getBluetoothLeScanner();
            ScanSettings scanSettings = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();
            bluetoothScanner.startScan(null, scanSettings, scanCallback);
        } else {
            Toast.makeText(context, "藍牙未啟用", Toast.LENGTH_SHORT).show();
        }
    }

    // 停止 BLE 設備掃描
    @SuppressLint("MissingPermission")
    public void stopScanning() {
        bluetoothScanner.stopScan(scanCallback);
    }

    // 用於處理 BLE 設備掃描結果的回呼函式
    private ScanCallback scanCallback = new ScanCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            if (device.getName()!= null) {
                if (!deviceList.contains(device.getAddress())) {
                    deviceList.add(device.getAddress());
                    BluetoothData bluetoothData = new BluetoothData(device.getName(), device.getAddress());
                    bluetoothScanListener.scanListeners(bluetoothData);
                }
            }
        }
    };

    @SuppressLint("MissingPermission")
    public boolean connectToDevice(String deviceAddress) {
        this.deviceAddress = deviceAddress;
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
        bluetoothGatt = device.connectGatt(context, false, gattCallback);
        return bluetoothGatt != null;
    }

    @SuppressLint("MissingPermission")
    public void disconnect() {
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
        }
    }

    private BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                Log.d("lee", "Connected to GATT server.");
                gatt.discoverServices();
            } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                Log.d("lee", "Disconnected from GATT server.");
            }
        }
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            byte[] dataReceived = characteristic.getValue();
            if(String.valueOf(dataReceived[0]).equals("3")) {
            }
        }
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                byte[] dataReceived = characteristic.getValue();
            }
        }
    };

    @SuppressLint("MissingPermission")
    public void sendData(BluetoothGatt gatt, String serviceUuid, String uuid, byte[] sendData) {
        // 獲取設備的特徵與服務
        service = gatt.getService(UUID.fromString(serviceUuid));
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(uuid));
        //設定傳輸設定
        characteristic.setValue(sendData);
        gatt.setCharacteristicNotification(characteristic, true);
        boolean writeSuccess = gatt.writeCharacteristic(characteristic);

        if (writeSuccess) {
            Log.d("lee", "Data sent successfully.");
        } else {
            Log.e("lee", "Failed to send data.");
        }
    }

    @SuppressLint("MissingPermission")
    public void readData(BluetoothGatt gatt, String serviceUuid, String uuid) {
        // 獲取設備的特徵與服務
        service = gatt.getService(UUID.fromString(serviceUuid));
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(uuid));
        // 讀取特徵資料
        boolean readSuccess = gatt.readCharacteristic(characteristic);
        characteristic.getValue();

        if (readSuccess) {
            Log.d("lee", "Data read successfully.");
        } else {
            Log.e("lee", "Failed to read data.");
        }
        gatt.setCharacteristicNotification(characteristic, true);
    }

    public void setScanListener(BluetoothScanListener listener) {
        this.bluetoothScanListener = listener;
    }

    public interface BluetoothScanListener {
        void scanListeners(BluetoothData bluetoothData);
    }
}