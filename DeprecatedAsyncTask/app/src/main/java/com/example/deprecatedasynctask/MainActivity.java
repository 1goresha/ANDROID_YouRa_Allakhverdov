package com.example.deprecatedasynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.deprecatedasynctask.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TextView textView, textView2;
    private MyAsyncTask myAsyncTask;
    private Handler handler;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        handler = new Handler(Looper.myLooper()) {

            @Override
            public void handleMessage(@NonNull Message msg) {

                if (msg.what != 0) {

                    AlphaAnimation animationIn = new AlphaAnimation(0.0f, 1.0f);
                    AlphaAnimation animationOut = new AlphaAnimation(1.0f, 0.0f);

                    animationOut.setDuration(1000);
                    animationIn.setStartOffset(1000);
                    animationIn.setDuration(1000);

                    textView.startAnimation(animationIn);
                    textView.startAnimation(animationOut);

                }
            }
        };

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);

        myAsyncTask = new MyAsyncTask(textView);
    }

    public void doAsyncTask(View view) {


        new CountDownTimer(10000, 1000) {


            @Override
            public void onTick(long l) {

//                AlphaAnimation animationIn = new AlphaAnimation(0.0f, 1.0f);
//                AlphaAnimation animationOut = new AlphaAnimation(1.0f, 0.0f);
//
//                animationOut.setDuration(1000);
//                animationIn.setStartOffset(1000);
//                animationIn.setDuration(1000);
//
//                textView.startAnimation(animationIn);
//                textView.startAnimation(animationOut);

                handler.sendEmptyMessage(1);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }


    public void doAsyncTask2(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 100; i++) {
            textView.setText(i + "");
//                    activityMainBinding.setHeartRate(i + "");

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();


//        myAsyncTask.execute();
    }

    public void doAsyncTask3(View view) {

        AlphaAnimation animationIn = new AlphaAnimation(0.0f, 1.0f);
        AlphaAnimation animationOut = new AlphaAnimation(1.0f, 0.0f);

        animationIn.setDuration(1000);
        animationIn.setRepeatMode(Animation.INFINITE);
//        animationIn.setRepeatCount(Animation.INFINITE);
        textView2.startAnimation(animationIn);

        animationOut.setDuration(1000);
        animationIn.setRepeatMode(Animation.INFINITE);
//        animationIn.setRepeatCount(Animation.INFINITE);
        textView2.startAnimation(animationOut);
    }


    public static class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private TextView textView;

        public MyAsyncTask(TextView textView) {

            this.textView = textView;
        }


        @Override
        protected Void doInBackground(Void... Voids) {// return measure element

            for (int i = 0; i < 1000; i++) {
//                textView.setText(i + "");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textView.setText(i + "");
            }
            return null;
        }
    }
}
