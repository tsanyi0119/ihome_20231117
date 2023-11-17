package com.example.smarthomeandroid.ui.device.monitor.imagemain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.SimpleExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.utils.sharedpreferences.LoginSharedPreferences;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UnstableApi
public class MonitorVideoActivity extends AppCompatActivity implements MonitorVideoContract.View {

    private Toolbar monitor_image_toolbar;
    private MonitorVideoAdapter monitorVideoAdapter;
    private RecyclerView monitor_image_recyclerView;
    private ImageView monitor_video_live_imageView;
    private PlayerView monitor_video_playerView;
    private View monitor_overlay_View;
    private ExoPlayer player;
    private LoginSharedPreferences loginSharedPreferences;
    private boolean isLive = false;
    private boolean playWhenReady = true;
    private long playbackPosition = 0;
    private Thread drawThread;
    private MonitorVideoPresenter presenter;
    private List<String> videoList = new ArrayList<>();
    private String videoPath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_video);
        init();
    }

    private void init() {
        loginSharedPreferences = new LoginSharedPreferences(this);
        setupUI();
        presenter = new MonitorVideoPresenter(this, this);
        drawThread = getDrawThread();
        presenter.getVideoList();
    }

    private void setupUI() {
        setupToolbar();
        setupImage();
        setupRecyclerView();
        setupPlayView();
    }

    private void setupPlayView() {
        monitor_video_playerView = findViewById(R.id.monitor_video_playerView);
        monitor_overlay_View = findViewById(R.id.monitor_overlay_View);

        DefaultHttpDataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", "Bearer " + loginSharedPreferences.getToken());
        dataSourceFactory.setDefaultRequestProperties(requestHeaders);
        ProgressiveMediaSource.Factory progressivemediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory);
        player = new ExoPlayer.Builder(this).build();
        videoPath = "http://192.168.1.248:8080/api/smarthome/monitor/videos/video.mp4";
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoPath));
        MediaSource mediaSource = progressivemediaSource.createMediaSource(mediaItem);
        player.setMediaSource(mediaSource);
        player.setPlayWhenReady(playWhenReady);
        player.prepare();
        monitor_video_playerView.setPlayer(player);
        monitor_video_playerView.setUseController(false);
        player.play();

    }
    private void setupImage() {
        monitor_video_live_imageView = findViewById(R.id.monitor_video_live_imageView);
        monitor_video_live_imageView.setOnClickListener(this::onLiveSwitchClick);
    }

    private void setupRecyclerView() {
        monitor_image_recyclerView = findViewById(R.id.monitor_video_recyclerView);
        monitorVideoAdapter = new MonitorVideoAdapter(this);
        monitor_image_recyclerView.setAdapter(monitorVideoAdapter);
        monitor_image_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        monitorVideoAdapter.setDataList(videoList);
    }

    private void setupToolbar() {
        monitor_image_toolbar = findViewById(R.id.monitor_video_toolbar);
        monitor_image_toolbar.setNavigationIcon(getDrawable(R.drawable.ic_back_arrow));

        setSupportActionBar(monitor_image_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 移除預設標題
        monitor_image_toolbar.setNavigationOnClickListener(this::onBackPressed);
    }

    public void onLiveSwitchClick(View view) {
        if(!isLive) {
            isLive = true;
            monitor_video_live_imageView.setImageResource(R.drawable.ph_live);
        } else {
            isLive = false;
            monitor_video_live_imageView.setImageResource(R.drawable.ph_off_live);
        }
    }

    private void drawRectangle(int right, int left, int top, int bottom,
                               int x1, int x2, int y1, int y2) {

        int width = right - left; // 獲取 PlayerView 的寬度
        int height = bottom - top; // 獲取 PlayerView 的高度

        int rectLeft = Math.min(x1, x2); // 矩形的左上角 x 座標
        int rectTop = Math.min(y1, y2); // 矩形的左上角 y 座標
        int rectRight = Math.max(x1, x2); // 矩形的右下角 x 座標
        int rectBottom = Math.max(y1, y2); // 矩形的右下角 y 座標


        // 創建一個畫筆
        Paint paint = new Paint();
        paint.setColor(Color.RED); // 設置矩形的顏色為紅色
        paint.setStyle(Paint.Style.STROKE); // 設置為只畫邊框，不填充
        paint.setStrokeWidth(5); // 設置邊框寬度為5像素

        Bitmap overlayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlayBitmap);
        canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, paint);
        monitor_overlay_View.setBackgroundColor(Color.TRANSPARENT);
        monitor_overlay_View.setBackground(new BitmapDrawable(getResources(), overlayBitmap));

    }

    private Thread getDrawThread() {
        return new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long currentPositionMillis = player.getCurrentPosition();
                            int Seconds = (int) (currentPositionMillis / 1000);
                            Log.e("lee", "run: " +Seconds );
                            monitor_overlay_View.setBackgroundColor(Color.TRANSPARENT);
                            if(Seconds<3) {
//                                drawRectangle(right, left, top, bottom
//                                        , coordinateResponses.get(Seconds).getX1()
//                                        , coordinateResponses.get(Seconds).getX2()
//                                        , coordinateResponses.get(Seconds).getY1()
//                                        , coordinateResponses.get(Seconds).getY2());
                            }
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public void stopPostThread() {
        if(drawThread != null) {
            synchronized (drawThread) {
                drawThread.interrupt();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(playbackPosition);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            stopPostThread();
            player.release();
            player = null;
        }
    }

    private void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }

    @Override
    public void oldVideoList(List<String> videoList) {
        monitorVideoAdapter.setDataList(videoList);
    }

    @Override
    public void onOldVideoClick(String videoFile) {
        presenter.getVideoFile(videoFile);
    }

    @Override
    public void oldVideoFile(InputStream videoFile) {
        player.setMediaItem(MediaItem.fromUri(Uri.parse("http://192.168.1.248:8080/api/smarthome/monitor/videos/video.mp4")));
        player.setPlayWhenReady(playWhenReady);
        player.prepare();
        monitor_video_playerView.setPlayer(player);
        monitor_video_playerView.setUseController(false);
        player.play();
    }
}