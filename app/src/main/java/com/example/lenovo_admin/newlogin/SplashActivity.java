package com.example.lenovo_admin.newlogin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

public class SplashActivity extends AppCompatActivity {
    private static int spla=7000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        DrawableImageViewTarget imageViewTarget=new DrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.launchcar).into(imageView);
        ImageView imageView2 = findViewById(R.id.imageView2);
        DrawableImageViewTarget imageViewTarget2=new DrawableImageViewTarget(imageView2);
        Glide.with(this).load(R.drawable.load1).into(imageView2);

        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run () {
                Intent intent = new Intent(SplashActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, spla);
    }
}