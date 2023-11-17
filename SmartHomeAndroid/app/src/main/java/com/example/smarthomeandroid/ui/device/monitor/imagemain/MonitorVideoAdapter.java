package com.example.smarthomeandroid.ui.device.monitor.imagemain;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.main.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class MonitorVideoAdapter extends RecyclerView.Adapter<MonitorVideoAdapter.ViewHolder> implements MonitorVideoContract.Adapter {
    private MonitorVideoContract.View view;
    public MonitorVideoAdapter(MonitorVideoContract.View view) {
        this.view = view;
    }
    private List<String> videoList= new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView old_video_textView;
        private LinearLayout old_video_linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            old_video_linearLayout = itemView.findViewById(R.id.old_video_linearLayout);
            old_video_textView = itemView.findViewById(R.id.old_video_textView);
        }
    }

    @NonNull
    @Override
    public MonitorVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_monitor_video,parent,false);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 15);
        view.setLayoutParams(layoutParams);
        return new MonitorVideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonitorVideoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.old_video_textView.setText(videoList.get(position));

        holder.old_video_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.onOldVideoClick(videoList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    @Override
    public void setDataList(List<String> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }


}
