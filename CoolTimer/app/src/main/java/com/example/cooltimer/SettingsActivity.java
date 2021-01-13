package com.example.cooltimer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = this.getSupportActionBar();//извлекаем из библиотеки Support ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//устанавливаем переход на уровень выше(то есть назад к родителю) значение true
        }
    }
}
