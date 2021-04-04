package com.example.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        LiveData<Integer> liveData = myViewModel.getMutableLiveData();
        liveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textView.setText(String.valueOf(integer));
            }
        });
    }

    public void increase(View view){
        myViewModel.getIncrease();
    }

    public void decrease(View view){
        myViewModel.getDecrease();
    }


}
