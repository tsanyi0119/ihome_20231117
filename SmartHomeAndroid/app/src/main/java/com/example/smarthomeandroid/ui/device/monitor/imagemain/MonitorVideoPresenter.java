package com.example.smarthomeandroid.ui.device.monitor.imagemain;

import android.content.Context;
import android.util.Log;

import com.example.smarthomeandroid.utils.api.ApiClient;
import com.example.smarthomeandroid.utils.api.ApiService;
import com.example.smarthomeandroid.utils.api.soap.response.VideoMP4Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class MonitorVideoPresenter implements MonitorVideoContract.Presenter{
    private MonitorVideoContract.View view;
    private ApiService apiService;
    private Context context;
    public MonitorVideoPresenter(MonitorVideoContract.View view, Context context) {
        this.view = view;
        apiService = ApiClient.getSmartHomeInstance().getApiService();
        this.context = context;
    }
    @Override
    public void getVideoList() {
        apiService.videoName()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<List<String>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<List<String>> videoResponseResponse) {
                        if(videoResponseResponse.isSuccessful()) {
                            view.oldVideoList(videoResponseResponse.body());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getVideoFile(String fileName) {
        fileName = fileName + ".mp4";
        apiService.videoData(fileName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody videoMP4ResponseResponse) {
                        Log.e("onNext", "onNext: test123" );
//                        Log.e("onNext", "onNext: test" + videoMP4ResponseResponse.byteStream());
                        saveVideoToFile(videoMP4ResponseResponse.byteStream());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("onError", e.toString() );

                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete", "onComplete: onComplete"  );

                    }
                });
    }

    private void saveVideoToFile(InputStream inputStream) {
        try {
            // 指定存儲影片的目標檔案路徑
            String savePath =context.getExternalFilesDir(null) + File.separator + "downloaded_video.mp4";

            File outputFile = new File(savePath);

            // 創建一個輸出串流，用於寫入影片內容
            OutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 關閉串流
            inputStream.close();
            outputStream.close();

            Log.e("saveVideoToFile", "Video saved to: " + savePath);

            // 現在您可以使用這個檔案進行播放或其他操作
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("saveVideoToFile", "Error saving video: " + e.getMessage());
        }
    }

}
