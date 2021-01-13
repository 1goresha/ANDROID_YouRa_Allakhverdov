package com.example.cooltimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private Button button;
    private TextView textview;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;

    private int minutes;
    private int seconds;
    private int maxSeekBar = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);
        textview = findViewById(R.id.textView);

        seekBar.setMax(maxSeekBar);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateViewText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String melody = sharedPreferences.getString("songs", "bell");

                if (button.getText().equals("Start")) {
                    button.setText("Stop");
                    seekBar.setEnabled(false);
                    if (melody.equals("siren")) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_siren_sound);
                    } else if (melody.equals("bell")) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                    } else if (melody.equals("bip")) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bip_sound);
                    }
                    countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateViewText((int) millisUntilFinished / 1000);
                            seekBar.setProgress((int) millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            if (sharedPreferences.getBoolean("isEnable", true)) {
                                mediaPlayer.start();
                            }
                        }
                    };
                    countDownTimer.start();
                } else {
                    button.setText("Start");
                    seekBar.setEnabled(true);
                    countDownTimer.cancel();
                    mediaPlayer.stop();
                }
            }
        });
    }

    public void updateViewText(int progress) {
        minutes = progress / 60;
        seconds = progress - (minutes * 60);

        if (progress == 0) {
            textview.setText("00:00");
        } else if (progress == maxSeekBar) {
            textview.setText("10:00");
        } else if (seconds <= 9) {
            textview.setText("0" + minutes);
            textview.append(":" + "0" + seconds);
        } else {
            textview.setText("0" + minutes);
            textview.append(":" + seconds);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
