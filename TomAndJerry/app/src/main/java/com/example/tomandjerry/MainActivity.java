package com.example.tomandjerry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView tomImageView;
    ImageView jerryImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tomImageView = findViewById(R.id.tomImageView);
        jerryImageView = findViewById(R.id.jerryImageView);
        jerryImageView.animate().scaleY(0).scaleX(0);
    }

    public void changePicture(View view) {
        if (tomImageView.getAlpha() == 1) {
            tomImageView.animate().alpha(0).rotationBy(3600).scaleX(0).scaleY(0).setDuration(3000);
            jerryImageView.animate().alpha(1).rotationBy(3600).scaleX(1).scaleY(1).setDuration(3000);
        } else if (tomImageView.getAlpha() == 0){
            jerryImageView.animate().alpha(0).rotationBy(3600).scaleX(0).scaleY(0).setDuration(3000);
            tomImageView.animate().alpha(1).rotationBy(3600).scaleX(1).scaleY(1).setDuration(3000);
        }
    }
}
