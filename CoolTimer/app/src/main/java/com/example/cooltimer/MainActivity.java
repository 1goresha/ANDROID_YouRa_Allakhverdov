package com.example.cooltimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private SeekBar seekBar;
    private Button button;
    private TextView textView;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;

    private int minutes;
    private int seconds;
    private int maxSeekBar = 600;
    private int timerDuration;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        seekBar.setMax(maxSeekBar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        setTimerDurationFromSharedPreferences(sharedPreferences,"timer_duration");

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
//                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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
                    setTimerDurationFromSharedPreferences(sharedPreferences, "timer_duration");
                }
            }
        });

//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setTimerDurationFromSharedPreferences(sharedPreferences, key);
            }
        });
    }

    public void updateViewText(int progress) {
        minutes = progress / 60;
        seconds = progress - (minutes * 60);

        if (progress == 0) {
            textView.setText("00:00");
        } else if (progress == maxSeekBar) {
            textView.setText("10:00");
        } else if (seconds <= 9) {
            textView.setText("0" + minutes);
            textView.append(":" + "0" + seconds);
        } else {
            textView.setText("0" + minutes);
            textView.append(":" + seconds);
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

    public void setTimerDurationFromSharedPreferences(SharedPreferences sharedPreferences, String key){
        timerDuration = Integer.valueOf(sharedPreferences.getString(key, "30"));
        seekBar.setProgress(timerDuration);
        updateViewText(timerDuration);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!(key.equals("timer_duration"))) return;
        setTimerDurationFromSharedPreferences(sharedPreferences, key);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
}
