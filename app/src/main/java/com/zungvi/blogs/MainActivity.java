package com.zungvi.blogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoImageView = findViewById(R.id.icon);
        logoImageView.animate().alpha(3000).setDuration(0);
        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent secondForm = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(secondForm);
                finish();
            }
        },4000);
    }
}