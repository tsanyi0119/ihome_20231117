package com.example.smarthomeandroid.ui.device.newdevice.connectbluefragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;



public class ConnectBlueFragment extends Fragment implements ConnectBlueFragmentContract.View {

    private RecyclerView recyclerView;
    private ConnectBlueFragmentAdapter newDeviceAdapter;
    private List<String> dataList = new ArrayList<>();
    private BaseDialog baseDialog;
    private Activity activity;
    public ConnectBlueFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connect_blue_device, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        baseDialog = new BaseDialog();
        baseDialog.setOnSubmitClickListener(onSubmitClickListener);

    }
    private void init(View view) {
        setupUI(view);
    }

    private void setupUI(View view) {
        recyclerView = view.findViewById(R.id.new_device_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        newDeviceAdapter = new ConnectBlueFragmentAdapter(this.getContext(), dataList);
        recyclerView.setAdapter(newDeviceAdapter);

        newDeviceAdapter.setData(dataList);
    }

    @Override
    public void connectBlueDevice(String deviceName) {
        baseDialog.init(activity,"連 接 藍 芽","是否要連接\""+deviceName+ "\"","確認");
    }

    private BaseDialog.OnClickListener onSubmitClickListener = new BaseDialog.OnClickListener() {
        @Override
        public void onSubmitClick() {
            Toast.makeText(activity, "送出連線", Toast.LENGTH_SHORT).show();
        }
    };
}