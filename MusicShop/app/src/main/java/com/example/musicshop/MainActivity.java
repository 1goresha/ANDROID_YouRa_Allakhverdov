package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    TextView quantityTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityTextView = findViewById(R.id.quantity);
    }

    public void increase(View view) {
        quantityTextView.setText(++quantity + "");
    }

    public void decrease(View view) {
        quantity--;
        if (quantity < 0){
            quantity++;
        }
        quantityTextView.setText(quantity + "");
    }
}
