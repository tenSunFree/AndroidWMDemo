package com.example.administrator.androidwmdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.bean.WatermarkImage;
import com.watermark.androidwm.bean.WatermarkText;
import com.watermark.androidwm.listener.BuildFinishListener;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private Bitmap pieBitmap;
    private WatermarkImage watermarkImage;
    private WatermarkText watermarkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
    }

    private void initViews() {
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        /** 初始化imageView */
        pieBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android_9_pie);
        watermarkImage = new WatermarkImage(this, R.drawable.android_9_pie);
        WatermarkBuilder.create(this, pieBitmap)
                .loadWatermarkImage(watermarkImage)
                .setInvisibleWMListener(true, new BuildFinishListener<Bitmap>() {
                    @Override
                    public void onSuccess(Bitmap object) {
                        imageView.setImageBitmap(object);
                        button.setClickable(true);
                    }
                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initEvents() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /** 添加浮水印的文字 */
                watermarkText = new WatermarkText("google")
                        .setPositionX(0.5)
                        .setPositionY(0.5)
                        .setTextAlpha(102)
                        .setRotation(-20)
                        .setTextSize(50)
                        .setTextColor(0xe0a2a2a2)
                        .setTextFont(R.font.champagne);
                WatermarkBuilder.create(MainActivity.this, imageView)
                        .setTileMode(true)
                        .loadWatermarkText(watermarkText)
                        .getWatermark()
                        .setToImageView(imageView);

                /** 添加浮水印的圖像 */
                watermarkImage = new WatermarkImage(MainActivity.this, R.drawable.google_logo)
                        .setImageAlpha(40)
                        .setPositionX(0.75)
                        .setPositionY(0)
                        .setRotation(15)
                        .setSize(0.2);
                WatermarkBuilder
                        .create(MainActivity.this, imageView)
                        .loadWatermarkImage(watermarkImage)
                        .setTileMode(false)
                        .getWatermark()
                        .setToImageView(imageView);

                button.setClickable(false);
            }
        });
        button.setClickable(false);
    }
}
