package com.example.musicshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    List<String> arrayList;
    HashMap<String, Double> goodsMap;
    TextView textViewPrice;
    String selectedItemFormSpinner;
    Double orderPrice;
    Double price;
    ImageView goodsImage;
    EditText customerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityTextView = findViewById(R.id.quantity);
        textViewPrice = findViewById(R.id.price);
        goodsImage = findViewById(R.id.goodsImage);
        customerName = findViewById(R.id.editText2);

        createSpinner();
        createGoodsMap();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemFormSpinner = spinner.getSelectedItem().toString();
                Double priceDouble = goodsMap.get(selectedItemFormSpinner);
                orderPrice = quantity * priceDouble;
                textViewPrice.setText(orderPrice + "");

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
        arrayList = new ArrayList<>();
        arrayList.add("guitar");
        arrayList.add("drums");
        arrayList.add("piano");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void createGoodsMap() {
        goodsMap = new HashMap<>();
        goodsMap.put("guitar", 1000.0);
        goodsMap.put("drums", 1500.0);
        goodsMap.put("piano", 2000.0);
    }

    public void increase(View view) {
        quantityTextView.setText(++quantity + "");
        price = goodsMap.get(selectedItemFormSpinner);
        orderPrice = quantity * price;
        textViewPrice.setText(orderPrice + "");
    }

    public void decrease(View view) {
        quantity--;
        if (quantity < 0) {
            quantity++;
        }
        quantityTextView.setText(quantity + "");
        price = goodsMap.get(selectedItemFormSpinner);
        orderPrice = quantity * price;
        textViewPrice.setText(orderPrice + "");
    }

    public void makeOrder(View view) {
        Order order = new Order();
        order.setCustomerName(customerName.getText().toString());
        order.setGoodsName(selectedItemFormSpinner);
        order.setQuantity(quantity);
        order.setPrice(price);
        order.setOrderPrice(orderPrice);

        Intent mainIntent = new Intent(this, OrderActivity.class);
        mainIntent.putExtra("customerName", order.getCustomerName());
        mainIntent.putExtra("goodsName", order.getGoodsName());
        mainIntent.putExtra("quantity", order.getQuantity());
        mainIntent.putExtra("price", order.getPrice());
        mainIntent.putExtra("orderPrice", order.getOrderPrice());
        startActivity(mainIntent);
    }
}
