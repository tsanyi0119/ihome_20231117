package com.example.smarthomeandroid.ui.device.monitor.imagemain;


import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface MonitorVideoContract {
    interface Presenter{
        void getVideoList();
        void getVideoFile(String fileName);
    }
    interface View {
        void oldVideoList(List<String> videoList);

        void onOldVideoClick(String videoFile);

        void oldVideoFile(InputStream videoFile);
    }
    interface Adapter {
        void setDataList(List<String> videoList);
    }
}
