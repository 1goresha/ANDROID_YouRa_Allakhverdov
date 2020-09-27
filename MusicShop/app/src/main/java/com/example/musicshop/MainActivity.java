package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    TextView quantityTextView;
    Spinner spinner;
    List arrayList;
    HashMap goodsMap;
    TextView textViewPrice;
    String selectedItemFormSpinner;
    Double price;
    ImageView goodsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityTextView = findViewById(R.id.quantity);
        textViewPrice = findViewById(R.id.price);
        goodsImage = findViewById(R.id.goodsImage);

        createSpinner();
        createGoodsMap();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemFormSpinner = spinner.getSelectedItem().toString();
                Double priceDouble = (Double) goodsMap.get(selectedItemFormSpinner);
                price = quantity * priceDouble;
                textViewPrice.setText(price + "");

                switch (selectedItemFormSpinner) {
                    case "drums":
                        goodsImage.setImageResource(R.drawable.drum_set);
                        break;
                    case "piano":
                        goodsImage.setImageResource(R.drawable.piano);
                        break;
                    default:
                        goodsImage.setImageResource(R.drawable.guitar);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void createSpinner() {
        spinner = findViewById(R.id.selectSpinner);
        arrayList = new ArrayList();
        arrayList.add("guitar");
        arrayList.add("drums");
        arrayList.add("piano");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void createGoodsMap() {
        goodsMap = new HashMap();
        goodsMap.put("guitar", 1000.0);
        goodsMap.put("drums", 1500.0);
        goodsMap.put("piano", 2000.0);
    }

    public void increase(View view) {
        quantityTextView.setText(++quantity + "");
        Double priceDouble = (Double) goodsMap.get(selectedItemFormSpinner);
        price = quantity * priceDouble;
        textViewPrice.setText(price + "");
    }

    public void decrease(View view) {
        quantity--;
        if (quantity < 0) {
            quantity++;
        }
        quantityTextView.setText(quantity + "");
        Double priceDouble = (Double) goodsMap.get(selectedItemFormSpinner);
        price = quantity * priceDouble;
        textViewPrice.setText(price + "");
    }

}
