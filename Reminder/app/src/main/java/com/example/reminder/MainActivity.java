package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final String NEW_ALARM = "newAlarm";
    FloatingActionButton create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create = (FloatingActionButton)findViewById(R.id.floatingActionButton);


    }

    public void createNewAlarm(){
        Intent intent = new Intent(this,AlarmService.class);
        intent.setAction(NEW_ALARM);
    }
}
