package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewPrevious;
    ImageView imageViewStart;
    ImageView imageViewNext;
    ImageView imageViewLabel;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.stuff);
        seekBar = findViewById(R.id.seekBar);
        imageViewStart = findViewById(R.id.imageViewStart);
        imageViewPrevious = findViewById(R.id.imageViewPrevious);
        imageViewNext = findViewById(R.id.imageViewNext);
        imageViewLabel = findViewById(R.id.imageView5);

        seekBar.setMax(mediaPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

        imageViewStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imageViewStart.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    imageViewLabel.animate().rotation(imageViewLabel.getRotation() + 2).setDuration(1000);
                } else {
                    if (seekBar.getProgress() == seekBar.getMax()){
                        seekBar.setProgress(0);
                        mediaPlayer.seekTo(0);
                    }
                    mediaPlayer.start();
                    imageViewStart.setImageResource(R.drawable.ic_pause_black_24dp);
                    imageViewLabel.animate().rotation(mediaPlayer.getDuration() / 10).setDuration(mediaPlayer.getDuration());
                }
            }
        });

        imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(0);
                mediaPlayer.seekTo(0);
                mediaPlayer.pause();
                imageViewStart.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                imageViewLabel.animate().rotation(imageViewLabel.getRotation() + 2).setDuration(1000);
            }
        });

        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(seekBar.getMax());
                mediaPlayer.seekTo(mediaPlayer.getDuration());
                mediaPlayer.pause();
                imageViewStart.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                imageViewLabel.animate().rotation(imageViewLabel.getRotation() + 2).setDuration(1000);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
