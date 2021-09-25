package com.example.testwithnewthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button start;
    Button stop;
    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
    CountDownTimer countDownTimer1;
    CountDownTimer countDownTimer2;
    CountDownTimer countDownTimer3;
    public static boolean isReady1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.image1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.image2);

        countDownTimer1 = new CountDownTimer(60000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (isReady1) {

                    relativeLayout1.setAlpha(0);
                    isReady1 = false;
                } else {

                    relativeLayout1.setAlpha(1);
                    isReady1 = true;
                }
            }

            @Override
            public void onFinish() {

            }
        };

        countDownTimer2 = new CountDownTimer(60000, 250) {

            @Override
            public void onTick(long millisUntilFinished) {

                relativeLayout2.setRotation(millisUntilFinished / 100);
            }

            @Override
            public void onFinish() {

            }
        };

        countDownTimer3 = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                Toast.makeText(MainActivity.this, millisUntilFinished + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {

            }
        };


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        countDownTimer1.start();
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        countDownTimer2.start();
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        countDownTimer3.start();
                    }
                }).start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countDownTimer1.cancel();
                countDownTimer2.cancel();
                countDownTimer3.cancel();
            }
        });

    }
}