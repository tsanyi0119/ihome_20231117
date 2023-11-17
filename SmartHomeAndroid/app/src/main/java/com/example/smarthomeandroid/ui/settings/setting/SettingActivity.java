package com.example.smarthomeandroid.ui.settings.setting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smarthomeandroid.R;
import com.example.smarthomeandroid.ui.base.BaseDialog;
import com.example.smarthomeandroid.ui.settings.changepassword.ChangePasswordActivity;
import com.example.smarthomeandroid.ui.authentication.login.LoginActivity;
import com.example.smarthomeandroid.utils.sharedpreferences.LoginSharedPreferences;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class SettingActivity extends AppCompatActivity implements SettingContract.View {
    private Toolbar setting_toolbar;
    private ConstraintLayout setting_constraint_layout;
    private ImageView setting_imageView;
    private Button setting_email_button;
    private Button setting_home_button;
    private Button setting_password_button;
    private Button setting_logout_button;
    private SettingPresenter settingPresenter;
    private TextView setting_userName_textView;
    private LoginSharedPreferences loginSharedPreferences;
    private BaseDialog deleteHomeDialog;
    private static final int REQUEST_SELECT_PICTURE = 101;
    private Uri Selected_imagePath;
    private String homeName;
    private Long honeId;
    private int homeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init(){
        Intent intent = getIntent();
        homeName = intent.getStringExtra("homeName");
        homeCount = intent.getIntExtra("homeCount", 1);
        honeId = intent.getLongExtra("homeId", 0);

        loginSharedPreferences = new LoginSharedPreferences(this);
        settingPresenter = new SettingPresenter(this);
        setupUI();
        settingPresenter.getPhoto();


    }

    private void setupUI() {
        setupActionBar();
        setupImageView();
        setupButton();
        setupConstraintLayout();
        setupTextView();
    }

    private void setupTextView() {
        setting_userName_textView = findViewById(R.id.setting_userName_textView);
        setting_userName_textView.setText(loginSharedPreferences.getUser());
    }

    private void setupButton() {
        setting_email_button = findViewById(R.id.setting_email_button);
        setting_home_button = findViewById(R.id.setting_home_button);
        setting_password_button = findViewById(R.id.setting_password_button);
        setting_logout_button = findViewById(R.id.setting_logout_button);
        setting_email_button.setText(loginSharedPreferences.getEmail());
        setting_home_button.setAlpha(1);
        if(homeCount == 1) {
            setting_home_button.setEnabled(false);
            setting_home_button.setAlpha(0.5f);
        }
        setting_home_button.setOnClickListener(this::onHomeClicked);
        setting_password_button.setOnClickListener(this::onPasswordClicked);
        setting_logout_button.setOnClickListener(this::onLogoutClicked);
    }

    private void setupActionBar() {
        setting_toolbar = findViewById(R.id.setting_toolbar);
        setting_toolbar.setNavigationIcon(getDrawable(R.drawable.ic_back_arrow));
        setting_toolbar = findViewById(R.id.setting_toolbar);

        setSupportActionBar(setting_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setting_toolbar.setNavigationOnClickListener(this::onBackPressed);

    }

    private void setupImageView() {
        setting_imageView = findViewById(R.id.setting_imageView);
        setting_imageView.setOnClickListener(this::oUserLogoClicked);
    }

    private void setupConstraintLayout() {
        setting_constraint_layout = findViewById(R.id.setting_frame_layout);
        startFloatUpAnimation();
    }


    private void oUserLogoClicked(View view) {
        Log.e("jay", "oUserLogoClicked: " );
        pickImage();
    }

    private void onHomeClicked(View view) {
        deleteHomeDialog = new BaseDialog();
        deleteHomeDialog.init(this,"刪 除 家 庭","確定要刪除\""+homeName+"\"","刪除");
        deleteHomeDialog.setOnSubmitClickListener(getOnSubmitClickListener());
        deleteHomeDialog.show();
    }

    private void onPasswordClicked(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }
    private void onLogoutClicked(View view) {
        settingPresenter.logout();
    }

    @Override
    public void logoutSuccess() {
        loginSharedPreferences.dataClear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void logoutFailed() {
        Toast.makeText(this, R.string.logout_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadPhotoFailed() {
        Toast.makeText(this, R.string.upload_photo_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uploadPhotoSuccess() {
        Toast.makeText(this, R.string.upload_photo_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPhotoSuccess(Bitmap photo) {
        Log.e("jay", "getPhotoSuccess: "+ photo);
        glideLoadImage(photo);
    }

    @Override
    public void deleteHomeSuccess() {
        Toast.makeText(this, "刪除成功", Toast.LENGTH_SHORT).show();
        deleteHomeDialog.dismiss();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void deleteHomeFailed() {
        deleteHomeDialog.dismiss();
        Toast.makeText(this, "刪除家庭失敗", Toast.LENGTH_SHORT).show();
    }

    public BaseDialog.OnClickListener getOnSubmitClickListener() {
        return new BaseDialog.OnClickListener() {
            @Override
            public void onSubmitClick() {
                settingPresenter.deleteHome(honeId);
            }
        };
    }

    // 讓 FrameLayout 加入動畫
    private void startFloatUpAnimation() {
        ObjectAnimator floatUpAnimator = ObjectAnimator.ofFloat(
                setting_constraint_layout,
                "translationY",
                1600f,
                0f
        );
        floatUpAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        floatUpAnimator.setDuration(1000);
        floatUpAnimator.start();
    }

    private void onBackPressed(View view) {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    // Glide讀取圖片並設置圖片在ImageView上
    private void glideLoadImage(Bitmap imagePath) {
        Glide.with(this)
                .load(imagePath) //圖片路徑
                .fitCenter()    //置中
                .circleCrop()   // 裁切成圓形
                .into(setting_imageView);   // 放置圖片的ImageView或GridView等等
    }
    // 選取圖片功能
    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_PICTURE);
    }
    // 開啟裁切圖片功能
    private void startCropActivity(Uri sourceUri) {
        // 构建裁切图像的文件路径
        File destinationFile = new File(getCacheDir(), "cropped_image.jpg");

        // 清除之前的裁切图像
        if (destinationFile.exists()) {
            destinationFile.delete();
        }

        Uri destinationUri = Uri.fromFile(destinationFile);


        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(80);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(false);
        options.setToolbarTitle("裁切圖像");
        options.setShowCropFrame(true);
        options.setShowCropGrid(true);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);
    }
    // 返回資料，傳回選取的圖片並利用取得真實路徑傳入並設置圖片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_PICTURE && resultCode == RESULT_OK) {
            if (data != null) {
                // 取得選取的Image的URI
                Uri selectedImageUri = data.getData();
                // 開啟裁切圖片功能
                startCropActivity(selectedImageUri);

            }
        }
        // 若裁切圖片成功則取得裁切後的圖片，並將圖片路徑傳入寫好的GlideLoadImage方法
        else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            Uri imageUri = UCrop.getOutput(data);

            // 讀取圖片的位元組數組
            Bitmap croppedBitmap = getBitmapFromUri(imageUri);

            // 將 Bitmap 轉換為 Base64 字串
            String base64String = convertBitmapToBase64(croppedBitmap);
            settingPresenter.uploadPhoto(base64String);
        }
    }
    // 這個輔助方法用於從URI中讀取圖片的位元組數組並轉換為Bitmap
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 使用內容解析器打開並讀取圖片
            InputStream inputStream = getContentResolver().openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 處理錯誤情況
        }
    }
    // 這個輔助方法用於將Bitmap轉換為Base64字串
    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream); // 壓縮為JPEG格式
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // 使用Base64編碼將位元組數組轉換為Base64字串
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}