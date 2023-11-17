package com.example.smarthomeandroid.ui.device.newdevice.connectbluefragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthomeandroid.R;

import java.util.List;

public class ConnectBlueFragmentAdapter extends RecyclerView.Adapter<ConnectBlueFragmentAdapter.ViewHolder> implements ConnectBlueFragmentContract.Adapter {
    private List<String> dataList; // 用于存储您的数据
    private Context context;

    public ConnectBlueFragmentAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_connect_blue_device, parent, false);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 15);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectBlueFragmentAdapter.ViewHolder holder, int position) {
        holder.new_device_item_textview.setText(dataList.get(position));
        holder.blue_device_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void setData(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView new_device_item_textview;
        private ConstraintLayout blue_device_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            new_device_item_textview = itemView.findViewById(R.id.new_device_item_textview);
            blue_device_layout = itemView.findViewById(R.id.blue_device_layout);
        }
    }
}
