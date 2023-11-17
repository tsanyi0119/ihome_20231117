package com.example.smarthomeandroid.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.main.item.RoomItem;
import com.example.smarthomeandroid.utils.api.soap.response.HomeResponse;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements MainContract.Adapter{
    private MainContract.View view;
    private List<HomeResponse.HomeData.Room> roomList;
    private OnItemClickListener clickListener;

    public MainAdapter(MainContract.View view) {
        this.view = view;
    }

    //點擊接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView roomName_textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName_textView = itemView.findViewById(R.id.room_name_textview);
        }
    }
    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_room,parent,false);

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(20, 0, 20, 0);
        view.setLayoutParams(layoutParams);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        final HomeResponse.HomeData.Room item = roomList.get(position);
        holder.roomName_textView.setText(item.getRoomName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(adapterPosition);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    @Override
    public void setRoomList(List<HomeResponse.HomeData.Room> roomList) {
        this.roomList = roomList;
    }

    // 設定點擊監聽器的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }
}
